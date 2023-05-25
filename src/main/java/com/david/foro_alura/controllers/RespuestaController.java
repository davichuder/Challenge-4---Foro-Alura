package com.david.foro_alura.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.david.foro_alura.dto.respuesta.EliminarRespuestaRequest;
import com.david.foro_alura.dto.respuesta.ModificarRespuestaRequest;
import com.david.foro_alura.dto.respuesta.NuevaRespuestaRequest;
import com.david.foro_alura.dto.respuesta.RespuestaResponse;
import com.david.foro_alura.exceptions.DuplicadoException;
import com.david.foro_alura.exceptions.NoExisteException;
import com.david.foro_alura.exceptions.TopicoResueltoException;
import com.david.foro_alura.services.RespuestaService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/respuestas")
public class RespuestaController {
    @Autowired
    private RespuestaService respuestaService;

    @PostMapping
    @Transactional
    public ResponseEntity<RespuestaResponse> nuevaRespuesta(
            @RequestBody @Valid NuevaRespuestaRequest nuevaRespuesta) throws DuplicadoException, NoExisteException, TopicoResueltoException {
        return ResponseEntity.ok(new RespuestaResponse(respuestaService.nueva(nuevaRespuesta)));
    }

    @RequestMapping
    public ResponseEntity<Page<RespuestaResponse>> listadoRespuestas(@PageableDefault(size = 10) Pageable paginacion) {
        return ResponseEntity.ok(respuestaService.listado(paginacion));
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<Object> eliminarRespuesta(@RequestBody @Valid EliminarRespuestaRequest eliminarRespuesta) throws NoExisteException {
        respuestaService.eliminar(eliminarRespuesta);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity<RespuestaResponse> modificarRespuesta(
            @RequestBody @Valid ModificarRespuestaRequest modificarRespuesta) throws NoExisteException, DuplicadoException {
        return ResponseEntity.ok(new RespuestaResponse(respuestaService.modificar(modificarRespuesta)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespuestaResponse> verRespuesta(@PathVariable Long id) throws NoExisteException {
        return ResponseEntity.ok(new RespuestaResponse(respuestaService.ver(id)));
    }
}