package com.notifications.dao;

import java.util.List;

import com.notifications.models.Tema;
import com.notifications.models.Usuario;

public interface TemaDAO {

	public int crearTema(Tema tema);
	public Tema buscarTema(int id);
	public List<Tema> consultarTemas();
	public List<Usuario> consultarUsuariosPorTema(int id);

}
