package com.notifications.dao;

import java.util.List;

import com.notifications.models.Usuario;

public interface UserDAO {

	public int crearUsuario(Usuario usuario);
	public Usuario buscarUsuario(int id);
	public List<Usuario> consultarUsuarios();

}
