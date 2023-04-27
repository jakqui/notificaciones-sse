package com.notifications.services;

import java.util.List;

import com.notifications.models.BelongUser;

public interface UserThemesService {

	public List<BelongUser> consultarTemasPorUsuario(int numero_usuario);	

}
