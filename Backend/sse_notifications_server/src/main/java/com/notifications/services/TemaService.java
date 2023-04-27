package com.notifications.services;

import java.util.List;

import com.notifications.models.Tema;
import com.notifications.models.Usuario;

public interface TemaService {

	public Tema crear(Tema usuario);
	public List<Tema> consultarTemas();
	public Tema buscarTema(int id);
	public List<Usuario> consultarUsuariosPorTema(int id);
	

}
