package com.notifications;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.notifications.controllers.ConfiguracionSSE;
import com.notifications.models.ClientesDepartamentosSSE;
import com.notifications.models.Notificacion;
import com.notifications.services.NotificationsService;

@Component
public class ScheduledUpdatesOnTopic {
	
	@Autowired
	ConfiguracionSSE sse;
	
	@Autowired
	NotificationsService s;
    
    @Scheduled(fixedDelay=10000)
    public void sincronizar(){ 
    	List<Notificacion> notificaciones = s.consultarNotificacionesSinSincronizar();    	
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
		for(Notificacion n : notificaciones) {
			for(ClientesDepartamentosSSE csse : clientesDepartamentosSSECopia) {				
				cliente = csse.getCliente();
				agregarNotificacion(cliente, csse, n);
			}
		}  
		
		//CREAR SINCRONIZACION SOLO SI HAY NOTIFICACIONES
    	if(notificaciones.size() > 0) {
    		s.crearSincronizacion();
    	} 
    }
    
    //ENVIO DE NUEVAS NOTIFICACIONES
  	public void agregarNotificacion(SseEmitter cliente, ClientesDepartamentosSSE csse, Notificacion notificacion) {
  		if (cliente != null) {
  			try {
  				cliente.send(SseEmitter.event().name(notificacion.getTheme_nombre_destino()).data(notificacion));
  			} catch (Exception e) {
  				sse.eliminarClienteDepartamentos(csse.getId());
  			}
  		}
  	}

}
