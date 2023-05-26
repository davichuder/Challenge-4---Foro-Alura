package com.david.foro_alura.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.david.foro_alura.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    Boolean existsByEmail(String email);

    Page<Usuario> findAllByActivo(boolean activo, Pageable pageable);

    UserDetails findByEmail(String email);
}
