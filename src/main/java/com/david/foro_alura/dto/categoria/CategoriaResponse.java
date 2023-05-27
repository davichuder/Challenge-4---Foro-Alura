package com.david.foro_alura.dto.categoria;

import com.david.foro_alura.entity.Categoria;

public record CategoriaResponse(Long id, String nombre) {
    public CategoriaResponse(Categoria categoria) {
        this(categoria.getId(),
            categoria.getNombre());
    }
}
