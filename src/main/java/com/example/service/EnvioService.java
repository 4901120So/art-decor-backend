package com.example.service;

import java.util.List;
import java.util.Optional;

import com.example.models.entity.Envio;

public interface EnvioService {

    public Optional<Envio> findById(Long id);
    public List<Envio> findAll();
    public Envio save(Envio e);
    public void deleteById(Long id);
}
