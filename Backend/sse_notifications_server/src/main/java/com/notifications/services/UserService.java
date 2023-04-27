package com.notifications.services;

import java.util.List;

import com.notifications.models.Usuario;

public interface UserService {

	public Usuario crear(Usuario usuario);
	public List<Usuario> consultarUsuarios();
	public Usuario buscarUsuario(int id);
	

}
