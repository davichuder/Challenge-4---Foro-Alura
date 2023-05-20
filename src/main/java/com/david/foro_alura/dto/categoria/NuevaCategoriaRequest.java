package com.david.foro_alura.dto.categoria;

import jakarta.validation.constraints.NotBlank;

public record NuevaCategoriaRequest(@NotBlank String nombre) {
}
