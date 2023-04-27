package com.notifications.services;

import java.util.List;

import com.notifications.models.Notificacion;
import com.notifications.models.Sincronizacion;

public interface NotificationsService {

	public List<Notificacion> consultarNotificacionesSinSincronizar();	
	public Sincronizacion crearSincronizacion();
	public List<Notificacion> consultarHistorialNotificacionesPorUsuario(int numero, String statusLecturaUsuario);
	public Notificacion leerNotificacionPorUsuario(int id, int numero);
	public Notificacion crearNotificacion(Notificacion notificacion);
	
}