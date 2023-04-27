package com.notifications.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tema {
	
	private int id;
	private String nombre;
	private String descripcion;
	private List<Usuario> usuarios;
	
}
