package com.notifications.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.notifications.dao.UserThemesDAO;
import com.notifications.models.BelongUser;

@Service
public class LogicaUserThemes implements UserThemesService{

	@Autowired
	UserThemesDAO repositorio;

	@Override
	public List<BelongUser> consultarTemasPorUsuario(int numero_usuario) {
		return repositorio.consultarTemasPorUsuario(numero_usuario);
	}

}
