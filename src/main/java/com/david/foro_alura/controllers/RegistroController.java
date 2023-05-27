package com.david.foro_alura.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.david.foro_alura.dto.usuario.RegistroUsuarioRequest;
import com.david.foro_alura.exceptions.DuplicadoException;
import com.david.foro_alura.services.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "1. Registro", description = "Free Access")
@Controller
@RequestMapping("/registro")
public class RegistroController {
    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Registro", description = "Se envian email, y password para registrarlos en la API\n" +
            "Nota: Por defecto se crear los usuarios con el Rol USUARIO, unicamente si la base de datos no hay usuario cargados, se creara el primer usuario con el rol ADMIN")
    @PostMapping
    public ResponseEntity<String> registroUsuario(
            @RequestBody @Valid RegistroUsuarioRequest registroUsuario) throws DuplicadoException {
        usuarioService.registro(registroUsuario);
        return ResponseEntity.ok("Registro correcto");
    }
}
