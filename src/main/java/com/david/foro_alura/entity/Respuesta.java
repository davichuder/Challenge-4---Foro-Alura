package com.david.foro_alura.entity;

import java.util.Date;

import com.david.foro_alura.dto.respuesta.ModificarRespuestaRequest;
import com.david.foro_alura.dto.respuesta.NuevaRespuestaRequest;

import jakarta.persistence.Entity;
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
@Table(name = "respuestas")
@Data
@NoArgsConstructor
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario usuario;

    private String mensaje;

    @Temporal(TemporalType.TIME)
    private Date fechaDeCreacion;

    private Boolean mejorRespuesta;

    @ManyToOne
    private Topico topico;

    public Respuesta(NuevaRespuestaRequest nuevaRespuesta, Usuario usuario, Topico topico) {
        this.usuario = usuario;
        this.mensaje = nuevaRespuesta.mensaje();
        this.fechaDeCreacion = new Date();
        this.mejorRespuesta = false;
        this.topico = topico;
    }

    public void modificar(ModificarRespuestaRequest modificarRespuesta) {
        this.mensaje = modificarRespuesta.mensaje();
    }

    public void marcarComoSolucion() {
        this.mejorRespuesta = true;
    }
}
