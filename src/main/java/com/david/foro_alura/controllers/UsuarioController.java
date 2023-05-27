package com.david.foro_alura.controllers;

import org.springdoc.core.annotations.ParameterObject;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Tag(name = "3. Usuarios", description = "Authenticated Access")
@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping("/usuarios")
@PreAuthorize("isAuthenticated()")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Actualizar cuenta", description = "Cualquier usuario puede actualizar su email, y password, pero debe volver a generar el token en '/login'")
    @PutMapping("/actualizar_cuenta")
    public ResponseEntity<UsuarioResponse> actualizarCuenta(HttpServletRequest request,
            @RequestBody ActualizarUsuarioRequest actualizarUsuario) throws NoExisteException, DuplicadoException {
        return ResponseEntity.ok(new UsuarioResponse(usuarioService.actualizarCuenta(request, actualizarUsuario)));
    }

    @Operation(summary = "Desactivar cuenta", description = "Cualquier usuario puede desactivar permanentemente su cuenta")
    @DeleteMapping("/desactivar_cuenta")
    @Transactional
    public ResponseEntity<Object> desactivarCuenta(HttpServletRequest request) throws NoExisteException {
        usuarioService.desactivarCuenta(request);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Modificar rol (Admin)", description = "Admin puede cambiarle el rol a cualquier cuenta")
    @PutMapping("/id/{idUsuario}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UsuarioResponse> modificarRol(@PathVariable Long idUsuario,
            @RequestBody @Valid ModificarRolUsuarioRequest modificarUsuario)
            throws NoExisteException, DuplicadoException {
        return ResponseEntity.ok(new UsuarioResponse(usuarioService.modificar(idUsuario, modificarUsuario)));
    }

    @Operation(summary = "Desactivar cuenta (Admin)", description = "Admin puede desactivar cualquier cuenta")
    @DeleteMapping("/id/{idUsuario}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> desactivarUsuario(@PathVariable Long idUsuario)
            throws NoExisteException {
        usuarioService.desactivar(idUsuario);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Lista de usuarios", description = "Cualquier usuario puede obtener la lista de todos los usuarios")
    @GetMapping
    public ResponseEntity<Page<UsuarioResponse>> listadoUsuarios(@ParameterObject @PageableDefault(size = 10) Pageable paginacion) {
        return ResponseEntity.ok(usuarioService.listado(null));
    }

    @Operation(summary = "Detalles de usuario", description = "Cualquier usuario puede ver los detalles de otro usuario")
    @GetMapping("/id/{id}")
    public ResponseEntity<UsuarioResponse> detallesUsuario(@PathVariable Long id) throws NoExisteException {
        return ResponseEntity.ok(new UsuarioResponse(usuarioService.detallesUsuario(id)));
    }
}
