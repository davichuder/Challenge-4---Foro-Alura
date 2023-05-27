package com.david.foro_alura.dto.topico;

import java.util.Date;

import com.david.foro_alura.entity.Topico;
import com.david.foro_alura.enums.Estatus;
import com.david.foro_alura.enums.Tag;

public record DetallesTopicoResponse(Long id,
        Long idUsuario,
        String titulo,
        String mensaje,
        Date fechaDeCreacion,
        Estatus estatus,
        Long idCurso,
        Tag tag,
        Integer cantidadRespuestas) {

    public DetallesTopicoResponse(Topico topico) {
        this(topico.getId(),
                topico.getUsuario().getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaDeCreacion(),
                topico.getEstatus(),
                topico.getCurso().getId(),
                topico.getTag(),
                null);
    }
}
