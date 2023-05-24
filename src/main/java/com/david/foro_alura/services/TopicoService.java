package com.david.foro_alura.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.david.foro_alura.dto.topico.EliminarTopicoRequest;
import com.david.foro_alura.dto.topico.ModificarTopicoRequest;
import com.david.foro_alura.dto.topico.NuevoTopicoRequest;
import com.david.foro_alura.dto.topico.TopicoResponse;
import com.david.foro_alura.entity.Curso;
import com.david.foro_alura.entity.Topico;
import com.david.foro_alura.entity.Usuario;
import com.david.foro_alura.exceptions.DuplicadoException;
import com.david.foro_alura.exceptions.NoExisteException;
import com.david.foro_alura.repository.CursoRepository;
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
    private UsuarioRepository usuarioRepository;

    public Topico nuevo(NuevoTopicoRequest nuevoTopico) throws NoExisteException, DuplicadoException {
        Optional<Usuario> usuario = usuarioRepository.findById(nuevoTopico.idUsuario());
        if (!usuario.isPresent()){
            throw new NoExisteException("idUsuario");
        }
        Optional<Curso> curso = cursoRepository.findById(nuevoTopico.idCurso());
        if (!curso.isPresent()){
            throw new NoExisteException("idCurso");
        }
        if (topicoRepository.existsByTitulo(nuevoTopico.titulo())){
            throw new DuplicadoException("titulo");
        }
        if (topicoRepository.existsByMensaje(nuevoTopico.mensaje())){
            throw new DuplicadoException("mensaje");
        }
        return topicoRepository.save(new Topico(nuevoTopico, usuario.get(), curso.get()));
    }

    public Page<TopicoResponse> listado(Pageable paginacion) {
        return topicoRepository.findAll(paginacion).map(TopicoResponse::new);
    }

    public void eliminar(EliminarTopicoRequest eliminarTopico) throws NoExisteException {
        Optional<Topico> topico = topicoRepository.findById(eliminarTopico.id());
        if (!topico.isPresent()) {
            throw new NoExisteException("idTopico");
        }
        topicoRepository.deleteById(eliminarTopico.id());
    }

    public Topico modificar(ModificarTopicoRequest modificarTopico) throws DuplicadoException, NoExisteException{
        Optional<Topico> topico = topicoRepository.findById(modificarTopico.idTopico());
        if (!topico.isPresent()) {
            throw new NoExisteException("idTopico");
        }
        Optional<Curso> curso = cursoRepository.findById(modificarTopico.idCurso());
        if (!curso.isPresent()){
            throw new NoExisteException("idCurso");
        }
        if (topicoRepository.existsByTitulo(modificarTopico.titulo())){
            throw new DuplicadoException("titulo");
        }
        if (topicoRepository.existsByMensaje(modificarTopico.mensaje())){
            throw new DuplicadoException("mensaje");
        }
        Topico modificacion = topicoRepository.getReferenceById(modificarTopico.idTopico());
        modificacion.actualizar(modificarTopico, curso.get());
        return modificacion;
    }

    public Topico ver(Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);
        if (!topico.isPresent()) {
            throw new EntityNotFoundException("Error el ID del topico no existe");
        }
        return topicoRepository.getReferenceById(id);
    }
}