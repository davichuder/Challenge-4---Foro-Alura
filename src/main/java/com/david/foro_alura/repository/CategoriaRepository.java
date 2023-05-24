package com.david.foro_alura.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.david.foro_alura.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
    Boolean existsByNombre(String nombre);
}
