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
import com.david.foro_alura.exceptions.NoExisteException;
import com.david.foro_alura.repository.CursoRepository;
import com.david.foro_alura.repository.TopicoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TopicoService {
    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    final String nombreEntidadTopico = Topico.class.getSimpleName();

    public Topico nuevo(NuevoTopicoRequest nuevoTopico) throws NoExisteException {
        Optional<Curso> curso = cursoRepository.findById(nuevoTopico.idCurso());
        if (!curso.isPresent()){
            throw new NoExisteException(nombreEntidadTopico);
        }
        return topicoRepository.save(new Topico(nuevoTopico, curso.get()));
    }

    public Page<TopicoResponse> listado(Pageable paginacion) {
        return topicoRepository.findAll(paginacion).map(TopicoResponse::new);
    }

    public void eliminar(EliminarTopicoRequest eliminarTopico) throws NoExisteException {
        Optional<Topico> topico = topicoRepository.findById(eliminarTopico.id());
        if (!topico.isPresent()) {
            throw new NoExisteException(nombreEntidadTopico);
        }
        topicoRepository.deleteById(eliminarTopico.id());
    }

    // public Topico modificar(ModificarTopicoRequest modificarTopico) throws ExisteException {
    //     Optional<Topico> topico = topicoRepository.findById(modificarTopico.id());
    //     if (!topico.isPresent()) {
    //         throw new ExisteException(nombreEntidadTopico);
    //     }
    //     Topico modificacion = topicoRepository.getReferenceById(modificarTopico.id());
    //     modificacion.actualizar(modificarTopico);
    //     return modificacion;
    // }

    public Topico ver(Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);
        if (!topico.isPresent()) {
            throw new EntityNotFoundException("Error el ID del " + nombreEntidadTopico + " no esta existe");
        }
        return topicoRepository.getReferenceById(id);
    }
}