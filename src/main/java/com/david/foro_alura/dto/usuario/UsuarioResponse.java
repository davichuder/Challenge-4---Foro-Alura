package com.david.foro_alura.dto.usuario;

import com.david.foro_alura.entity.Usuario;
import com.david.foro_alura.enums.Rol;

public record UsuarioResponse(Long id,
                String email,
                Rol rol) {
        public UsuarioResponse(Usuario usuario) {
                this(usuario.getId(),
                                usuario.getEmail(),
                                usuario.getRol());
        }
}
