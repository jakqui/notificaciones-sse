package com.notifications.controllers;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.notifications.models.BelongUser;
import com.notifications.models.Usuario;
import com.notifications.services.BelongService;


@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.PUT})
@RequestMapping("/belong")
public class WSBelong {
	
	private static final Log bitacora = LogFactory.getLog(WSBelong.class);
	
	@Autowired
	BelongService s;
	
	@PutMapping()
	public ResponseEntity<List<Usuario>> actualizarUsuariosPorTema(@RequestBody List<BelongUser> usuariosTema){
		List<Usuario> ut = null;
		try {
			ut = s.actualizarUsuariosPorTema(usuariosTema);
		} catch (Exception e) {
			bitacora.error(e.getMessage());
			return new ResponseEntity<List<Usuario>> (HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Usuario>> (ut, HttpStatus.OK);
	}
	

}
