package com.david.foro_alura.controllers;

import java.sql.SQLIntegrityConstraintViolationException;

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

import com.david.foro_alura.dto.curso.CursoResponse;
import com.david.foro_alura.dto.curso.EliminarCursoRequest;
import com.david.foro_alura.dto.curso.ModificarCursoRequest;
import com.david.foro_alura.dto.curso.NuevoCursoRequest;
import com.david.foro_alura.exceptions.ExisteException;
import com.david.foro_alura.services.CursoService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/cursos")
public class CursoController {
    @Autowired
    private CursoService cursoService;

    @PostMapping
    @Transactional
    public ResponseEntity<CursoResponse> nuevoCurso(
            @RequestBody @Valid NuevoCursoRequest nuevoCurso) {
        return ResponseEntity.ok(new CursoResponse(cursoService.nuevo(nuevoCurso)));
    }

    @RequestMapping
    public ResponseEntity<Page<CursoResponse>> listadoCursos(@PageableDefault(size = 10) Pageable paginacion) {
        return ResponseEntity.ok(cursoService.listado(paginacion));
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<Object> eliminarCurso(@RequestBody @Valid EliminarCursoRequest eliminarCurso) throws SQLIntegrityConstraintViolationException, ExisteException {
        cursoService.eliminar(eliminarCurso);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity<CursoResponse> modificarCurso(@RequestBody @Valid ModificarCursoRequest modificarCurso) throws SQLIntegrityConstraintViolationException, ExisteException {
        return ResponseEntity.ok(new CursoResponse(cursoService.modificar(modificarCurso)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoResponse> verCurso(@PathVariable Long id) {
        return ResponseEntity.ok(new CursoResponse(cursoService.ver(id)));
    }
}
