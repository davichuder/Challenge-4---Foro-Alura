package com.david.foro_alura.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.david.foro_alura.entity.Respuesta;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long>{
    Boolean existsByMensaje(String mensaje);

    Page<Respuesta> findAllByTopicoId(Long idTopico, Pageable paginacion);

    Page<Respuesta> findAllByUsuarioId(Long idUsuario, Pageable paginacion);
}
