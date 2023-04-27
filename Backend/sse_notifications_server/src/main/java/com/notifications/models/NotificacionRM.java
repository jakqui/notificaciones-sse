package com.notifications.models;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class NotificacionRM implements RowMapper<Notificacion>{
	
	@Override
	public Notificacion mapRow(ResultSet rs, int i) throws SQLException {
		Notificacion n = new Notificacion();
		n.setId(rs.getInt("id"));
		n.setTheme_id_origen(rs.getInt("theme_id_origen"));
		n.setTheme_nombre_origen(rs.getString("theme_nombre_origen"));
		n.setTheme_id_destino(rs.getInt("theme_id_destino"));
		n.setTheme_nombre_destino(rs.getString("theme_nombre_destino"));
		n.setTitulo(rs.getString("titulo"));
		n.setDescripcion(rs.getString("descripcion"));
		n.setCreado(rs.getString("creado"));
		n.setSincroniza_id(rs.getInt("sincroniza_id"));
		n.setAdicionales(rs.getString("adicionales"));
		n.setLectura(rs.getInt("lectura"));
		n.setLectura_por_usuario(rs.getInt("lectura_por_usuario"));
		n.setEstatus_leida_usuario(rs.getString("estatus_leida_usuario"));
		n.setTiempo_transcurrido(rs.getString("tiempo_transcurrido"));
		n.setNumero_usuario_dirigida(rs.getInt("numero_usuario_dirigida"));
		return n;
	}
	
}
