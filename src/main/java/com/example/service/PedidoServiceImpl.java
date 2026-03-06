package com.example.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.models.entity.Cliente;
import com.example.models.entity.Pedido;
import com.example.models.entity.PedidoItem;
import com.example.models.entity.Producto;
import com.example.repository.ClienteRepository;
import com.example.repository.PedidoRepository;
import com.example.repository.ProductoRepository;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public Optional<Pedido> findById(Long id) {
        return pedidoRepository.findById(id);
    }

    @Override
    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    /**
     * Guarda un pedido validando que los productos existan y tengan stock suficiente.
     * - Carga el Producto gestionado desde DB
     * - Decrementa el stock
     * - Calcula el total (BigDecimal) y lo almacena como String por compatibilidad
     * - Persiste el Pedido y sus items (CascadeType.ALL asumido en la entidad)
     */
    @Override
    @Transactional
    public Pedido save(Pedido pedido) {
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido no puede ser null");
        }

        // --- Cargar cliente gestionado (Opción recomendada) ---
        if (pedido.getCliente() == null || pedido.getCliente().getId() == null) {
            throw new IllegalArgumentException("Pedido debe incluir un cliente con id");
        }
        Long clienteId = pedido.getCliente().getId();
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado id=" + clienteId));
        pedido.setCliente(cliente);

        BigDecimal total = BigDecimal.ZERO;

        List<PedidoItem> items = pedido.getItems();
        if (items != null && !items.isEmpty()) {
            for (PedidoItem item : items) {
                if (item == null) {
                    continue; // saltar items nulos
                }

                if (item.getProducto() == null) {
                    throw new IllegalArgumentException("Cada PedidoItem debe incluir un producto (con su id)");
                }

                int prodId = item.getProducto().getId_Producto();
                if (prodId <= 0) {
                    throw new IllegalArgumentException("Producto.id inválido: " + prodId);
                }

                Producto producto = productoRepository.findById(prodId)
                        .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado id=" + prodId));

                Integer cantidad = item.getCantidad() == null ? 0 : item.getCantidad();

                if (cantidad < 0) {
                    throw new IllegalArgumentException("Cantidad no puede ser negativa: " + cantidad);
                }

                if (producto.getStock() < cantidad) {
                    throw new IllegalArgumentException("Stock insuficiente para producto id=" + prodId + ", stock="
                            + producto.getStock() + ", pedido=" + cantidad);
                }

                // decrementar stock y persistir el producto actualizado
                producto.setStock(producto.getStock() - cantidad);
                productoRepository.save(producto);

                // asegurar la relación entre item y pedido y asignar el producto gestionado
                item.setProducto(producto);
                item.setPedido(pedido);

                // obtener precio (priorizar precio en item, si no usar producto.precio)
                BigDecimal precioItem = BigDecimal.ZERO;
                String precioStr = item.getPrecio() != null ? item.getPrecio() : producto.getPrecio();
                if (precioStr != null && !precioStr.isBlank()) {
                    try {
                        precioStr = precioStr.replace(',', '.').trim();
                        precioItem = new BigDecimal(precioStr);
                    } catch (Exception e) {
                        // si no es parseable, dejar precioItem en 0
                        precioItem = BigDecimal.ZERO;
                    }
                }

                BigDecimal subtotal = precioItem.multiply(new BigDecimal(cantidad));
                total = total.add(subtotal);
            }
        }

        // Guardar total como String para mantener compatibilidad con el modelo existente
        pedido.setTotal(total.toPlainString());

        return pedidoRepository.save(pedido);
    }
    
    @Override
    @Transactional
    public Optional<Pedido> update(Long id, Pedido p) {
        return pedidoRepository.findById(id).map(existente -> {

            // 1. Devolver stock de los items anteriores
            for (PedidoItem oldItem : existente.getItems()) {
                Producto prod = oldItem.getProducto();
                prod.setStock(prod.getStock() + oldItem.getCantidad());
                productoRepository.save(prod);
            }

            // 2. Actualizar estado
            if (p.getEstado() != null) {
                existente.setEstado(p.getEstado());
            }

            // 3. Actualizar cliente si viene en el payload
            if (p.getCliente() != null && p.getCliente().getId() != null) {
                Cliente cliente = clienteRepository.findById(p.getCliente().getId())
                    .orElseThrow(() -> new IllegalArgumentException(
                        "Cliente no encontrado id=" + p.getCliente().getId()));
                existente.setCliente(cliente);
            }

            // 4. Reemplazar items y descontar stock nuevo
            existente.getItems().clear();
            BigDecimal total = BigDecimal.ZERO;

            if (p.getItems() != null) {
                for (PedidoItem newItem : p.getItems()) {
                    if (newItem.getProducto() == null) {
                        throw new IllegalArgumentException("Cada item debe incluir un producto con id");
                    }

                    int prodId = newItem.getProducto().getId_Producto();
                    Producto producto = productoRepository.findById(prodId)
                        .orElseThrow(() -> new IllegalArgumentException(
                            "Producto no encontrado id=" + prodId));

                    int cantidad = newItem.getCantidad() == null ? 0 : newItem.getCantidad();
                    if (producto.getStock() < cantidad) {
                        throw new IllegalArgumentException(
                            "Stock insuficiente para producto id=" + prodId
                            + " (disponible=" + producto.getStock() + ", pedido=" + cantidad + ")");
                    }

                    producto.setStock(producto.getStock() - cantidad);
                    productoRepository.save(producto);

                    newItem.setProducto(producto);

                    // Calcular precio del item
                    BigDecimal precioItem = BigDecimal.ZERO;
                    String precioStr = newItem.getPrecio() != null
                        ? newItem.getPrecio()
                        : producto.getPrecio();
                    if (precioStr != null && !precioStr.isBlank()) {
                        try {
                            precioItem = new BigDecimal(precioStr.replace(',', '.').trim());
                        } catch (Exception ignored) {}
                    }
                    total = total.add(precioItem.multiply(new BigDecimal(cantidad)));

                    existente.addItem(newItem);
                }
            }

            existente.setTotal(total.toPlainString());
            return pedidoRepository.save(existente);
        });
    }


    @Override
    public void deleteById(Long id) {
        pedidoRepository.deleteById(id);
    }
}