package com.david.foro_alura.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ModificarUsuarioRequest(@NotNull Long id,
                @NotBlank String email,
                @NotNull String password) {
}
