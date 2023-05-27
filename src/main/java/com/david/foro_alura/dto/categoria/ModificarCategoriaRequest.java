package com.david.foro_alura.dto.categoria;

import jakarta.validation.constraints.NotBlank;

public record ModificarCategoriaRequest(@NotBlank String nombre){
}
