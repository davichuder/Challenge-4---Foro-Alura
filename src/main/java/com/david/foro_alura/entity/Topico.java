package com.david.foro_alura.entity;

import java.util.Date;

import com.david.foro_alura.dto.topico.ModificarTopicoRequest;
import com.david.foro_alura.dto.topico.NuevoTopicoRequest;
import com.david.foro_alura.enums.Estatus;
import com.david.foro_alura.enums.Tag;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne
    private Usuario usuario;
    
    private String titulo;
    private String mensaje;

    @Temporal(TemporalType.TIME)
    private Date fechaDeCreacion;

    @Enumerated(EnumType.STRING)
    private Estatus estatus;

    @ManyToOne
    private Curso curso;

    @Enumerated(EnumType.STRING)
    private Tag tag;

    public Topico(NuevoTopicoRequest nuevoTopico, Usuario usuario, Curso curso) {
        this.usuario = usuario;
        this.titulo = nuevoTopico.titulo();
        this.mensaje = nuevoTopico.mensaje();
        this.fechaDeCreacion = new Date();
        this.estatus = Estatus.SIN_RESPUESTAS;
        this.curso = curso;
        this.tag = nuevoTopico.tag();
    }


    public void modificar(ModificarTopicoRequest modificarTopico, Curso curso) {
        this.titulo = modificarTopico.titulo();
        this.mensaje = modificarTopico.mensaje();
        this.tag = modificarTopico.tag();
        this.curso = curso;
    }

    public void marcarComoResuelto() {
        this.estatus = Estatus.RESUELTO;
    }
}
