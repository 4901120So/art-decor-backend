package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.models.entity.Producto;
import com.example.repository.ProductoRepository;

@Service
public class ProductoServiceImpl  implements ProductoService {
	
	@Autowired
	ProductoRepository productoRepository;

	@Override
	public Optional<Producto> findById(Integer id) {
		// TODO Auto-generated method stub
		return productoRepository.findById(id);
	}

	@Override
	public List<Producto> findAll() {
		// TODO Auto-generated method stub
		return productoRepository.findAll();
	}

	@Override
	public Producto save(Producto p) {
		// TODO Auto-generated method stub
		return productoRepository.save(p);
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		productoRepository.deleteById(id);
	}


	

}
