package com.david.foro_alura.dto.curso;

import com.david.foro_alura.entity.Curso;

public record CursoResponse(Long id, String nombre, Long idCategoria) {

    public CursoResponse(Curso curso) {
        this(curso.getId(),
            curso.getNombre(),
            curso.getCategoria().getId());
    }
}
