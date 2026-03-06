package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.models.entity.Envio;

@Repository
public interface EnvioRepository extends JpaRepository<Envio, Long> {

}
