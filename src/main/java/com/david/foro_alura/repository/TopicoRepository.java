package com.david.foro_alura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.david.foro_alura.entity.Topico;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    Boolean existsByTitulo(String titulo);

    Boolean existsByMensaje(String mensaje);

    @Query("SELECT t FROM Topico t JOIN t.respuestas r WHERE r.id = :idRespuesta")
    Topico topicoWithIdRespuesta(@Param("idRespuesta") Long id);

}
