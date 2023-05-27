package com.david.foro_alura.dto.respuesta;

import jakarta.validation.constraints.NotNull;

public record SolucionRespuestaRequest(@NotNull Long idTopico, @NotNull Long idRespuesta) {
}
