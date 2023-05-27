package com.david.foro_alura.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.david.foro_alura.dto.topico.DetallesTopicoResponse;
import com.david.foro_alura.dto.topico.ModificarTopicoRequest;
import com.david.foro_alura.dto.topico.NuevoTopicoRequest;
import com.david.foro_alura.dto.topico.TopicoResponse;
import com.david.foro_alura.exceptions.DuplicadoException;
import com.david.foro_alura.exceptions.NoEsCreadorException;
import com.david.foro_alura.exceptions.NoExisteException;
import com.david.foro_alura.services.TopicoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Tag(name = "6. Topicos", description = "Authenticated Access")
@SecurityRequirement(name = "bearer-key")
@Controller
@RequestMapping("/topicos")
@PreAuthorize("isAuthenticated()")
public class TopicoController {
    @Autowired
    private TopicoService topicoService;

    @Operation(summary = "Nuevo topico", description = "Cualquier usuario puede crear un nuevo topico")
    @PostMapping
    public ResponseEntity<TopicoResponse> nuevoTopico(HttpServletRequest request,
            @RequestBody @Valid NuevoTopicoRequest nuevoTopico) throws NoExisteException, DuplicadoException {
        return ResponseEntity.ok(new TopicoResponse(topicoService.nuevo(request, nuevoTopico)));
    }

    @Operation(summary = "Eliminar topico", description = "Cualquier usuario puede eliminar unicamente los topicos propios")
    @DeleteMapping("/id/{idTopico}")
    public ResponseEntity<Object> eliminarTopico(HttpServletRequest request, @PathVariable Long idTopico)
            throws NoExisteException, NoEsCreadorException {
        topicoService.eliminar(request, idTopico);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Modificar topico", description = "Cualquier usuario puede modificar unicamente los topicos propios")
    @PutMapping("/id/{idTopico}")
    public ResponseEntity<TopicoResponse> modificarTopico(HttpServletRequest request, @PathVariable Long idTopico,
            @RequestBody @Valid ModificarTopicoRequest modificarTopico)
            throws DuplicadoException, NoExisteException, NoEsCreadorException {
        return ResponseEntity.ok(new TopicoResponse(topicoService.modificar(request, idTopico, modificarTopico)));
    }

    @Operation(summary = "Lista de topicos", description = "Devuelve un listado de todos los topicos")
    @GetMapping
    public ResponseEntity<Page<TopicoResponse>> listadoTopicos(@PageableDefault(size = 10) Pageable paginacion) {
        return ResponseEntity.ok(topicoService.listado(paginacion));
    }

    @Operation(summary = "Lista de topicos por usuario", description = "Devuelve un listado de todos los topicos correspondientes a un usuario especifico")
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<Page<TopicoResponse>> verTopicosPorUsuario(@PathVariable Long idUsuario,
            @PageableDefault(size = 10) Pageable paginacion) throws NoExisteException {
        return ResponseEntity.ok(topicoService.verTopicosPorUsuario(idUsuario, paginacion));
    }

    @Operation(summary = "Lista de topicos por curso", description = "Devuelve un listado de todos los topicos correspondientes a un curso especifico")
    @GetMapping("/curso/{idCurso}")
    public ResponseEntity<Page<TopicoResponse>> verTopicosPorCurso(@PathVariable Long idCurso,
            @PageableDefault(size = 10) Pageable paginacion) throws NoExisteException {
        return ResponseEntity.ok(topicoService.verTopicosPorCurso(idCurso, paginacion));
    }

    @Operation(summary = "Lista de topicos por estatus", description = "Devuelve un listado de todos los topicos correspondientes a un estatus especifico")
    @GetMapping("/estatus/{estatus}")
    public ResponseEntity<Page<TopicoResponse>> verTopicosPorEstatus(@PathVariable String estatus,
            @PageableDefault(size = 10) Pageable paginacion) throws NoExisteException {
        return ResponseEntity.ok(topicoService.verTopicosPorEstatus(estatus, paginacion));
    }

    @Operation(summary = "Detalles de topico", description = "Devuelve los detalles de un topico especifico")
    @GetMapping("/id/{idTopico}")
    public ResponseEntity<DetallesTopicoResponse> verTopico(@PathVariable Long idTopico) throws NoExisteException {
        return ResponseEntity.ok(new DetallesTopicoResponse(topicoService.ver(idTopico)));
    }
}
