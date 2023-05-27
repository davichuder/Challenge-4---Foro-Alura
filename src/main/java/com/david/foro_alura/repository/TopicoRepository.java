package com.david.foro_alura.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.david.foro_alura.entity.Topico;
import com.david.foro_alura.enums.Estatus;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    Boolean existsByTitulo(String titulo);

    Boolean existsByMensaje(String mensaje);

    Boolean existsByEstatus(Estatus estatus);

    Page<Topico> findAllByUsuarioId(Long idUsuario, Pageable paginacion);

    Page<Topico> findAllByCursoId(Long idCurso, Pageable paginacion);

    Page<Topico> findAllByEstatus(Estatus status, Pageable paginacion);
}
