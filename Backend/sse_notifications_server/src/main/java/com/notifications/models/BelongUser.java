package com.notifications.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BelongUser {
	private int id;
	private int users_id;
	private int users_numero;
	private int theme_id;
	private String theme_nombre;
}
