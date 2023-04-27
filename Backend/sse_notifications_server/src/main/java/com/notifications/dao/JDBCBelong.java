package com.notifications.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.notifications.models.BelongUser;
import com.notifications.models.Usuario;
import com.notifications.services.TemaService;

@Repository
public class JDBCBelong implements BelongDAO{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	TemaService temaService;

	@Transactional
	@Override
	public List<Usuario> actualizarUsuariosPorTema(List<BelongUser> usuariosTema) {
		//DESACTIVAR USUARIOS DE UN TEMA
		String sql = "UPDATE belong SET activo = 0, modificado = CURRENT_TIMESTAMP WHERE theme_id = ?";
		jdbcTemplate.update(sql, usuariosTema.get(0).getTheme_id());
		
		//CREAR NUEVOS USUARIOS
		for(BelongUser userTheme: usuariosTema) {
			if(userTheme.getUsers_id() >0) {
				jdbcTemplate.update("INSERT INTO belong (users_id, theme_id) VALUES(?, ?)",
									userTheme.getUsers_id(), userTheme.getTheme_id());
			}
		}
		
		//CONSULTAR USUARIOS DEL TEMA
		return temaService.consultarUsuariosPorTema(usuariosTema.get(0).getTheme_id());
	}

}
