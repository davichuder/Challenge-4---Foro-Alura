package com.david.foro_alura.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.david.foro_alura.dto.curso.CursoResponse;
import com.david.foro_alura.dto.curso.ModificarCursoRequest;
import com.david.foro_alura.dto.curso.NuevoCursoRequest;
import com.david.foro_alura.entity.Categoria;
import com.david.foro_alura.entity.Curso;
import com.david.foro_alura.exceptions.DuplicadoException;
import com.david.foro_alura.exceptions.NoExisteException;
import com.david.foro_alura.repository.CategoriaRepository;
import com.david.foro_alura.repository.CursoRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class CursoService {
    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional
    public Curso nuevo(NuevoCursoRequest nuevoCurso) throws DuplicadoException, NoExisteException {
        if (cursoRepository.existsByNombre(nuevoCurso.nombre())) {
            throw new DuplicadoException("nombre");
        }
        if (!categoriaRepository.existsById(nuevoCurso.idCategoria())) {
            throw new NoExisteException("idCategoria");
        }
        return cursoRepository.save(new Curso(nuevoCurso.nombre(),
                categoriaRepository.getReferenceById(nuevoCurso.idCategoria())));
    }

    @Transactional
    public void eliminar(Long idCurso) throws NoExisteException {
        if (!cursoRepository.existsById(idCurso)) {
            throw new NoExisteException("id");
        }
        cursoRepository.deleteById(idCurso);
    }

    @Transactional
    public Curso modificar(Long idCurso, ModificarCursoRequest modificarCurso) throws NoExisteException {
        Optional<Curso> curso = cursoRepository.findById(idCurso);
        if (!curso.isPresent()) {
            throw new NoExisteException("idCurso");
        }
        Optional<Categoria> categoria = categoriaRepository.findById(modificarCurso.idCategoria());
        if (!categoria.isPresent()) {
            throw new NoExisteException("idCategoria");
        }
        curso.get().modificar(modificarCurso.nombre(), categoria.get());
        return curso.get();
    }

    public Page<CursoResponse> listado(Pageable paginacion) {
        return cursoRepository.findAll(paginacion).map(CursoResponse::new);
    }

    public Page<CursoResponse> listadoPorCategoria(Long idCategoria, Pageable paginacion) throws NoExisteException {
        if (!categoriaRepository.existsById(idCategoria)){
            throw new NoExisteException("idCategoria");
        }
        return cursoRepository.findAllByCategoriaId(idCategoria, paginacion).map(CursoResponse::new);
    }

    public Curso ver(Long id) {
        Optional<Curso> curso = cursoRepository.findById(id);
        if (!curso.isPresent()) {
            throw new EntityNotFoundException("Error el ID del curso no existe");
        }
        return curso.get();
    }
}
