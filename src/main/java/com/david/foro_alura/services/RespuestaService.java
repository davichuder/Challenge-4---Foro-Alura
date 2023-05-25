package com.david.foro_alura.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.david.foro_alura.dto.respuesta.EliminarRespuestaRequest;
import com.david.foro_alura.dto.respuesta.ModificarRespuestaRequest;
import com.david.foro_alura.dto.respuesta.NuevaRespuestaRequest;
import com.david.foro_alura.dto.respuesta.RespuestaResponse;
import com.david.foro_alura.entity.Respuesta;
import com.david.foro_alura.entity.Topico;
import com.david.foro_alura.entity.Usuario;
import com.david.foro_alura.enums.Estatus;
import com.david.foro_alura.exceptions.DuplicadoException;
import com.david.foro_alura.exceptions.NoExisteException;
import com.david.foro_alura.exceptions.TopicoResueltoException;
import com.david.foro_alura.repository.RespuestaRepository;
import com.david.foro_alura.repository.TopicoRepository;
import com.david.foro_alura.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RespuestaService {
    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    public Respuesta nueva(NuevaRespuestaRequest nuevaRespuesta) throws NoExisteException, DuplicadoException, TopicoResueltoException {
        Optional<Topico> topico = topicoRepository.findById(nuevaRespuesta.idTopico());
        if (!topico.isPresent()){
            throw new NoExisteException("idTopico");
        }
        Topico actualizarTopico = topico.get();
        if (actualizarTopico.getEstatus().equals(Estatus.RESUELTO)){
            throw new TopicoResueltoException(nuevaRespuesta.idTopico());
        }
        Optional<Usuario> usuario = usuarioRepository.findById(nuevaRespuesta.idUsuario());
        if (!usuario.isPresent()){
            throw new NoExisteException("idUsuario");
        }
        if (respuestaRepository.existsByMensaje(nuevaRespuesta.mensaje())){
            throw new DuplicadoException("mensaje");
        }
        Respuesta respuesta = new Respuesta(nuevaRespuesta, usuario.get());
        respuestaRepository.save(respuesta);
        actualizarTopico.agregarRespuesta(respuesta);
        return respuesta;
    }

    public Page<RespuestaResponse> listado(Pageable paginacion) {
        return respuestaRepository.findAll(paginacion).map(RespuestaResponse::new);
    }

    public void eliminar(EliminarRespuestaRequest eliminarRespuesta) throws NoExisteException {
        Optional<Respuesta> respuesta = respuestaRepository.findById(eliminarRespuesta.id());
        if (!respuesta.isPresent()) {
            throw new NoExisteException("id");
        }
        Topico topico = topicoRepository.topicoWithIdRespuesta(eliminarRespuesta.id());
        topico.removerRespuesta(respuesta.get());
        respuestaRepository.deleteById(eliminarRespuesta.id());
    }

    public Respuesta modificar(ModificarRespuestaRequest modificarRespuesta) throws NoExisteException, DuplicadoException {
        Optional<Respuesta> respuesta = respuestaRepository.findById(modificarRespuesta.id());
        if (!respuesta.isPresent()) {
            throw new NoExisteException("id");
        }
        if (respuestaRepository.existsByMensaje(modificarRespuesta.mensaje())){
            throw new DuplicadoException("mensaje");
        }
        Respuesta modificacion = respuesta.get();
        modificacion.actualizar(modificarRespuesta);
        return modificacion;
    }

    public Respuesta ver(Long id) {
        Optional<Respuesta> respuesta = respuestaRepository.findById(id);
        if (!respuesta.isPresent()) {
            throw new EntityNotFoundException("Error el ID de la respuesta no existe");
        }
        return respuesta.get();
    }
}
