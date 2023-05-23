package com.david.foro_alura.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.david.foro_alura.dto.curso.CursoResponse;
import com.david.foro_alura.dto.curso.EliminarCursoRequest;
import com.david.foro_alura.dto.curso.ModificarCursoRequest;
import com.david.foro_alura.dto.curso.NuevoCursoRequest;
import com.david.foro_alura.entity.Categoria;
import com.david.foro_alura.entity.Curso;
import com.david.foro_alura.exceptions.DuplicadoException;
import com.david.foro_alura.exceptions.NoExisteException;
import com.david.foro_alura.repository.CategoriaRepository;
import com.david.foro_alura.repository.CursoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CursoService {
    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Curso nuevo(NuevoCursoRequest nuevoCurso) throws DuplicadoException {
        if (categoriaRepository.existsByNombre(nuevoCurso.nombre())) {
            throw new DuplicadoException("nombre");
        }
        return cursoRepository
                .save(new Curso(nuevoCurso.nombre(), categoriaRepository.getReferenceById(nuevoCurso.idCategoria())));
    }

    public Page<CursoResponse> listado(Pageable paginacion) {
        return cursoRepository.findAll(paginacion).map(CursoResponse::new);
    }

    public void eliminar(EliminarCursoRequest eliminarCurso) throws NoExisteException {
        Optional<Curso> curso = cursoRepository.findById(eliminarCurso.id());
        if (!curso.isPresent()) {
            throw new NoExisteException("id");
        }
        cursoRepository.deleteById(eliminarCurso.id());
    }

    public Curso modificar(ModificarCursoRequest modificarCurso) throws NoExisteException {
        Optional<Curso> curso = cursoRepository.findById(modificarCurso.id());
        if (!curso.isPresent()) {
            throw new NoExisteException("id");
        }
        Optional<Categoria> categoria = categoriaRepository.findById(modificarCurso.idCategoria());
        if (!categoria.isPresent()) {
            throw new NoExisteException("idCategoria");
        }
        Curso modificacion = curso.get();
        modificacion.actualizar(modificarCurso.nombre(), categoria.get());
        return modificacion;
    }

    public Curso ver(Long id) {
        Optional<Curso> curso = cursoRepository.findById(id);
        if (!curso.isPresent()) {
            throw new EntityNotFoundException("Error el ID del curso no existe");
        }
        return curso.get();
    }
}
