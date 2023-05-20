package com.david.foro_alura.entity;

import java.util.Date;

import com.david.foro_alura.enums.Estatus;
import com.david.foro_alura.enums.Tag;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "topicos")
@Data
@NoArgsConstructor
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
    private String titulo;
    private String mensaje;

    @Temporal(TemporalType.TIME)
    private Date fechaDeCreacion;

    private Estatus estatus;

    @OneToOne
    private Usuario usuario;

    @OneToOne
    private Curso curso;

    @Enumerated(EnumType.STRING)
    private Tag tag;
}
