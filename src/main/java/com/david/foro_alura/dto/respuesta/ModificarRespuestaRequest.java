package com.david.foro_alura.dto.respuesta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ModificarRespuestaRequest(@NotNull Long id,
        @NotBlank String mensaje) {
}
