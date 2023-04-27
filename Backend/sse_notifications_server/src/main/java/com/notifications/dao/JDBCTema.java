package com.notifications.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.notifications.models.Tema;
import com.notifications.models.TemaRM;
import com.notifications.models.Usuario;
import com.notifications.models.UsuarioRM;

@Repository
public class JDBCTema implements TemaDAO{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public int crearTema(Tema tema) {
		SimpleJdbcInsert insert = new  SimpleJdbcInsert(jdbcTemplate);
		List<String> columnas = new ArrayList<>();
		columnas.add("nombre");
		columnas.add("descripcion");
		
		insert.setTableName("theme");
		insert.setColumnNames(columnas);
		
		Map<String, Object> datos = new HashMap<>();
		datos.put("nombre",tema.getNombre());
		datos.put("descripcion",tema.getDescripcion());
		
		insert.setGeneratedKeyName("id");
		Number id = insert.executeAndReturnKey(datos);
		
		return id.intValue();
	}
	
	private static String SQL = "SELECT *, "
											+ "(SELECT JSON_ARRAYAGG( "
													+ "JSON_OBJECT('id', u.id, "
													+ "'numero', u.numero, "
													+ "'nombre', u.nombre, "
													+ "'paterno', u.paterno, "
													+ "'materno', u.materno, "
													+ "'email', u.email)) "
											+ "FROM users u " 
											+ "WHERE u.id IN (SELECT b.users_id FROM belong b WHERE b.theme_id = te.id AND b.activo = 1)) usuarios "								
								+ "FROM theme te "; 

	@Override
	public Tema buscarTema(int id) {
		Tema tema = jdbcTemplate.queryForObject(SQL + "WHERE id = ?" + "AND te.activo = 1 ", new TemaRM(), id);
		return tema;
	}

	@Override
	public List<Tema> consultarTemas() {
		return jdbcTemplate.query(SQL + "WHERE te.activo = 1 ", new TemaRM());
	}

	@Override
	public List<Usuario> consultarUsuariosPorTema(int id) {
		return jdbcTemplate.query("SELECT * FROM users WHERE id IN (SELECT users_id FROM belong WHERE theme_id = ? AND activo = 1)", new UsuarioRM(), id);
	}

}
