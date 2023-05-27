package com.david.foro_alura.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.david.foro_alura.entity.Usuario;
import com.david.foro_alura.security.TokenService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class RequestService {
    @Autowired
    private TokenService tokenService;

    public String obtenerEmail(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            String token = authorizationHeader.replace("Bearer ", "");
            String email = tokenService.obtenerEmail(token);
            if (email != null) {
                return email;
            }
        }
        return null;
    }

    public Boolean esPropietario(HttpServletRequest request, Usuario usuario){
        return obtenerEmail(request).equals(usuario.getEmail());
    }
}
