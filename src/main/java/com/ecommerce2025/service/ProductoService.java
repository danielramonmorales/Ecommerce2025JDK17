package com.ecommerce2025.service;

import com.ecommerce2025.model.Producto;
import com.ecommerce2025.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository repository;

    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }

    public Producto crearProducto(Producto producto) {
        producto.setCreadoEn(LocalDateTime.now());
        return repository.save(producto);
    }

    public List<Producto> listarProductos() {
        return repository.findAll();
    }

    public Producto buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID " + id));
    }
}
