package com.example.service;

import com.example.models.entity.Cliente;

public interface ClienteService {
	
	public Cliente save (Cliente c);
	
	public String login (String user, String ctr);
	

}
