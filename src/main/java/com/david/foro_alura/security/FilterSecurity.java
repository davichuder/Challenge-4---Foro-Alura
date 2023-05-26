package com.david.foro_alura.security;

import java.io.IOException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.david.foro_alura.repository.UsuarioRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterSecurity extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            String token = authorizationHeader.replace("Bearer ", "");
            String email = tokenService.obtenerEmail(token);
            if (email != null) {
                UserDetails detallesUsuario = usuarioRepository.findByEmail(email);
                if (detallesUsuario != null) {
                    Collection<? extends GrantedAuthority> authorities = detallesUsuario.getAuthorities();
                    UsernamePasswordAuthenticationToken credenciales = new UsernamePasswordAuthenticationToken(
                            detallesUsuario, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(credenciales);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}