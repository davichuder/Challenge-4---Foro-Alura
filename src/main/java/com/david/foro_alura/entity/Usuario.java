package com.david.foro_alura.entity;

import com.david.foro_alura.dto.usuario.ModificarUsuarioRequest;
import com.david.foro_alura.dto.usuario.NuevoUsuarioRequest;
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
public class Usuario {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String email;
	private String password;

	@Enumerated(EnumType.STRING)
	private Rol rol;

	private Boolean activo;

	public Usuario(RegistroUsuarioRequest registroUsuario) {
		this.email = registroUsuario.email();
		this.password = registroUsuario.password();
		this.rol = Rol.USUARIO;
		this.activo = true;
	}

	public Usuario(NuevoUsuarioRequest nuevoUsuario) {
		this.email = nuevoUsuario.email();
		this.password = nuevoUsuario.password();
		this.rol = nuevoUsuario.rol();
		this.activo = true;
	}

	public void actualizar(ModificarUsuarioRequest modificarUsuario) {
		this.email = modificarUsuario.email();
		this.password = modificarUsuario.password();
	}

	public void desactivar() {
		this.activo = false;
	}
}
