package com.notifications.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.notifications.dao.UserDAO;
import com.notifications.models.Usuario;

@Service
public class LogicaUser implements UserService{

	@Autowired
	UserDAO repositorio;
	
	@Override
	public Usuario crear(Usuario usuario) {
		int id = repositorio.crearUsuario(usuario);
		return repositorio.buscarUsuario(id);
	}

	@Override
	public List<Usuario> consultarUsuarios() {
		return repositorio.consultarUsuarios();
	}

	@Override
	public Usuario buscarUsuario(int id) {
		return repositorio.buscarUsuario(id);
	}

}
