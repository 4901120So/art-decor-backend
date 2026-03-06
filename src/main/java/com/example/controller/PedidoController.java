package com.example.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
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

import com.example.models.entity.Pedido;
import com.example.service.PedidoService;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT })
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    PedidoService pedidoService;

    @GetMapping("/{id}")
    public Optional<Pedido> buscarPorId(@PathVariable("id") Long id) {
        return pedidoService.findById(id);
    }

    @GetMapping("/listar")
    public List<Pedido> listarTodos() {
        return pedidoService.findAll();
    }

    @PostMapping
    public Pedido guardar(@RequestBody Pedido p) {
        // items will be cascaded
        return pedidoService.save(p);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable("id") Long id) {
        pedidoService.deleteById(id);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizar(@RequestBody Pedido p, @PathVariable("id") Long id) {
        try {
            return pedidoService.update(id, p)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
