package com.notifications.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notificacion {
	
	private int id;
	private int theme_id_origen;
	private String theme_nombre_origen;	
	private int theme_id_destino;
	private String theme_nombre_destino;
	private String titulo;
	private String descripcion;
	private String creado;
	private String adicionales;
	private int lectura;
	private int sincroniza_id;
	private int lectura_por_usuario;
	private String estatus_leida_usuario;
	private String tiempo_transcurrido;
	private int numero_usuario_dirigida;
}
