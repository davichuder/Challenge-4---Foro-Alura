package com.david.foro_alura.dto.topico;

import com.david.foro_alura.enums.Tag;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NuevoTopicoRequest(@NotNull Long idUsuario,
                @NotNull Long idCurso,
                @NotNull Tag tag,
                @NotBlank String titulo,
                @NotBlank String mensaje) {
}
