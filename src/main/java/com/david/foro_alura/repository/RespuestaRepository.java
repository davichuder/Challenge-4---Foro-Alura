package com.david.foro_alura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.david.foro_alura.entity.Respuesta;

@Repository
public interface RespuestaRepository extends JpaRepository<Respuesta, Long>{
}
