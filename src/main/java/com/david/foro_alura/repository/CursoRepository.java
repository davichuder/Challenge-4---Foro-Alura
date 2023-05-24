package com.david.foro_alura.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.david.foro_alura.entity.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long>{
}
