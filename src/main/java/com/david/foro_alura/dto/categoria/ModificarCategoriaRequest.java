package com.david.foro_alura.dto.categoria;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ModificarCategoriaRequest(@NotNull Long id, @NotBlank String nombre){
}
