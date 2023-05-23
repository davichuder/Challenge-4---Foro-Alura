package com.david.foro_alura.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.david.foro_alura.dto.categoria.CategoriaResponse;
import com.david.foro_alura.dto.categoria.EliminarCategoriaRequest;
import com.david.foro_alura.dto.categoria.ModificarCategoriaRequest;
import com.david.foro_alura.dto.categoria.NuevaCategoriaRequest;
import com.david.foro_alura.entity.Categoria;
import com.david.foro_alura.exceptions.ExisteException;
import com.david.foro_alura.repository.CategoriaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    final String nombreEntidadCategoria = Categoria.class.getSimpleName();

    public Categoria nueva(NuevaCategoriaRequest nuevaCategoria) {
        return categoriaRepository.save(new Categoria(nuevaCategoria));
    }

    public Page<CategoriaResponse> listado(Pageable paginacion) {
        return categoriaRepository.findAll(paginacion).map(CategoriaResponse::new);
    }

    public void eliminar(EliminarCategoriaRequest eliminarCategoria) throws ExisteException {
        Optional<Categoria> categoria = categoriaRepository.findById(eliminarCategoria.id());
        if (!categoria.isPresent()) {
            throw new ExisteException(nombreEntidadCategoria);
        }
        categoriaRepository.deleteById(eliminarCategoria.id());
    }

    public Categoria modificar(ModificarCategoriaRequest modificarCategoria) throws ExisteException {
        Optional<Categoria> categoria = categoriaRepository.findById(modificarCategoria.id());
        if (!categoria.isPresent()) {
            throw new ExisteException(nombreEntidadCategoria);
        }
        Categoria modificacion = categoriaRepository.getReferenceById(modificarCategoria.id());
        modificacion.actualizar(modificarCategoria);
        return modificacion;
    }

    public Categoria ver(Long id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        if (!categoria.isPresent()) {
            throw new EntityNotFoundException("Error el ID del " + nombreEntidadCategoria + " no esta existe");
        }
        return categoriaRepository.getReferenceById(id);
    }
}
