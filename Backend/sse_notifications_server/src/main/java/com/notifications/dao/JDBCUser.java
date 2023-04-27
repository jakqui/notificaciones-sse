package com.notifications.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.notifications.models.Usuario;
import com.notifications.models.UsuarioRM;

@Repository
public class JDBCUser implements UserDAO{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public int crearUsuario(Usuario usuario) {
		SimpleJdbcInsert insert = new  SimpleJdbcInsert(jdbcTemplate);
		List<String> columnas = new ArrayList<>();
		columnas.add("nombre");
		columnas.add("paterno");
		columnas.add("materno");
		columnas.add("numero");
		columnas.add("email");
		
		insert.setTableName("users");
		insert.setColumnNames(columnas);
		
		Map<String, Object> datos = new HashMap<>();
		datos.put("nombre",usuario.getNombre());
		datos.put("paterno",usuario.getPaterno());
		datos.put("materno",usuario.getMaterno());
		datos.put("numero",usuario.getNumero());
		datos.put("email", usuario.getEmail());
		
		insert.setGeneratedKeyName("id");
		Number id = insert.executeAndReturnKey(datos);
		
		return id.intValue();
	}

	@Override
	public Usuario buscarUsuario(int id) {
		Usuario user = jdbcTemplate.queryForObject("SELECT * FROM users where id = ?",new UsuarioRM(), id);
		return user;
	}

	@Override
	public List<Usuario> consultarUsuarios() {
		return jdbcTemplate.query("SELECT * FROM users", new UsuarioRM());
	}

}
