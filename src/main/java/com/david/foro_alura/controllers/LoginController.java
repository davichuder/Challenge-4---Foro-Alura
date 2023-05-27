package com.david.foro_alura.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.david.foro_alura.entity.Usuario;
import com.david.foro_alura.security.JwtTokenResponse;
import com.david.foro_alura.security.LoginRequest;
import com.david.foro_alura.security.TokenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "2. Login")
@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Operation(summary = "Login", description = "Una vez registrado se envia email, y password, y te devuelve un token Bearer con duracion de 24 horas para la utilizacion de la API")
    @PostMapping
    public ResponseEntity<JwtTokenResponse> login(@RequestBody @Valid LoginRequest loginUsuario) {
        UsernamePasswordAuthenticationToken credenciales = new UsernamePasswordAuthenticationToken(loginUsuario.email(),
                loginUsuario.password());
        Authentication authentication = authenticationManager.authenticate(credenciales);
        Usuario usuario = (Usuario) authentication.getPrincipal();
        String token = tokenService.generarToken(usuario);
        return ResponseEntity.ok(new JwtTokenResponse(token));
    }
}
