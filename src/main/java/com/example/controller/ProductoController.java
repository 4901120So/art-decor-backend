package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.models.entity.Producto;
import com.example.service.ProductoService;

@RestController
@CrossOrigin(origins="*", methods={RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
@RequestMapping("/api/productos")
public class ProductoController {
	
	@Autowired
	ProductoService productoService;

	
	@GetMapping("/{id}")
	public Optional<Producto> buscarPorId(@PathVariable("id") Integer id) {
		
		return productoService.findById(id);
		
	}
	@GetMapping("/listar")
	public List<Producto> ListarTodos() {
		
		return productoService.findAll();
		
	}
	@PostMapping
	public Producto guardar(@RequestBody Producto p) {
		
		return productoService.save(p);
	}
	
	@DeleteMapping("/{id}")
	public void eliminar(@PathVariable("id") Integer id){
		productoService.deleteById(id);
	}
		
	
	@PutMapping("/actualizar/{id}")
	public Producto actualizar(@RequestBody Producto p, @PathVariable("id") Integer id) {
	   
	    Producto pEnBD = productoService.findById(id).get();
	    
	 
	    pEnBD.setColor(p.getColor());
	    pEnBD.setDescripcion(p.getDescripcion()); // 
	    pEnBD.setDimension(p.getDimension());
	    pEnBD.setName(p.getName()); // 
	    pEnBD.setPrecio(p.getPrecio());
	    pEnBD.setStock(p.getStock());
	    
	   
	    return productoService.save(pEnBD); 
	}
		
}

