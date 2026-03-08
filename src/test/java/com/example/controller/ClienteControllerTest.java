package com.example.controller;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.models.entity.Cliente;
import com.example.service.ClienteService;

@ExtendWith(MockitoExtension.class)
class ClienteControllerTest {

    @Mock
    ClienteService clienteService;

    @InjectMocks
    ClienteController clienteController;

    @Test
    void listarTodos_returnsList() throws Exception {
        when(clienteService.findAll()).thenReturn(Arrays.asList(new Cliente()));

        List<Cliente> res = clienteController.listarTodos();
        assertEquals(1, res.size());
    }

    @Test
    void obtenerPorId_notFound() throws Exception {
        when(clienteService.findById(anyLong())).thenReturn(Optional.empty());

        var response = clienteController.obtenerPorId(99L);
        assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    void crearCliente_returnsSaved() throws Exception {
        Cliente c = new Cliente(1L, "u", "p");
        when(clienteService.save(any())).thenReturn(c);

        Cliente saved = clienteController.guardar(c);
        assertEquals(c.getUsername(), saved.getUsername());
    }

    @Test
    void actualizar_notFound() throws Exception {
        when(clienteService.update(anyLong(), any())).thenReturn(Optional.empty());

        var response = clienteController.actualizar(999L, new Cliente());
        assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    void eliminar_callsService() throws Exception {
        doNothing().when(clienteService).deleteById(anyLong());

        var response = clienteController.eliminar(1L);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }
}