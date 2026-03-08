package com.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.models.entity.Cliente;
import com.example.models.entity.Pedido;
import com.example.models.entity.PedidoItem;
import com.example.models.entity.Producto;
import com.example.repository.ClienteRepository;
import com.example.repository.PedidoRepository;
import com.example.repository.ProductoRepository;

@ExtendWith(MockitoExtension.class)
class PedidoServiceImplTest {

    @Mock
    PedidoRepository pedidoRepository;

    @Mock
    ProductoRepository productoRepository;

    @Mock
    ClienteRepository clienteRepository;

    @InjectMocks
    PedidoServiceImpl pedidoService;

    @Test
    void save_whenClienteNotFound_throws() {
        Pedido pedido = new Pedido();
        Cliente c = new Cliente();
        c.setId(99L);
        pedido.setCliente(c);

        when(clienteRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> pedidoService.save(pedido));
    }

    @Test
    void save_whenStockInsufficient_throws() {
        Pedido pedido = new Pedido();
        Cliente c = new Cliente();
        c.setId(1L);
        pedido.setCliente(c);

        PedidoItem item = new PedidoItem();
        Producto p = new Producto(1, "nombre", "desc", "col", "dim", 0, "10.0");
        item.setProducto(p);
        item.setCantidad(2);
        List<PedidoItem> items = new ArrayList<>();
        items.add(item);
        pedido.setItems(items);

        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(new Cliente()));
        when(productoRepository.findById(anyInt())).thenReturn(Optional.of(p));

        assertThrows(IllegalArgumentException.class, () -> pedidoService.save(pedido));
    }

    @Test
    void save_happyPath_calculatesTotalAndSaves() {
        Pedido pedido = new Pedido();
        Cliente c = new Cliente();
        c.setId(1L);
        pedido.setCliente(c);

        PedidoItem item = new PedidoItem();
        Producto p = new Producto(1, "nombre", "desc", "col", "dim", 10, "10.00");
        item.setProducto(p);
        item.setCantidad(2);
        item.setPrecio("10.00");
        List<PedidoItem> items = new ArrayList<>();
        items.add(item);
        pedido.setItems(items);

        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(new Cliente()));
        when(productoRepository.findById(anyInt())).thenReturn(Optional.of(p));
        when(pedidoRepository.save(pedido)).thenReturn(pedido);

        Pedido saved = pedidoService.save(pedido);
        assertEquals("20.00", saved.getTotal());
    }
}
