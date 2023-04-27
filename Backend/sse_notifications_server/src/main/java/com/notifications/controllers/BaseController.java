package com.notifications.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class BaseController {
	
	@GetMapping
	public ResponseEntity<String> redirectIndex1() {
		return new ResponseEntity<String>("Hola Mundo", HttpStatus.OK);
	}

}
