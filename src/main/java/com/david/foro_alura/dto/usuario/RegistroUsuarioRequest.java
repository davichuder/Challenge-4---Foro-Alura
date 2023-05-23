package com.david.foro_alura.dto.usuario;

import jakarta.validation.constraints.NotBlank;

public record RegistroUsuarioRequest(@NotBlank String email,
        @NotBlank String password) {
}
