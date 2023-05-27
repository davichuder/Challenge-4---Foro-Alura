package com.david.foro_alura.dto.respuesta;

import java.util.Date;

import com.david.foro_alura.entity.Respuesta;

public record RespuestaResponse(Long id,
        Long idUsuario,
        Long idTopico,
        String mensaje,
        Date fechaDeCreacion,
        Boolean mejorRespuesta) {
    public RespuestaResponse(Respuesta respuesta) {
        this(respuesta.getId(),
                respuesta.getUsuario().getId(),
                respuesta.getTopico().getId(),
                respuesta.getMensaje(),
                respuesta.getFechaDeCreacion(),
                respuesta.getMejorRespuesta());
    }
}
