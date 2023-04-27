package com.notifications.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.notifications.models.Notificacion;
import com.notifications.models.NotificacionRM;
import com.notifications.models.Sincronizacion;
import com.notifications.models.SincronizacionRM;

@Repository
public class JDBCNotifications implements NotificationsDAO{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Notificacion> consultarNotificacionesSinSincronizar() {
		String sql = "SELECT n.*, "
				+ "(SELECT nombre FROM theme WHERE id = theme_id_origen) AS theme_nombre_origen, "
				+ "(SELECT nombre FROM theme WHERE id = theme_id_destino) AS theme_nombre_destino, "
				+ "r.lectura AS lectura_por_usuario, "
				+ "IF(r.lectura = 1, 'SI', 'NO') as estatus_leida_usuario, "
				+ "CALCULAR_TIEMPO_TRANSCURRIDO(n.creado) as tiempo_transcurrido, "
				+ "(SELECT numero FROM users WHERE id = r.users_id) AS numero_usuario_dirigida "
				+ "FROM notifications n "
				+ "JOIN readings r ON r.notifications_id = n.id "
				+ "WHERE sincroniza_id IS NULL OR sincroniza_id = 0";
		return jdbcTemplate.query(sql, new NotificacionRM());
	}

	@Override
	public int crearSincronizacion() {
		SimpleJdbcInsert insert = new  SimpleJdbcInsert(jdbcTemplate);
		List<String> columnas = new ArrayList<>();
		
		insert.setTableName("synchronize");
		insert.setColumnNames(columnas);
		
		Map<String, Object> datos = new HashMap<>();
		
		insert.setGeneratedKeyName("id");
		Number id = insert.executeAndReturnKey(datos);
		
		return id.intValue();
	}

	@Override
	public Sincronizacion buscar(int id) {
		Sincronizacion sinc = jdbcTemplate.queryForObject("SELECT * FROM synchronize where id = ?",new SincronizacionRM(), id);
		return sinc;
	}

	

	@Override
	public void actualizarSincronizacionNotificacionesSinSincronizar(int sincronizacion_id) {
		String sql = "UPDATE notifications SET sincroniza_id = ? WHERE sincroniza_id IS NULL OR sincroniza_id = 0";
		jdbcTemplate.update(sql, sincronizacion_id);
	}
	
	@Override
	public List<Notificacion> consultarHistorialNotificacionesPorUsuario(int numero, String statusLecturaUsuario) {
		String sql = "SELECT n.*, "
				+ "(SELECT nombre FROM theme WHERE id = theme_id_origen) AS theme_nombre_origen, "
				+ "(SELECT nombre FROM theme WHERE id = theme_id_destino) AS theme_nombre_destino, " 
				+ "r.lectura AS lectura_por_usuario, "
				+ "IF(r.lectura = 1, 'SI', 'NO') as estatus_leida_usuario, "
				+ "CALCULAR_TIEMPO_TRANSCURRIDO(n.creado) as tiempo_transcurrido, "
				+ "(SELECT numero FROM users WHERE id = r.users_id) AS numero_usuario_dirigida "
				+ "FROM notifications n "
				+ "JOIN readings r ON r.notifications_id = n.id "
				+ "WHERE theme_id_destino IN (SELECT theme_id FROM belong WHERE users_id = (SELECT id FROM users WHERE numero = ?)) AND "
				+ "users_id = (SELECT id FROM users WHERE numero = ?)"
				+ "AND r.lectura LIKE ? "
				+ "ORDER BY n.creado DESC";
		return jdbcTemplate.query(sql, new NotificacionRM(), numero, numero, statusLecturaUsuario);
	}

	@Override
	public Notificacion buscarNotificacion(int id) {
		String sql = "SELECT n.*, "
				+ "(SELECT nombre FROM theme WHERE id = theme_id_origen) AS theme_nombre_origen, "
				+ "(SELECT nombre FROM theme WHERE id = theme_id_destino) AS theme_nombre_destino, " 
				+ "r.lectura AS lectura_por_usuario, "
				+ "IF(r.lectura = 1, 'SI', 'NO') as estatus_leida_usuario, "
				+ "CALCULAR_TIEMPO_TRANSCURRIDO(n.creado) as tiempo_transcurrido, "
				+ "(SELECT numero FROM users WHERE id = r.users_id) AS numero_usuario_dirigida "
				+ "FROM notifications n "
				+ "JOIN readings r ON r.notifications_id = n.id "
				+ "WHERE n.id = ?";
		return jdbcTemplate.queryForObject(sql, new NotificacionRM(), id);
	}

	@Override
	public void leerNotificacionPorUsuario(int id, int numero) {
		String sql = "UPDATE readings SET lectura = 1 WHERE notifications_id = ? AND users_id = (SELECT id FROM users WHERE numero = ?)";
		jdbcTemplate.update(sql, id, numero);
	}

	@Override
	public int crearNotificacion(Notificacion notificacion) {
		SimpleJdbcInsert insert = new  SimpleJdbcInsert(jdbcTemplate);
		List<String> columnas = new ArrayList<>();
		columnas.add("theme_id_origen");
		columnas.add("theme_id_destino");
		columnas.add("titulo");
		columnas.add("descripcion");
		columnas.add("adicionales");
		
		insert.setTableName("notifications");
		insert.setColumnNames(columnas);
		
		Map<String, Object> datos = new HashMap<>();
		datos.put("theme_id_origen", notificacion.getTheme_id_origen());
		datos.put("theme_id_destino", notificacion.getTheme_id_destino());
		datos.put("titulo", notificacion.getTitulo());
		datos.put("descripcion", notificacion.getDescripcion());
		datos.put("adicionales", notificacion.getAdicionales());
		
		insert.setGeneratedKeyName("id");
		Number id = insert.executeAndReturnKey(datos);
		
		return id.intValue();
	}

}
