// Restaurando la implementación del servicio de productos para que Spring lo detecte como bean
package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.models.entity.Producto;
import com.example.repository.PedidoItemRepository;
import com.example.repository.ProductoRepository;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    PedidoItemRepository pedidoItemRepository;

    @Override
    public Optional<Producto> findById(Integer id) {
        return productoRepository.findById(id);
    }

    @Override
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    @Override
    public Producto save(Producto p) {
        return productoRepository.save(p);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        // If the producto doesn't exist, nothing to do
        if (id == null || !productoRepository.existsById(id)) {
            return;
        }
        // Eliminar items de pedido que referencien el producto para evitar violación de FK
        pedidoItemRepository.deleteByProductoId(id);
        productoRepository.deleteById(id);
    }

}