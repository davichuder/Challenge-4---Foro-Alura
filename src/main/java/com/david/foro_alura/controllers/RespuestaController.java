package com.david.foro_alura.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.david.foro_alura.dto.respuesta.ModificarRespuestaRequest;
import com.david.foro_alura.dto.respuesta.NuevaRespuestaRequest;
import com.david.foro_alura.dto.respuesta.RespuestaResponse;
import com.david.foro_alura.dto.respuesta.SolucionRespuestaRequest;
import com.david.foro_alura.exceptions.DuplicadoException;
import com.david.foro_alura.exceptions.NoEsCreadorException;
import com.david.foro_alura.exceptions.NoExisteException;
import com.david.foro_alura.exceptions.RespuestaNoCorrespondeException;
import com.david.foro_alura.exceptions.TopicoResueltoException;
import com.david.foro_alura.services.RespuestaService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/respuestas")
@PreAuthorize("isAuthenticated()")
public class RespuestaController {
    @Autowired
    private RespuestaService respuestaService;

    @PostMapping("/topico/{idTopico}")
    public ResponseEntity<RespuestaResponse> nuevaRespuesta(@PathVariable Long idTopico, HttpServletRequest request,
            @RequestBody @Valid NuevaRespuestaRequest nuevaRespuesta)
            throws DuplicadoException, NoExisteException, TopicoResueltoException {
        return ResponseEntity.ok(new RespuestaResponse(respuestaService.nueva(idTopico, request, nuevaRespuesta)));
    }

    @DeleteMapping("/id/{idRespuesta}")
    public ResponseEntity<Object> eliminarRespuesta(@PathVariable Long idRespuesta, HttpServletRequest request)
            throws NoExisteException, NoEsCreadorException {
        respuestaService.eliminar(idRespuesta, request);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/id/{idRespuesta}")
    public ResponseEntity<RespuestaResponse> modificarRespuesta(@PathVariable Long idRespuesta,
            @RequestBody @Valid ModificarRespuestaRequest modificarRespuesta, HttpServletRequest request)
            throws NoExisteException, DuplicadoException, NoEsCreadorException {
        return ResponseEntity
                .ok(new RespuestaResponse(respuestaService.modificar(idRespuesta, modificarRespuesta, request)));
    }

    @RequestMapping
    public ResponseEntity<Page<RespuestaResponse>> listadoRespuestas(@PageableDefault(size = 10) Pageable paginacion) {
        return ResponseEntity.ok(respuestaService.listado(paginacion));
    }

    @RequestMapping("/topico/{idTopico}")
    public ResponseEntity<Page<RespuestaResponse>> listadoRespuestasPorTopico(@PathVariable Long idTopico,
            @PageableDefault(size = 10) Pageable paginacion) {
        return ResponseEntity.ok(respuestaService.listadoPorTopico(idTopico, paginacion));
    }

    @RequestMapping("/usuario/{idUsuario}")
    public ResponseEntity<Page<RespuestaResponse>> listadoRespuestasPorUsuario(@PathVariable Long idUsuario,
            @PageableDefault(size = 10) Pageable paginacion) {
        return ResponseEntity.ok(respuestaService.listadoPorUsuario(idUsuario, paginacion));
    }

    @GetMapping("/id/{idRespuesta}")
    public ResponseEntity<RespuestaResponse> verRespuesta(@PathVariable Long idRespuesta) throws NoExisteException {
        return ResponseEntity.ok(new RespuestaResponse(respuestaService.ver(idRespuesta)));
    }

    @PostMapping("/marcarComoSolucion")
    public ResponseEntity<RespuestaResponse> marcarComoSolucion(@RequestBody SolucionRespuestaRequest solucionRequest,
            HttpServletRequest request) throws NoExisteException, NoEsCreadorException, RespuestaNoCorrespondeException, TopicoResueltoException {
        return ResponseEntity.ok(new RespuestaResponse(respuestaService.marcarComoSolucion(solucionRequest, request)));
    }
}