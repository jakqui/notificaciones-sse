package com.notifications.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.notifications.models.ClientesDepartamentosSSE;
import com.notifications.models.Notificacion;
import com.notifications.services.NotificationsService;

@RestController
@RequestMapping("/SSE/notificaciones")
@CrossOrigin
public class NotificacionesSSEController {
	
	@Autowired
	ConfiguracionSSE sse;
	
	@Autowired
	NotificationsService s;
	
	@PostMapping("/sincronizacion")
	public void sincronizar() {
		SseEmitter cliente = null;
		
		//HACER COPIA PARA EVITAR ERRORES AL RECORRER LA ORIGINAL
		List<ClientesDepartamentosSSE> clientesDepartamentosSSECopia = new ArrayList<ClientesDepartamentosSSE>();
		for(ClientesDepartamentosSSE csse : sse.clientesDepartamentosSSE) {
			clientesDepartamentosSSECopia.add(csse);
		}
		//VALIDAR SOLO CONEXIONES ACTIVAS
		sse.revisarComunicacionClientesActivos(clientesDepartamentosSSECopia);		
		
		
		//VOLVER A HACER COPIA PARA EVITAR ERRORES AL RECORRER LA ORIGINAL
		clientesDepartamentosSSECopia = new ArrayList<ClientesDepartamentosSSE>();
		for(ClientesDepartamentosSSE csse : sse.clientesDepartamentosSSE) {
			clientesDepartamentosSSECopia.add(csse);
		}
		//RECUPERAR NOTIFICACIONES SIN SINCRONIZAR
		for(Notificacion n : s.consultarNotificacionesSinSincronizar()) {
			for(ClientesDepartamentosSSE csse : clientesDepartamentosSSECopia) {				
				cliente = csse.getCliente();
				agregarNotificacion(cliente, csse, n);
			}
		}
	}
	
	
	
	//ENVIO DE NUEVAS NOTIFICACIONES
	public void agregarNotificacion(SseEmitter cliente, ClientesDepartamentosSSE csse, Notificacion notificacion) {
		if (cliente != null) {
			try {
				cliente.send(SseEmitter.event().name(notificacion.getTheme_nombre_destino()).data(notificacion));
			} catch (IOException e) {
				sse.eliminarClienteDepartamentos(csse.getId());
			}
		}
	}
}
