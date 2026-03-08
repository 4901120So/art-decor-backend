package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.models.entity.PedidoItem;

@Repository
public interface PedidoItemRepository extends JpaRepository<PedidoItem, Long> {

    @Modifying
    @Query(value = "DELETE FROM pedido_items WHERE producto_id = :productoId", nativeQuery = true)
    int deleteByProductoId(@Param("productoId") Integer productoId);

}
