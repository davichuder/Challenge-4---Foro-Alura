package com.david.foro_alura.dto.topico;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.david.foro_alura.dto.respuesta.RespuestaResponse;
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
        List<RespuestaResponse> respuestasResponse) {

    public DetallesTopicoResponse(Topico topico) {
        this(topico.getId(),
                topico.getUsuario().getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaDeCreacion(),
                topico.getEstatus(),
                topico.getCurso().getId(),
                topico.getTag(),
                topico.getRespuestas().stream().map(RespuestaResponse::new).collect(Collectors.toList()));
    }
}
