package com.notifications.services;

import java.util.List;

import com.notifications.models.BelongUser;
import com.notifications.models.Usuario;

public interface BelongService {

	public List<Usuario> actualizarUsuariosPorTema(List<BelongUser> usuariosTema);	

}
