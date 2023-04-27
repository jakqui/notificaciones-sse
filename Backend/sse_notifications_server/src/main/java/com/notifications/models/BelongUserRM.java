package com.notifications.models;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BelongUserRM implements RowMapper<BelongUser>{
	
	@Override
	public BelongUser mapRow(ResultSet rs, int i) throws SQLException {
		BelongUser n = new BelongUser();
		n.setId(rs.getInt("id"));
		n.setUsers_id(rs.getInt("users_id"));
		n.setUsers_numero(rs.getInt("users_numero"));
		n.setTheme_id(rs.getInt("theme_id"));
		n.setTheme_nombre(rs.getString("theme_nombre"));
		return n;
	}
	
}
