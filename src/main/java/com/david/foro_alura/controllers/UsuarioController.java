package com.david.foro_alura.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.david.foro_alura.dto.usuario.DesactivarUsuarioRequest;
import com.david.foro_alura.dto.usuario.ModificarUsuarioRequest;
import com.david.foro_alura.dto.usuario.UsuarioResponse;
import com.david.foro_alura.exceptions.DuplicadoException;
import com.david.foro_alura.exceptions.NoExisteException;
import com.david.foro_alura.services.UsuarioService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @RequestMapping
    public ResponseEntity<Page<UsuarioResponse>> listadoUsuarios(@PageableDefault(size = 10) Pageable paginacion) {
        return ResponseEntity.ok(usuarioService.listado(paginacion));
    }
    
    @DeleteMapping
    @Transactional
    public ResponseEntity<Object> desactivarUsuario(@RequestBody @Valid DesactivarUsuarioRequest desactivarUsuario)
            throws NoExisteException {
        usuarioService.desactivar(desactivarUsuario);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity<UsuarioResponse> modificarUsuario(
            @RequestBody @Valid ModificarUsuarioRequest modificarUsuario) throws NoExisteException, DuplicadoException {
        return ResponseEntity.ok(new UsuarioResponse(usuarioService.modificar(modificarUsuario)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> verUsuario(@PathVariable Long id) throws NoExisteException {
        return ResponseEntity.ok(new UsuarioResponse(usuarioService.ver(id)));
    }
}
