package com.example.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.models.entity.Envio;
import com.example.repository.EnvioRepository;

@ExtendWith(MockitoExtension.class)
class EnvioServiceImplTest {

    @Mock
    EnvioRepository envioRepository;

    @InjectMocks
    EnvioServiceImpl envioService;

    @Test
    void findAll_callsRepository() {
        when(envioRepository.findAll()).thenReturn(Arrays.asList(new Envio()));
        envioService.findAll();
        verify(envioRepository).findAll();
    }

    @Test
    void findById_callsRepository() {
        when(envioRepository.findById(anyLong())).thenReturn(Optional.empty());
        envioService.findById(1L);
        verify(envioRepository).findById(1L);
    }

    @Test
    void save_callsRepository() {
        Envio e = new Envio();
        when(envioRepository.save(any())).thenReturn(e);
        envioService.save(e);
        verify(envioRepository).save(e);
    }

    @Test
    void deleteById_callsRepository() {
        envioService.deleteById(1L);
        verify(envioRepository).deleteById(1L);
    }
}
