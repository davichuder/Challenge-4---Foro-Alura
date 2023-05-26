package com.david.foro_alura.dto.usuario;

import com.david.foro_alura.entity.Usuario;

public record UsuarioResponse(Long id, String email) {
        public UsuarioResponse(Usuario usuario) {
                this(usuario.getId(), usuario.getEmail());
        }
}
