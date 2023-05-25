package com.david.foro_alura.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ModificarUsuarioRequest(@NotNull Long id,
        @NotBlank @Email String email,
        @NotNull String password) {
}
