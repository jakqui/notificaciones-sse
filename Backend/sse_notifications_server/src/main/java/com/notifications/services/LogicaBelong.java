package com.notifications.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.notifications.dao.BelongDAO;
import com.notifications.models.BelongUser;
import com.notifications.models.Usuario;

@Service
public class LogicaBelong implements BelongService{

	@Autowired
	BelongDAO repositorio;

	@Override
	public List<Usuario> actualizarUsuariosPorTema(List<BelongUser> usuariosTema) {
		return repositorio.actualizarUsuariosPorTema(usuariosTema);
	}

}
