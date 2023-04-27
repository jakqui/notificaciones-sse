package com.notifications.dao;

import java.util.List;

import com.notifications.models.BelongUser;

public interface UserThemesDAO {

	public List<BelongUser> consultarTemasPorUsuario(int numero_usuario);

}
