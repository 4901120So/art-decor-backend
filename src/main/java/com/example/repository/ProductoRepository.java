package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.models.entity.Producto;

@Repository
public interface ProductoRepository extends JpaRepository <Producto, Integer> {

}
