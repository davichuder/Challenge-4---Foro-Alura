package com.david.foro_alura.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.david.foro_alura.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    Boolean existsByEmail(String email);

    Page<Usuario> findAllByActivo(boolean activo, Pageable pageable);
}
