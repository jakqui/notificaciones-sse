package com.notifications.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.notifications.dao.NotificationsDAO;
import com.notifications.models.Notificacion;
import com.notifications.models.Sincronizacion;

@Service
public class LogicaNotifications implements NotificationsService{

	@Autowired
	NotificationsDAO repositorio;
	
	@Override
	public List<Notificacion> consultarNotificacionesSinSincronizar() {
		return repositorio.consultarNotificacionesSinSincronizar();
	}	

	@Override
	public Sincronizacion crearSincronizacion() {
		int id = repositorio.crearSincronizacion();
		//Actualizar Sincronización
		repositorio.actualizarSincronizacionNotificacionesSinSincronizar(id);
		return repositorio.buscar(id);
	}
	
	@Override
	public List<Notificacion> consultarHistorialNotificacionesPorUsuario(int numero, String statusLecturaUsuario) {
		return repositorio.consultarHistorialNotificacionesPorUsuario(numero, statusLecturaUsuario);
	}

	@Override
	public Notificacion leerNotificacionPorUsuario(int id, int numero) {
		repositorio.leerNotificacionPorUsuario(id, numero);
		return repositorio.buscarNotificacion(id); 
	}

	@Override
	public Notificacion crearNotificacion(Notificacion notificacion) {
		int id = repositorio.crearNotificacion(notificacion);
		return repositorio.buscarNotificacion(id);
	}

}
