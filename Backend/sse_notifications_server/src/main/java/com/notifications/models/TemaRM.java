package com.notifications.models;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TemaRM implements RowMapper<Tema>{
	
	@Override
	public Tema mapRow(ResultSet rs, int i) throws SQLException {
		Tema n = new Tema();
		n.setId(rs.getInt("id"));
		n.setNombre(rs.getString("nombre"));
		n.setDescripcion(rs.getString("descripcion"));
		//RECUPERAR USUARIOS DEL TEMA
		try {
			String json=rs.getString("usuarios");
			ObjectMapper mapper = new ObjectMapper();
			List<Usuario> users=null;
			users = mapper.readValue(json, new TypeReference<List<Usuario>>(){});	
			n.setUsuarios(users);
		} catch (Exception e) {
			//System.out.println("No se pudo parsear Usuarios en objeto Tema: " + e.toString());
		}
		return n;
	}
	
}
