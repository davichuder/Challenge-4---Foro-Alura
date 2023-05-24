package com.david.foro_alura.dto.respuesta;

import jakarta.validation.constraints.NotNull;

public record EliminarRespuestaRequest(@NotNull Long id) {
}
