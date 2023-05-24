package com.david.foro_alura.dto.respuesta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NuevaRespuestaRequest(@NotNull Long idTopico,
                @NotNull Long idUsuario,
                @NotBlank String mensaje) {
}
