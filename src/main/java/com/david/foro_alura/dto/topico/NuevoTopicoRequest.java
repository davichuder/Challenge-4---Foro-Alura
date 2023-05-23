package com.david.foro_alura.dto.topico;

import com.david.foro_alura.entity.Usuario;
import com.david.foro_alura.enums.Tag;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NuevoTopicoRequest(@NotNull Usuario usuario,
        @NotBlank String titulo,
        @NotBlank String mensaje,
        @NotNull Long idCurso,
        @NotNull Tag tag) {
}
