package com.david.foro_alura.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.david.foro_alura.dto.usuario.ModificarUsuarioRequest;
import com.david.foro_alura.dto.usuario.RegistroUsuarioRequest;
import com.david.foro_alura.enums.Rol;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
public class Usuario implements UserDetails{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String email;
	private String password;

	private Boolean activo;

	@Enumerated(EnumType.STRING)
	private Rol rol;

	public Usuario(RegistroUsuarioRequest registroUsuario) {
		this.email = registroUsuario.email();
		this.password = new BCryptPasswordEncoder().encode(registroUsuario.password());
		this.activo = true;
		this.rol = Rol.USUARIO;
	}

	public void actualizar(ModificarUsuarioRequest modificarUsuario) {
		this.email = modificarUsuario.email();
		this.password = modificarUsuario.password();
	}

	public void desactivar() {
		this.activo = false;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_" + this.rol.toString()));
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
