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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.notifications.models.BelongUser;
import com.notifications.services.UserThemesService;


@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RequestMapping("/notifications/user-themes")
public class WSUserThemes {
	
	private static final Log bitacora = LogFactory.getLog(WSUserThemes.class);
	
	@Autowired
	UserThemesService s;
	
	@GetMapping("/{numero_usuario}")
	public ResponseEntity<List<BelongUser>> consultarTemasPorUsuario(@PathVariable int numero_usuario){
		List<BelongUser> temas = null;
		try {
			temas = s.consultarTemasPorUsuario(numero_usuario);
		} catch (Exception e) {
			bitacora.error(e.getMessage());
			return new ResponseEntity<List<BelongUser>> (HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<BelongUser>> (temas, HttpStatus.OK);
	}
	

}
