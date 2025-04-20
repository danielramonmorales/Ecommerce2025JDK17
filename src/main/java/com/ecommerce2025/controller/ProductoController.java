package com.ecommerce2025.controller;

import com.ecommerce2025.dto.ProductoDTO;
import com.ecommerce2025.mapper.ProductoMapper;
import com.ecommerce2025.model.Producto;
import com.ecommerce2025.service.ProductoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService service;
    private final ProductoMapper mapper;

    public ProductoController(ProductoService service, ProductoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ProductoDTO crearProducto(@RequestBody ProductoDTO dto) {
        Producto producto = mapper.toEntity(dto);
        Producto creado = service.crearProducto(producto);
        return mapper.toDto(creado);
    }

    @GetMapping
    public List<ProductoDTO> listarTodos() {
        return service.listarProductos()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductoDTO buscarPorId(@PathVariable Long id) {
        Producto producto = service.buscarPorId(id);
        return mapper.toDto(producto);
    }
}
