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

import com.example.models.entity.Envio;
import com.example.service.EnvioService;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class EnvioControllerTest {

    @Mock
    EnvioService envioService;

    @InjectMocks
    EnvioController envioController;

    @Test
    void listarTodos_returnsList() throws Exception {
        when(envioService.findAll()).thenReturn(Arrays.asList(new Envio()));
        assertEquals(1, envioController.listarTodos().size());
    }

    @Test
    void guardar_returnsSaved() throws Exception {
        Envio e = new Envio();
        when(envioService.save(any())).thenReturn(e);
        ResponseEntity<?> response = envioController.guardar(e);
        Object body = response.getBody();
        assertEquals(e, body);
    }

    @Test
    void eliminar_callsService() throws Exception {
        doNothing().when(envioService).deleteById(anyLong());
        envioController.eliminar(1L);
    }
}