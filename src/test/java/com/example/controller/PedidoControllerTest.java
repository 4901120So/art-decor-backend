package com.example.controller;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.models.entity.Pedido;
import com.example.service.PedidoService;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class PedidoControllerTest {

    @Mock
    PedidoService pedidoService;

    @InjectMocks
    PedidoController pedidoController;

    @Test
    void listarTodos_returnsList() throws Exception {
        when(pedidoService.findAll()).thenReturn(Arrays.asList(new Pedido()));
        assertEquals(1, pedidoController.listarTodos().size());
    }

    @Test
    void guardar_returnsSaved() throws Exception {
        Pedido p = new Pedido();
        when(pedidoService.save(any())).thenReturn(p);
        ResponseEntity<?> response = pedidoController.guardar(p);
        Object body = response.getBody();
        assertEquals(p, body);
    }

    @Test
    void eliminar_callsService() throws Exception {
        doNothing().when(pedidoService).deleteById(anyLong());
        pedidoController.eliminar(1L);
        // no exception -> pass
    }
}