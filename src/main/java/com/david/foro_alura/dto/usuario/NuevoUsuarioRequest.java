package com.david.foro_alura.dto.usuario;

import com.david.foro_alura.enums.Rol;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NuevoUsuarioRequest(@NotBlank @Email String email,
        @NotBlank String password,
        @NotNull Rol rol) {
}