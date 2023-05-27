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

import com.david.foro_alura.dto.categoria.NuevaCategoriaRequest;
import com.david.foro_alura.exceptions.DuplicadoException;
import com.david.foro_alura.exceptions.NoExisteException;
import com.david.foro_alura.dto.categoria.CategoriaResponse;
import com.david.foro_alura.dto.categoria.ModificarCategoriaRequest;
import com.david.foro_alura.services.CategoriaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categorias")
@PreAuthorize("isAuthenticated()")
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CategoriaResponse> nuevaCategoria(
            @RequestBody @Valid NuevaCategoriaRequest nuevaCategoria) throws DuplicadoException {
        return ResponseEntity.ok(new CategoriaResponse(categoriaService.nueva(nuevaCategoria)));
    }

    @DeleteMapping("/{idCategoria}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> eliminarCategoria(@PathVariable Long idCategoria) throws NoExisteException {
        categoriaService.eliminar(idCategoria);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{idCategoria}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CategoriaResponse> modificarCategoria(@PathVariable Long idCategoria,
            @RequestBody @Valid ModificarCategoriaRequest modificarCategoria) throws NoExisteException, DuplicadoException {
        return ResponseEntity.ok(new CategoriaResponse(categoriaService.modificar(idCategoria, modificarCategoria)));
    }

    @GetMapping("/{idCategoria}")
    public ResponseEntity<CategoriaResponse> verCategoria(@PathVariable Long idCategoria) throws NoExisteException {
        return ResponseEntity.ok(new CategoriaResponse(categoriaService.ver(idCategoria)));
    }

    @GetMapping
    public ResponseEntity<Page<CategoriaResponse>> listadoCategorias(@PageableDefault(size = 10) Pageable paginacion) {
        return ResponseEntity.ok(categoriaService.listado(paginacion));
    }
}
