package com.notifications.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.notifications.dao.TemaDAO;
import com.notifications.models.Tema;
import com.notifications.models.Usuario;

@Service
public class LogicaTema implements TemaService{

	@Autowired
	TemaDAO repositorio;
	
	@Override
	public Tema crear(Tema tema) {
		int id = repositorio.crearTema(tema);
		return repositorio.buscarTema(id);
	}

	@Override
	public List<Tema> consultarTemas() {
		return repositorio.consultarTemas();
	}

	@Override
	public Tema buscarTema(int id) {
		return repositorio.buscarTema(id);
	}

	@Override
	public List<Usuario> consultarUsuariosPorTema(int id) {
		return repositorio.consultarUsuariosPorTema(id);
	}

}
