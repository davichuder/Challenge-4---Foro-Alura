package com.david.foro_alura.entity;

import com.david.foro_alura.dto.categoria.ModificarCategoriaRequest;
import com.david.foro_alura.dto.categoria.NuevaCategoriaRequest;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categorias")
@Data
@NoArgsConstructor
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;

    public Categoria(NuevaCategoriaRequest nuevaCategoria) {
        this.nombre = nuevaCategoria.nombre();
    }

    public Categoria(ModificarCategoriaRequest modificarCategoria) {
        this.nombre = modificarCategoria.nombre();
    }

    public void actualizar(ModificarCategoriaRequest modificarCategoria) {
        this.nombre = modificarCategoria.nombre();
    }
}
