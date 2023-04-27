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

import com.notifications.models.Tema;
import com.notifications.models.Usuario;
import com.notifications.services.TemaService;


@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RequestMapping("/theme")
public class WSTheme {
	
	private static final Log bitacora = LogFactory.getLog(WSTheme.class);
	
	@Autowired
	TemaService s;
	
	@PostMapping("")
	public ResponseEntity<Tema> insertarTeme(@RequestBody Tema tema){
		Tema theme = null;
		bitacora.info(tema);
		try {
			theme = s.crear(tema);
			return new ResponseEntity<Tema>(theme, HttpStatus.CREATED);
		} catch (Exception e) {
			bitacora.error(e.getMessage());
			return new ResponseEntity<Tema>(HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Tema> buscarTema(@PathVariable int id){
		Tema tema = null;
		try {
			tema = s.buscarTema(id);
		} catch (Exception e) {
			bitacora.error(e.getMessage());
			return new ResponseEntity<Tema> (HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Tema> (tema, HttpStatus.OK);
	}
	
	@GetMapping("")
	public ResponseEntity<List<Tema>> consultarTemas(){
		List<Tema> tema = null;
		try {
			tema = s.consultarTemas();
		} catch (Exception e) {
			bitacora.error(e.getMessage());
			return new ResponseEntity<List<Tema>> (HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Tema>> (tema, HttpStatus.OK);
	}
	
	@GetMapping("/{id}/users")
	public ResponseEntity<List<Usuario>> consultarUsuariosPorTema(@PathVariable int id){
		List<Usuario> usuarios = null;
		try {
			usuarios = s.consultarUsuariosPorTema(id);
		} catch (Exception e) {
			bitacora.error(e.getMessage());
			return new ResponseEntity<List<Usuario>> (HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Usuario>> (usuarios, HttpStatus.OK);
	}
	

}
