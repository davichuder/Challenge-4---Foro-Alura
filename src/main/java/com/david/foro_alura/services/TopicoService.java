package com.david.foro_alura.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.david.foro_alura.dto.topico.EliminarTopicoRequest;
import com.david.foro_alura.dto.topico.ModificarTopicoRequest;
import com.david.foro_alura.dto.topico.NuevoTopicoRequest;
import com.david.foro_alura.dto.topico.SolucionTopicoRequest;
import com.david.foro_alura.dto.topico.TopicoResponse;
import com.david.foro_alura.entity.Respuesta;
import com.david.foro_alura.entity.Topico;
import com.david.foro_alura.enums.Estatus;
import com.david.foro_alura.exceptions.DuplicadoException;
import com.david.foro_alura.exceptions.NoExisteException;
import com.david.foro_alura.exceptions.TopicoResultoException;
import com.david.foro_alura.repository.CursoRepository;
import com.david.foro_alura.repository.RespuestaRepository;
import com.david.foro_alura.repository.TopicoRepository;
import com.david.foro_alura.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TopicoService {
    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Topico nuevo(NuevoTopicoRequest nuevoTopico) throws NoExisteException {
        if (!usuarioRepository.existsById(nuevoTopico.idUsuario())) {
            throw new NoExisteException("idCurso");
        }
        if (!cursoRepository.existsById(nuevoTopico.idCurso())) {
            throw new NoExisteException("idCurso");
        }
        return topicoRepository
                .save(new Topico(nuevoTopico, usuarioRepository.getReferenceById(nuevoTopico.idUsuario()),
                        cursoRepository.getReferenceById(nuevoTopico.idCurso())));
        
    }

    public Page<TopicoResponse> listado(Pageable paginacion) {
        return topicoRepository.findAll(paginacion).map(TopicoResponse::new);
    }

    public void eliminar(EliminarTopicoRequest eliminarTopico) throws NoExisteException {
        if (!topicoRepository.existsById(eliminarTopico.id())) {
            throw new NoExisteException("id");
        }
        topicoRepository.deleteById(eliminarTopico.id());
    }

    public Topico modificar(ModificarTopicoRequest modificarTopico) throws DuplicadoException, NoExisteException {
        Optional<Topico> topico = topicoRepository.findById(modificarTopico.idTopico());
        if (!topico.isPresent()) {
            throw new DuplicadoException("idTopico");
        }
        if (!cursoRepository.existsById(modificarTopico.idCurso())) {
            throw new NoExisteException("idCurso");
        }
        if (!topicoRepository.existsByTitulo(modificarTopico.titulo())) {
            throw new DuplicadoException("titulo");
        }
        if (!topicoRepository.existsByMensaje(modificarTopico.mensaje())) {
            throw new DuplicadoException("mensaje");
        }
        Topico modificacion = topico.get();
        modificacion.actualizar(modificarTopico,
                cursoRepository.getReferenceById(modificarTopico.idCurso()));
        return modificacion;
    }

    public Topico ver(Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);
        if (!topico.isPresent()) {
            throw new EntityNotFoundException("Error el ID del topico no existe");
        }
        return topico.get();
    }

    public Topico marcarComoSolucion(Long idTopico, SolucionTopicoRequest solucionTopico)
            throws NoExisteException, TopicoResultoException {
        Optional<Topico> topico = topicoRepository.findById(idTopico);
        if (!topico.isPresent()) {
            throw new NoExisteException("idTopico");
        }
        Topico topicoSolucion = topico.get();
        if (topicoSolucion.getEstatus().equals(Estatus.RESUELTO)) {
            throw new TopicoResultoException(idTopico);
        }
        Optional<Respuesta> respuesta = respuestaRepository.findById(solucionTopico.idSolucion());
        if (!respuesta.isPresent()) {
            throw new NoExisteException("idRespuesta");
        }
        Respuesta respuestaSolucion = respuesta.get();
        topicoSolucion.marcarComoResuelto();
        respuestaSolucion.marcarComoSolucion();
        return topicoSolucion;
    }
}