package com.example.service;

import java.util.List;
import java.util.Optional;

import com.example.models.entity.Cliente;

public interface ClienteService {
	
	public Cliente save (Cliente c);
	
	public String login (String user, String ctr);

	// CRUD adicionales
	public List<Cliente> findAll();
	public Optional<Cliente> findById(Long id);
	public Optional<Cliente> update(Long id, Cliente c);
	public void deleteById(Long id);
	
}