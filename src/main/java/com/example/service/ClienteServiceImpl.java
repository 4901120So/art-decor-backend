package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.models.entity.Cliente;
import com.example.repository.ClienteRepository;

@Service
public class ClienteServiceImpl implements ClienteService {
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Override
	public Cliente save(Cliente c) {
		
		return clienteRepository.save(c);
	}

	@Override
	public String login(String user, String ctr) {
		
		Cliente cliente=clienteRepository.findByUsername(user);
		
		if (cliente==null) {
			
			return "cliente no encontrado";
		}
		
		if (!cliente.getPassword().equals(ctr)) {
			
		return "contraseña incorrecta";
		}
		return "inicio de sesion exitoso";
	}

	@Override
	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	@Override
	public Optional<Cliente> findById(Long id) {
		return clienteRepository.findById(id);
	}

	@Override
	public Optional<Cliente> update(Long id, Cliente c) {
		return clienteRepository.findById(id).map(existing -> {
			existing.setUsername(c.getUsername() != null ? c.getUsername() : existing.getUsername());
			existing.setPassword(c.getPassword() != null ? c.getPassword() : existing.getPassword());
			return clienteRepository.save(existing);
		});
	}

	@Override
	public void deleteById(Long id) {
		clienteRepository.deleteById(id);
	}

}