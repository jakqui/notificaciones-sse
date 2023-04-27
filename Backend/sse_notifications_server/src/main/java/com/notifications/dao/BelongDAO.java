package com.notifications.dao;

import java.util.List;

import com.notifications.models.BelongUser;
import com.notifications.models.Usuario;

public interface BelongDAO {

	public List<Usuario> actualizarUsuariosPorTema(List<BelongUser> usuariosTema);

}
