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
import com.david.foro_alura.exceptions.ExisteException;
import com.david.foro_alura.repository.CategoriaRepository;
import com.david.foro_alura.repository.CursoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CursoService {
    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    final String nombreEntidadCurso = Curso.class.getSimpleName();
    final String nombreEntidadCategoria = Categoria.class.getSimpleName();

    public Curso nuevo(NuevoCursoRequest nuevoCurso) {
        return cursoRepository
                .save(new Curso(nuevoCurso.nombre(), categoriaRepository.getReferenceById(nuevoCurso.idCategoria())));
    }

    public Page<CursoResponse> listado(Pageable paginacion) {
        return cursoRepository.findAll(paginacion).map(CursoResponse::new);
    }

    public void eliminar(EliminarCursoRequest eliminarCurso) throws ExisteException {
        Optional<Curso> curso = cursoRepository.findById(eliminarCurso.id());
        if (!curso.isPresent()) {
            throw new ExisteException(nombreEntidadCurso);
        }
        cursoRepository.deleteById(eliminarCurso.id());
    }

    public Curso modificar(ModificarCursoRequest modificarCurso) throws ExisteException {
        Optional<Curso> curso = cursoRepository.findById(modificarCurso.id());
        if (!curso.isPresent()) {
            throw new ExisteException(nombreEntidadCurso);
        }
        Optional<Categoria> categoria = categoriaRepository.findById(modificarCurso.idCategoria());
        if (!categoria.isPresent()) {
            throw new ExisteException(nombreEntidadCategoria);
        }
        Curso modificacion = curso.get();
        modificacion.actualizar(modificarCurso.nombre(), categoria.get());
        return modificacion;
    }

    public Curso ver(Long id) {
        Optional<Curso> curso = cursoRepository.findById(id);
        if (!curso.isPresent()) {
            throw new EntityNotFoundException("Error el ID del " + nombreEntidadCurso + " no esta existe");
        }
        return curso.get();
    }
}
