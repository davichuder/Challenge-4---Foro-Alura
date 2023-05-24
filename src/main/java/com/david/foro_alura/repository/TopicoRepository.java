package com.david.foro_alura.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.david.foro_alura.entity.Topico;

public interface TopicoRepository extends JpaRepository<Topico, Long>{
    Boolean existsByTitulo(String titulo);

    Boolean existsByMensaje(String mensaje);
}
