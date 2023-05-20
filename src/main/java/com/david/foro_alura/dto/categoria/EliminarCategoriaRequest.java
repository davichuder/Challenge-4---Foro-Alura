package com.david.foro_alura.dto.categoria;

import jakarta.validation.constraints.NotNull;

public record EliminarCategoriaRequest(@NotNull Long id){
}
