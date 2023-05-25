package com.david.foro_alura.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.david.foro_alura.dto.usuario.DesactivarUsuarioRequest;
import com.david.foro_alura.dto.usuario.ModificarUsuarioRequest;
import com.david.foro_alura.dto.usuario.NuevoUsuarioRequest;
import com.david.foro_alura.dto.usuario.RegistroUsuarioRequest;
import com.david.foro_alura.dto.usuario.UsuarioResponse;
import com.david.foro_alura.entity.Usuario;
import com.david.foro_alura.exceptions.DuplicadoException;
import com.david.foro_alura.exceptions.NoExisteException;
import com.david.foro_alura.repository.UsuarioRepository;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public void registro(RegistroUsuarioRequest registroUsuario) throws DuplicadoException {
        if (usuarioRepository.existsByEmail(registroUsuario.email())) {
            throw new DuplicadoException("email");
        }
        usuarioRepository.save(new Usuario(registroUsuario));
    }

    public Usuario nuevo(NuevoUsuarioRequest nuevoUsuario) throws DuplicadoException {
        if (usuarioRepository.existsByEmail(nuevoUsuario.email())) {
            throw new DuplicadoException("email");
        }
        return usuarioRepository.save(new Usuario(nuevoUsuario));
    }

    public Page<UsuarioResponse> listado(Pageable paginacion) {
        return usuarioRepository.findAllByActivo(true, paginacion).map(UsuarioResponse::new);
    }

    public void desactivar(DesactivarUsuarioRequest desactivarUsuario) throws NoExisteException {
        Optional<Usuario> usuario = usuarioRepository.findById(desactivarUsuario.id());
        if (!usuario.isPresent()) {
            throw new NoExisteException("id");
        }
        Usuario desactivar = usuario.get();
        desactivar.desactivar();
    }

    public Usuario modificar(ModificarUsuarioRequest modificarUsuario) throws NoExisteException, DuplicadoException {
        Optional<Usuario> usuario = usuarioRepository.findById(modificarUsuario.id());
        if (!usuario.isPresent()) {
            throw new NoExisteException("id");
        }
        if (usuarioRepository.existsByEmail(modificarUsuario.email())){
            throw new DuplicadoException("email");
        }
        Usuario modificacion = usuario.get();
        modificacion.actualizar(modificarUsuario);
        return modificacion;
    }

    public Usuario ver(Long id) throws NoExisteException {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (!usuario.isPresent() || !usuario.get().getActivo()) {
            throw new NoExisteException("id");
        }
        return usuario.get();
    }
}
