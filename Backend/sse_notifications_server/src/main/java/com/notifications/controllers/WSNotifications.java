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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.notifications.models.Notificacion;
import com.notifications.services.NotificationsService;


@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.PUT})
@RequestMapping("/notifications")
public class WSNotifications {
	
	private static final Log bitacora = LogFactory.getLog(WSNotifications.class);
	
	@Autowired
	NotificationsService s;
	
	@GetMapping("/sin-sincronizar")
	public ResponseEntity<List<Notificacion>> consultarNotificacionesSinSincronizar(){
		List<Notificacion> notificaciones = null;
		try {
			notificaciones = s.consultarNotificacionesSinSincronizar();
		} catch (Exception e) {
			bitacora.error(e.getMessage());
			return new ResponseEntity<List<Notificacion>> (HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Notificacion>> (notificaciones, HttpStatus.OK);
	}
	
	@GetMapping("/user/{numero}")
	public ResponseEntity<List<Notificacion>> consultarHistorialNotificacionesPorUsuario(@PathVariable int numero, @RequestParam String statusLecturaUsuario){
		List<Notificacion> notificaciones = null;
		try {
			notificaciones = s.consultarHistorialNotificacionesPorUsuario(numero, statusLecturaUsuario);
		} catch (Exception e) {
			bitacora.error(e.getMessage());
			return new ResponseEntity<List<Notificacion>> (HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Notificacion>> (notificaciones, HttpStatus.OK);
	}
	
	@PutMapping("/{id}/user/{numero}")
	public ResponseEntity<Notificacion> leerNotificacionPorUsuario(@PathVariable int id, @PathVariable int numero){
		Notificacion notificacion = null;
		try {
			notificacion = s.leerNotificacionPorUsuario(id, numero);
		} catch (Exception e) {
			bitacora.error(e.getMessage());
			return new ResponseEntity<Notificacion> (HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Notificacion> (notificacion, HttpStatus.OK);
	}
	
	@PostMapping()
	public ResponseEntity<Notificacion> crearNotificacion(@RequestBody Notificacion notificacion){
		Notificacion notificacionCreada = null;
		try {
			notificacionCreada = s.crearNotificacion(notificacion);
		} catch (Exception e) {
			bitacora.error(e.getMessage());
			return new ResponseEntity<Notificacion> (HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Notificacion> (notificacionCreada, HttpStatus.OK);
	}

}
