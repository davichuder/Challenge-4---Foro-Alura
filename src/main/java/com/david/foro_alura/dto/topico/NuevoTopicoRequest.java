package com.david.foro_alura.dto.topico;

import jakarta.validation.constraints.NotBlank;

public record NuevoTopicoRequest(@NotBlank String titulo,
                                @NotBlank String mensaje) {
}
