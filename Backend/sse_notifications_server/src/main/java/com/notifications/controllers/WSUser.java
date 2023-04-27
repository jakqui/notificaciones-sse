package com.notifications.controllers;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.notifications.models.Usuario;
import com.notifications.services.UserService;

//WS PARA MANEJO DE USUARIOS 2
@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RequestMapping("/user")
public class WSUser {
	
	private static final Log bitacora = LogFactory.getLog(WSUser.class);
	
	@Autowired
	UserService s;
	
	@PostMapping("")
	public ResponseEntity<Usuario> insertarUsuario(@RequestBody Usuario usuario){
		Usuario user = null;
		bitacora.info(user);
		try {
			user = s.crear(usuario);
			return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);
		} catch (Exception e) {
			bitacora.error(e.getMessage());
			return new ResponseEntity<Usuario>(HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> buscarUsuario(@PathVariable int id){
		Usuario usuario = null;
		try {
			usuario = s.buscarUsuario(id);
		} catch (Exception e) {
			bitacora.error(e.getMessage());
			return new ResponseEntity<Usuario> (HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Usuario> (usuario, HttpStatus.OK);
	}
	
	@GetMapping("")
	public ResponseEntity<List<Usuario>> consultarUsuarios(){
		List<Usuario> usuarios = null;
		try {
			usuarios = s.consultarUsuarios();
		} catch (Exception e) {
			bitacora.error(e.getMessage());
			return new ResponseEntity<List<Usuario>> (HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Usuario>> (usuarios, HttpStatus.OK);
	}
	

}
