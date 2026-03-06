package com.example.service;

import java.util.List;
import java.util.Optional;

import com.example.models.entity.Pedido;

public interface PedidoService {

    public Optional<Pedido> findById(Long id);
    public Optional<Pedido> update(Long id, Pedido p);
    public List<Pedido> findAll();
    public Pedido save(Pedido p);
    public void deleteById(Long id);
}
