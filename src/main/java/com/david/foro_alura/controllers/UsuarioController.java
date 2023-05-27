package com.david.foro_alura.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.david.foro_alura.dto.usuario.ActualizarUsuarioRequest;
import com.david.foro_alura.dto.usuario.ModificarRolUsuarioRequest;
import com.david.foro_alura.dto.usuario.UsuarioResponse;
import com.david.foro_alura.exceptions.DuplicadoException;
import com.david.foro_alura.exceptions.NoExisteException;
import com.david.foro_alura.services.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
@PreAuthorize("isAuthenticated()")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PutMapping("/actualizar_cuenta")
    public ResponseEntity<UsuarioResponse> actualizarCuenta(HttpServletRequest request,
            @RequestBody ActualizarUsuarioRequest actualizarUsuario) throws NoExisteException, DuplicadoException {
        return ResponseEntity.ok(new UsuarioResponse(usuarioService.actualizarCuenta(request, actualizarUsuario)));
    }

    @DeleteMapping("/desactivar_cuenta")
    @Transactional
    public ResponseEntity<Object> desactivarCuenta(HttpServletRequest request) throws NoExisteException {
        usuarioService.desactivarCuenta(request);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/id/{idUsuario}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UsuarioResponse> modificarRol(@PathVariable Long idUsuario,
            @RequestBody @Valid ModificarRolUsuarioRequest modificarUsuario)
            throws NoExisteException, DuplicadoException {
        return ResponseEntity.ok(new UsuarioResponse(usuarioService.modificar(idUsuario, modificarUsuario)));
    }

    @DeleteMapping("/id/{idUsuario}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> desactivarUsuario(@PathVariable Long idUsuario)
            throws NoExisteException {
        usuarioService.desactivar(idUsuario);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping
    public ResponseEntity<Page<UsuarioResponse>> listadoUsuarios(@PageableDefault(size = 10) Pageable paginacion) {
        return ResponseEntity.ok(usuarioService.listado(paginacion));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UsuarioResponse> verUsuario(@PathVariable Long id) throws NoExisteException {
        return ResponseEntity.ok(new UsuarioResponse(usuarioService.ver(id)));
    }
}
