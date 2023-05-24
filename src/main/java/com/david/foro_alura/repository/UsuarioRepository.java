package com.david.foro_alura.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.david.foro_alura.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    Boolean existsByEmail(String email);
}
