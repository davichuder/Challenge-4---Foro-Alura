package com.david.foro_alura.dto.curso;

import jakarta.validation.constraints.NotNull;

public record EliminarCursoRequest(@NotNull Long id){
}
