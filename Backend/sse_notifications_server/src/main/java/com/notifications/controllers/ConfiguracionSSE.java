package com.notifications.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.notifications.models.ClientesDepartamentosSSE;

@RestController
@RequestMapping("/SSE/configuracion")
@CrossOrigin
public class ConfiguracionSSE {
	 
	public List<ClientesDepartamentosSSE> clientesDepartamentosSSE = new ArrayList<ClientesDepartamentosSSE>();
	
	/*START CONFIGURACION SSE----------------------------------------------------------------------------------------------*/
	public void eliminarClienteDepartamentos(int id_conexion) {		
		for(int j = 0; j < clientesDepartamentosSSE.size(); j++) {
			if(clientesDepartamentosSSE.get(j).getId() == id_conexion) {
				clientesDepartamentosSSE.remove(j);
			}
		}
	}
	
	int numero_conexion = 0;
	public int buscarSiguienteConexion() {
		numero_conexion ++;
		return numero_conexion;
	}
	/*END CONFIGURACION SSE----------------------------------------------------------------------------------------------*/
	
	
	/*CONSULTAR CONEXIONES ACTIVA*/
	@GetMapping("/notificaciones/conexiones")
	public ResponseEntity<List<ClientesDepartamentosSSE>> consultarConexiones() {
		//HACER COPIA PARA EVITAR ERRORES AL RECORRER LA ORIGINAL
		List<ClientesDepartamentosSSE> clientesDepartamentosSSECopia = new ArrayList<ClientesDepartamentosSSE>();
		for(ClientesDepartamentosSSE csse : clientesDepartamentosSSE) {
			clientesDepartamentosSSECopia.add(csse);
		}
		if(clientesDepartamentosSSECopia.size() > 0) {
			revisarComunicacionClientesActivos(clientesDepartamentosSSECopia);
		}		
		
		return new ResponseEntity<List<ClientesDepartamentosSSE>>(clientesDepartamentosSSE, HttpStatus.OK);
	}	
	
	/*SUBSCRIPCION AL TEMA NOTIFICACIONES*/
	@GetMapping("/notificaciones/{numero_personal}")
	public SseEmitter subscribir(@PathVariable int numero_personal) {
		int numero_conexion = buscarSiguienteConexion();
		SseEmitter cliente = new SseEmitter(Long.MAX_VALUE);
		try {
			cliente.send(SseEmitter.event().name("INIT"));
			clientesDepartamentosSSE.add(new ClientesDepartamentosSSE(numero_conexion, numero_personal, cliente));
			cliente.onCompletion(()->eliminarClienteDepartamentos(numero_conexion));
			cliente.onTimeout(()->eliminarClienteDepartamentos(numero_conexion));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//HACER COPIA PARA EVITAR ERRORES AL RECORRER LA ORIGINAL
			List<ClientesDepartamentosSSE> clientesDepartamentosSSECopia = new ArrayList<ClientesDepartamentosSSE>();
			for(ClientesDepartamentosSSE csse : clientesDepartamentosSSE) {
				clientesDepartamentosSSECopia.add(csse);
			}
			//System.out.println("Antes: "+clientesDepartamentosSSE.toString());
			revisarComunicacionClientesActivos(clientesDepartamentosSSECopia);
			
			System.out.println("Despues: "+clientesDepartamentosSSE.toString());
		}
		return cliente;
	}
	
	/*REVISAR CLIENTES ACTIVOS*/
	@PostMapping("/notificaciones/conexiones/activos")
	public void revisarComunicacionClientesActivos(List<ClientesDepartamentosSSE> clientesSSECopia) {
		//HACER COPIA PARA EVITAR ERRORES AL RECORRER LA ORIGINAL	
		for(ClientesDepartamentosSSE csse : clientesSSECopia) {	
			SseEmitter c = csse.getCliente();
			if (c != null) {
				try {
					c.send(SseEmitter.event().name("activo").data(""));
				} catch (Exception e) {
					eliminarClienteDepartamentos(csse.getId());
				}
			}
		}		
	}
	
	
	/*REVISAR CLIENTES ACTIVOS*/
	@PostMapping("/notificaciones/conexiones/activos/sindatos")
	public void revisarComunicacionClientesActivos() {
		//HACER COPIA PARA EVITAR ERRORES AL RECORRER LA ORIGINAL
		List<ClientesDepartamentosSSE> clientesDepartamentosSSECopia = new ArrayList<ClientesDepartamentosSSE>();
		for(ClientesDepartamentosSSE csse : clientesDepartamentosSSE) {
			clientesDepartamentosSSECopia.add(csse);
		}
		
		//HACER COPIA PARA EVITAR ERRORES AL RECORRER LA ORIGINAL	
		for(ClientesDepartamentosSSE csse : clientesDepartamentosSSECopia) {	
			SseEmitter c = csse.getCliente();
			if (c != null) {
				try {
					c.send(SseEmitter.event().name("activo").data(""));
				} catch (Exception e) {
					eliminarClienteDepartamentos(csse.getId());
				}
			}
		}		
	}
}
