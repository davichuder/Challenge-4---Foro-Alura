package com.david.foro_alura.dto.topico;

import java.util.Date;
import java.util.List;

import com.david.foro_alura.entity.Respuesta;
import com.david.foro_alura.entity.Topico;
import com.david.foro_alura.enums.Estatus;
import com.david.foro_alura.enums.Tag;

public record TopicoResponse(Long id,
        Long idUsuario,
        String titulo,
        String mensaje,
        Date fechaDeCreacion,
        Estatus estatus,
        Long idCurso,
        Tag tag,
        List<Respuesta> respuestas) {

    public TopicoResponse(Topico topico) {
        this(topico.getId(),
                topico.getUsuario().getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaDeCreacion(),
                topico.getEstatus(),
                topico.getCurso().getId(),
                topico.getTag(),
                topico.getRespuestas());
    }
}
