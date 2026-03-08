package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

import com.example.models.entity.Envio;
import com.example.service.EnvioService;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT })
@RequestMapping("/api/envios")
public class EnvioController {

    @Autowired
    EnvioService envioService;

    @GetMapping("/{id}")
    public Optional<Envio> buscarPorId(@PathVariable("id") Long id) {
        return envioService.findById(id);
    }

    @GetMapping("/listar")
    public List<Envio> listarTodos() {
        return envioService.findAll();
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Envio e) {
        try {
            return ResponseEntity.ok(envioService.save(e));
        } catch (Exception ex) {
            String msg = ex.getMessage() != null ? ex.getMessage() : "Error al guardar el envío";
            if (msg.toLowerCase().contains("duplicate") || msg.toLowerCase().contains("unique")) {
                return ResponseEntity.badRequest().body("Este pedido ya tiene un envío asignado");
            }
            return ResponseEntity.badRequest().body(msg);
        }
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable("id") Long id) {
        envioService.deleteById(id);
    }

    @PutMapping("/actualizar/{id}")
    public Envio actualizar(@RequestBody Envio e, @PathVariable("id") Long id) {
        Envio existente = envioService.findById(id).orElse(null);
        if (existente == null) {
            return null;
        }
        existente.setDireccion(e.getDireccion());
        existente.setEstado(e.getEstado());
        existente.setTrackingNumber(e.getTrackingNumber());
        existente.setFechaEnvio(e.getFechaEnvio());
        return envioService.save(existente);
    }
}
