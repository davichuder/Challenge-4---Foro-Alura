package com.david.foro_alura.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.david.foro_alura.entity.Respuesta;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long>{
    Boolean existsByMensaje(String mensaje);
}
