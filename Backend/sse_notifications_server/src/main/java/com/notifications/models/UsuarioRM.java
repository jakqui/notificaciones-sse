package com.notifications.models;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UsuarioRM implements RowMapper<Usuario>{
	
	@Override
	public Usuario mapRow(ResultSet rs, int i) throws SQLException {
		Usuario n = new Usuario();
		n.setId(rs.getInt("id"));
		n.setNombre(rs.getString("nombre"));
		n.setPaterno(rs.getString("paterno"));
		n.setMaterno(rs.getString("materno"));
		n.setNumero(rs.getInt("numero"));
		n.setEmail(rs.getString("email"));
		return n;
	}
	
}
