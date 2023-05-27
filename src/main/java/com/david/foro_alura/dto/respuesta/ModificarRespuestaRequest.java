package com.david.foro_alura.dto.respuesta;

import jakarta.validation.constraints.NotBlank;

public record ModificarRespuestaRequest(@NotBlank String mensaje) {
}
