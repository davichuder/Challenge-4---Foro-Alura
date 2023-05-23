package com.david.foro_alura.dto.curso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NuevoCursoRequest(@NotBlank String nombre, @NotNull Long idCategoria) {
}
