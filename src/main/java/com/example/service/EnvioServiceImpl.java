package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.models.entity.Envio;
import com.example.repository.EnvioRepository;

@Service
public class EnvioServiceImpl implements EnvioService {

    @Autowired
    EnvioRepository envioRepository;

    @Override
    public Optional<Envio> findById(Long id) {
        return envioRepository.findById(id);
    }

    @Override
    public List<Envio> findAll() {
        return envioRepository.findAll();
    }

    @Override
    public Envio save(Envio e) {
        return envioRepository.save(e);
    }

    @Override
    public void deleteById(Long id) {
        envioRepository.deleteById(id);
    }
}
