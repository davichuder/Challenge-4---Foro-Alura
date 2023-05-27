package com.david.foro_alura.dto.topico;

import java.util.Date;

import com.david.foro_alura.entity.Topico;
import com.david.foro_alura.enums.Estatus;
import com.david.foro_alura.enums.Tag;

public record TopicoResponse(Long id,
        Long idUsuario,
        Long idCurso,
        String titulo,
        String mensaje,
        Date fechaDeCreacion,
        Estatus estatus,
        Tag tag) {

    public TopicoResponse(Topico topico) {
        this(topico.getId(),
                topico.getUsuario().getId(),
                topico.getCurso().getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaDeCreacion(),
                topico.getEstatus(),
                topico.getTag());
    }
}
