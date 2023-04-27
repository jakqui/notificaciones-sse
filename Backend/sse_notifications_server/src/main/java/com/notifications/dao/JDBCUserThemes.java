package com.notifications.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.notifications.models.BelongUser;
import com.notifications.models.BelongUserRM;

@Repository
public class JDBCUserThemes implements UserThemesDAO{

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<BelongUser> consultarTemasPorUsuario(int numero_usuario) {
		String sql = "SELECT " + 
				"b.id, " + 
				"users_id, " + 
				"u.numero AS users_numero, " + 
				"theme_id, " + 
				"t.nombre AS theme_nombre " + 
				"FROM belong b " + 
				"JOIN users u ON u.id = b.users_id " + 
				"JOIN theme t ON t.id = b.theme_id " + 
				"WHERE u.numero = ? " +
				"AND b.activo = 1 ";
		return jdbcTemplate.query(sql, new BelongUserRM(), numero_usuario);
	}

}
