package com.example.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="clientes")
public class Cliente {
	
	@Id
	@Column(name="id_clie")
	private Long id;
	@Column(name="name_clie")
	private String username;
	@Column(name="pass_clie")
	private String password;
	
	
	public Cliente() {
		super();
	}


	public Cliente(long id, String username, String password) {
		super();
		this.setId(id);
		this.setUsername(username);
		this.setPassword(password);
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
