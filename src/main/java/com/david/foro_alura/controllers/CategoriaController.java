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

import com.david.foro_alura.dto.categoria.NuevaCategoriaRequest;
import com.david.foro_alura.dto.categoria.CategoriaResponse;
import com.david.foro_alura.dto.categoria.EliminarCategoriaRequest;
import com.david.foro_alura.dto.categoria.ModificarCategoriaRequest;
import com.david.foro_alura.entity.Categoria;
import com.david.foro_alura.repository.CategoriaRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<CategoriaResponse> nuevaCategoria(
            @RequestBody @Valid NuevaCategoriaRequest nuevaCategoria) {
        Categoria nueva = categoriaRepository.save(new Categoria(nuevaCategoria));
        return ResponseEntity.ok(new CategoriaResponse(nueva));
    }

    @RequestMapping
    public ResponseEntity<Page<CategoriaResponse>> listadoCategorias(@PageableDefault(size = 10) Pageable paginacion){
        return ResponseEntity.ok(categoriaRepository.findAll(paginacion).map(CategoriaResponse::new));
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<Object> eliminarCategoria(@RequestBody @Valid EliminarCategoriaRequest eliminarCategoria){       
        categoriaRepository.deleteById(eliminarCategoria.id());
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity<CategoriaResponse> modificarCategoria(@RequestBody @Valid ModificarCategoriaRequest modificarCategoria){
        Categoria modificacion = categoriaRepository.getReferenceById(modificarCategoria.id());
        modificacion.actualizar(modificarCategoria);
        return ResponseEntity.ok(new CategoriaResponse(modificacion));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponse> verCategoria(@PathVariable Long id){
        Categoria categoria = categoriaRepository.getReferenceById(id);
        return ResponseEntity.ok(new CategoriaResponse(categoria));
    }
}
