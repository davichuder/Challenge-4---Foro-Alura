package com.david.foro_alura.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.david.foro_alura.dto.categoria.CategoriaResponse;
import com.david.foro_alura.dto.categoria.ModificarCategoriaRequest;
import com.david.foro_alura.dto.categoria.NuevaCategoriaRequest;
import com.david.foro_alura.entity.Categoria;
import com.david.foro_alura.exceptions.DuplicadoException;
import com.david.foro_alura.exceptions.NoExisteException;
import com.david.foro_alura.repository.CategoriaRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional
    public Categoria nueva(NuevaCategoriaRequest nuevaCategoria) throws DuplicadoException {
        if (categoriaRepository.existsByNombre(nuevaCategoria.nombre())){
            throw new DuplicadoException("nombre");
        }
        return categoriaRepository.save(new Categoria(nuevaCategoria));
    }

    @Transactional
    public void eliminar(Long idCategoria) throws NoExisteException {
        if (!categoriaRepository.existsById(idCategoria)) {
            throw new NoExisteException("idCategoria");
        }
        categoriaRepository.deleteById(idCategoria);
    }

    @Transactional
    public Categoria modificar(Long idCategoria, ModificarCategoriaRequest modificarCategoria) throws NoExisteException, DuplicadoException {
        Optional<Categoria> categoria = categoriaRepository.findById(idCategoria);
        if (!categoria.isPresent()) {
            throw new NoExisteException("idCategoria");
        }
        if (categoriaRepository.existsByNombre(modificarCategoria.nombre())){
            throw new DuplicadoException("idCategoria");
        }
        Categoria modificacion = categoria.get();
        modificacion.modificar(modificarCategoria);
        return modificacion;
    }

    public Categoria ver(Long idCategoria) {
        Optional<Categoria> categoria = categoriaRepository.findById(idCategoria);
        if (!categoria.isPresent()) {
            throw new EntityNotFoundException("Error el ID de la categoria no existe");
        }
        return categoria.get();
    }

    public Page<CategoriaResponse> listado(Pageable paginacion) {
        return categoriaRepository.findAll(paginacion).map(CategoriaResponse::new);
    }
}
