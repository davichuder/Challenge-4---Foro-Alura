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
import com.david.foro_alura.entity.Curso;
import com.david.foro_alura.entity.Respuesta;
import com.david.foro_alura.entity.Topico;
import com.david.foro_alura.enums.Estatus;
import com.david.foro_alura.exceptions.DuplicadoException;
import com.david.foro_alura.exceptions.NoExisteException;
import com.david.foro_alura.exceptions.RespuestaNoCorrespondeException;
import com.david.foro_alura.exceptions.TopicoResueltoException;
import com.david.foro_alura.repository.CursoRepository;
import com.david.foro_alura.repository.RespuestaRepository;
import com.david.foro_alura.repository.TopicoRepository;
import com.david.foro_alura.repository.UsuarioRepository;

import jakarta.validation.Valid;

@Service
public class TopicoService {
    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RespuestaRepository respuestaRepository;

    public Topico nuevo(NuevoTopicoRequest nuevoTopico) throws NoExisteException, DuplicadoException {
        if (!cursoRepository.existsById(nuevoTopico.idCurso())) {
            throw new NoExisteException("idCurso");
        }
        if (!usuarioRepository.existsById(nuevoTopico.idUsuario())) {
            throw new NoExisteException("idUsuario");
        }
        if (topicoRepository.existsByTitulo(nuevoTopico.titulo())) {
            throw new DuplicadoException("titulo");
        }
        if (topicoRepository.existsByMensaje(nuevoTopico.mensaje())) {
            throw new DuplicadoException("mensaje");
        }
        return topicoRepository.save(
                new Topico(nuevoTopico, usuarioRepository.getReferenceById(nuevoTopico.idUsuario()),
                        cursoRepository.getReferenceById(nuevoTopico.idCurso())));
    }

    public Page<TopicoResponse> listado(Pageable paginacion) {
        return topicoRepository.findAll(paginacion).map(TopicoResponse::new);
    }

    public void eliminar(EliminarTopicoRequest eliminarTopico) throws NoExisteException {
        if (!topicoRepository.existsById(eliminarTopico.id())){
            throw new NoExisteException("id");
        }
        topicoRepository.deleteById(eliminarTopico.id());
    }

    public Topico modificar(ModificarTopicoRequest modificarTopico) throws NoExisteException, DuplicadoException {
        Optional<Topico> topico = topicoRepository.findById(modificarTopico.idTopico());
        if (!topico.isPresent()){
            throw new NoExisteException("idTopico");
        }
        if (topicoRepository.existsByTitulo(modificarTopico.titulo())) {
            throw new DuplicadoException("titulo");
        }
        if (topicoRepository.existsByMensaje(modificarTopico.mensaje())) {
            throw new DuplicadoException("mensaje");
        }
        Optional<Curso> curso = cursoRepository.findById(modificarTopico.idCurso());
        if (!curso.isPresent()) {
            throw new NoExisteException("idCurso");
        }
        Topico modificacion = topico.get();
        modificacion.actualizar(modificarTopico, curso.get());
        return modificacion;
    }

    public Topico ver(Long id) throws NoExisteException {
        Optional<Topico> topico = topicoRepository.findById(id);
        if (!topico.isPresent()){
            throw new NoExisteException("id");
        }
        return topico.get();
    }

    public Topico marcarComoSolucion(Long idTopico, @Valid SolucionTopicoRequest solucionTopico) throws NoExisteException, RespuestaNoCorrespondeException, TopicoResueltoException {
        Optional<Topico> topico = topicoRepository.findById(idTopico);
        if (!topico.isPresent()){
            throw new NoExisteException("idTopico");
        }
        Topico topicoResuelto = topico.get();
        if (topicoResuelto.getEstatus().equals(Estatus.RESUELTO)){
            throw new TopicoResueltoException(idTopico);
        }
        Optional<Respuesta> respuesta = respuestaRepository.findById(solucionTopico.idRespuesta());
        if (!respuesta.isPresent()){
            throw new NoExisteException("idRespuesta");
        }
        Respuesta respuestaSolucion = respuesta.get();
        if (!topicoResuelto.getRespuestas().contains(respuestaSolucion)){
            throw new RespuestaNoCorrespondeException(idTopico, idTopico);
        }
        topicoResuelto.marcarComoResuelto();
        respuestaSolucion.marcarComoSolucion();
        return topico.get();
    }
}