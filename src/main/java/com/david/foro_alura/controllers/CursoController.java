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

import com.david.foro_alura.dto.curso.CursoResponse;
import com.david.foro_alura.dto.curso.ModificarCursoRequest;
import com.david.foro_alura.dto.curso.NuevoCursoRequest;
import com.david.foro_alura.exceptions.DuplicadoException;
import com.david.foro_alura.exceptions.NoExisteException;
import com.david.foro_alura.services.CursoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "5. Cursos", description = "Authenticated Access")
@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping("/cursos")
@PreAuthorize("isAuthenticated()")
public class CursoController {
    @Autowired
    private CursoService cursoService;

    @Operation(summary = "Nueva curso (Admin)", description = "Admin puede crear nuevo curso")
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CursoResponse> nuevoCurso(
            @RequestBody @Valid NuevoCursoRequest nuevoCurso) throws DuplicadoException, NoExisteException {
        return ResponseEntity.ok(new CursoResponse(cursoService.nuevo(nuevoCurso)));
    }

    @Operation(summary = "Eliminar curso (Admin)", description = "Admin puede eliminar el curso")
    @DeleteMapping("/id/{idCurso}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> eliminarCurso(@PathVariable Long idCurso) throws NoExisteException {
        cursoService.eliminar(idCurso);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Modificar curso (Admin)", description = "Admin puede modicar el nombre y categoria del curso")
    @PutMapping("/id/{idCurso}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CursoResponse> modificarCurso(@PathVariable Long idCurso,
            @RequestBody @Valid ModificarCursoRequest modificarCurso) throws NoExisteException {
        return ResponseEntity.ok(new CursoResponse(cursoService.modificar(idCurso, modificarCurso)));
    }

    @Operation(summary = "Lista de cursos", description = "Cualquier usuario puede obtener el listado de todos los cursos")
    @GetMapping
    public ResponseEntity<Page<CursoResponse>> listadoCursos(@PageableDefault(size = 10) Pageable paginacion) {
        return ResponseEntity.ok(cursoService.listado(paginacion));
    }

    @Operation(summary = "Lista de cursos por categoria", description = "Cualquier usuario puede obtener todos los cursos de una categoria especifica")
    @GetMapping("/categoria/{idCategoria}")
    public ResponseEntity<Page<CursoResponse>> listadoCursosPorCategoria(@PathVariable Long idCategoria,
            @PageableDefault(size = 10) Pageable paginacion) throws NoExisteException {
        return ResponseEntity.ok(cursoService.listadoPorCategoria(idCategoria, paginacion));
    }

    @Operation(summary = "Detalles de curso", description = "")
    @GetMapping("/id/{id}")
    public ResponseEntity<CursoResponse> verCurso(@PathVariable Long id) {
        return ResponseEntity.ok(new CursoResponse(cursoService.ver(id)));
    }
}
