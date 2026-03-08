package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	@GetMapping
	public List<Cliente> listarTodos() {
		return clienteService.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cliente> obtenerPorId(@PathVariable("id") Long id) {
		Optional<Cliente> c = clienteService.findById(id);
		return c.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Cliente> actualizar(@PathVariable("id") Long id, @RequestBody Cliente cliente) {
		Optional<Cliente> updated = clienteService.update(id, cliente);
		return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Long id) {
		clienteService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
}