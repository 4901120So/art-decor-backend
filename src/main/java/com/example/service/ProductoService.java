package com.example.service;

import java.util.List;
import java.util.Optional;

import com.example.models.entity.Producto;

public interface ProductoService {

	public Optional<Producto> findById(Integer id);
	public List<Producto> findAll();
	public Producto save(Producto p);
	public void deleteById(Integer id);
	
}
