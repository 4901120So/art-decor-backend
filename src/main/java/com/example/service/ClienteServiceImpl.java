package com.example.service;

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

}
