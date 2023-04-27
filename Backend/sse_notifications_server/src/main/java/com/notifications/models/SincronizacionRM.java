package com.notifications.models;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class SincronizacionRM implements RowMapper<Sincronizacion>{
	
	@Override
	public Sincronizacion mapRow(ResultSet rs, int i) throws SQLException {
		Sincronizacion n = new Sincronizacion();
		n.setId(rs.getInt("id"));
		n.setCreado(rs.getString("creado"));
		return n;
	}
	
}
