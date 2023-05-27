package com.david.foro_alura.dto.topico;

import com.david.foro_alura.enums.Tag;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ModificarTopicoRequest(@NotNull Long idCurso,
        @NotBlank String titulo,
        @NotBlank String mensaje,
        @NotNull Tag tag) {
}
