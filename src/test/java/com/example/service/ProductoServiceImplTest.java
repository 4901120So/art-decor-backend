package com.example.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyInt;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.repository.PedidoItemRepository;
import com.example.repository.ProductoRepository;

@ExtendWith(MockitoExtension.class)
class ProductoServiceImplTest {

    @Mock
    ProductoRepository productoRepository;

    @Mock
    PedidoItemRepository pedidoItemRepository;

    @InjectMocks
    ProductoServiceImpl productoService;

    @Test
    void deleteById_whenProductoExists_deletesItemsAndProducto() {
        when(productoRepository.existsById(anyInt())).thenReturn(true);

        productoService.deleteById(1);

        verify(pedidoItemRepository).deleteByProductoId(1);
        verify(productoRepository).deleteById(1);
    }

    @Test
    void deleteById_whenProductoNotExists_doesNothing() {
        when(productoRepository.existsById(anyInt())).thenReturn(false);

        productoService.deleteById(5);

        verify(pedidoItemRepository, never()).deleteByProductoId(anyInt());
        verify(productoRepository, never()).deleteById(anyInt());
    }
}
