package com.example.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.models.entity.Producto;

@SpringBootTest
@ActiveProfiles("test")
class ProductoRepositoryIntegrationTest {

    @Autowired
    ProductoRepository productoRepository;

    @Test
    void saveAndFindById() {
        Producto p = new Producto(999, "prd", "desc", "red", "10x10", 5, "9.99");
        productoRepository.save(p);

        var found = productoRepository.findById(p.getId_Producto());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("prd");
    }
}