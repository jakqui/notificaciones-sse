package com.notifications.dao;

import java.util.List;

import com.notifications.models.Notificacion;
import com.notifications.models.Sincronizacion;

public interface NotificationsDAO {

	public List<Notificacion> consultarNotificacionesSinSincronizar();
	public int crearSincronizacion();
	public void actualizarSincronizacionNotificacionesSinSincronizar(int sincronizacion_id);
	public Sincronizacion buscar(int id);
	public List<Notificacion> consultarHistorialNotificacionesPorUsuario(int numero, String statusLecturaUsuario);
	public Notificacion buscarNotificacion(int id);
	public void leerNotificacionPorUsuario(int id, int numero);
	public int crearNotificacion(Notificacion notificacion);
	
}
