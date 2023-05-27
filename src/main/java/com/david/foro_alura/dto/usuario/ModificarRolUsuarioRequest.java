package com.david.foro_alura.dto.usuario;

import com.david.foro_alura.enums.Rol;

import jakarta.validation.constraints.NotNull;

public record ModificarRolUsuarioRequest(@NotNull Rol rol) {
}
