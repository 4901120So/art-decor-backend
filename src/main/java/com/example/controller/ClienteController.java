package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.models.entity.Cliente;
import com.example.service.ClienteService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

@Autowired
ClienteService clienteService;

	@PostMapping
	public Cliente guardar(@RequestBody Cliente c){
	
		return clienteService.save(c);
	}
	
	@PostMapping("login")
	public ResponseEntity<String> login(@RequestBody Cliente cliente){
		
		String mensaje= clienteService.login(cliente.getUsername(), cliente.getPassword());
		
		return ResponseEntity.ok(mensaje);
	}
	
}
