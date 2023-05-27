package com.david.foro_alura.dto.respuesta;

import jakarta.validation.constraints.NotBlank;

public record NuevaRespuestaRequest(@NotBlank String mensaje) {
}
