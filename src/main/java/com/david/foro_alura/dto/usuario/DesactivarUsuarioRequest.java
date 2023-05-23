package com.david.foro_alura.dto.usuario;

import jakarta.validation.constraints.NotNull;

public record DesactivarUsuarioRequest(@NotNull Long id) {
}
