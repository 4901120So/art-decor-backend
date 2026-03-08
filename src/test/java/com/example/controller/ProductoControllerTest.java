package com.example.controller;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.models.entity.Producto;
import com.example.service.ProductoService;

@ExtendWith(MockitoExtension.class)
class ProductoControllerTest {

    @Mock
    ProductoService productoService;

    @InjectMocks
    ProductoController productoController;

    @Test
    void listarTodos_returnsList() throws Exception {
        when(productoService.findAll()).thenReturn(Arrays.asList(new Producto()));
        assertEquals(1, productoController.ListarTodos().size());
    }

    @Test
    void buscarPorId_notFound() throws Exception {
        when(productoService.findById(anyInt())).thenReturn(Optional.empty());
        Optional<Producto> res = productoController.buscarPorId(999);
        assertTrue(res.isEmpty());
    }

    @Test
    void crearProducto_returnsSaved() throws Exception {
        Producto p = new Producto();
        when(productoService.save(any())).thenReturn(p);
        Producto saved = productoController.guardar(p);
        assertEquals(p, saved);
    }

    @Test
    void eliminar_callsService() throws Exception {
        doNothing().when(productoService).deleteById(anyInt());
        productoController.eliminar(1);
        // no exception means pass
    }

}