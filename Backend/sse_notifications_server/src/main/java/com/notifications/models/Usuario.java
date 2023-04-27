package com.notifications.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
	
	private int id;
	private String nombre;
	private String paterno;
	private String materno;
	private int numero;
	private String email;
	
}
