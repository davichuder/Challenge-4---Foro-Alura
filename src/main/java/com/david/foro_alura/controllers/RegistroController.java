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

import jakarta.validation.Valid;

@Controller
@RequestMapping("/registro")
public class RegistroController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<String> registroUsuario(
            @RequestBody @Valid RegistroUsuarioRequest registroUsuario) throws DuplicadoException {
        usuarioService.registro(registroUsuario);
        return ResponseEntity.ok("Registro correcto");
    }
}
