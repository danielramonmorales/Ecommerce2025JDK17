package com.ecommerce2025.infrastructure.controller;

import com.ecommerce2025.application.ProductService;
import com.ecommerce2025.domain.model.Product;
import com.ecommerce2025.infrastructure.exception.ProductNotFoundException;
import com.ecommerce2025.infrastructure.exception.BadRequestException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/admin/products")
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
@Tag(name = "Productos", description = "Operaciones para la gestión de productos del e-commerce")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Crear un nuevo producto", description = "Registra un nuevo producto en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<Product> save(@RequestBody Product product) {
        if (product == null || product.getName() == null || product.getName().trim().isEmpty()) {
            throw new BadRequestException("El nombre del producto no puede ser vacío.");
        }
        log.info("Nombre producto: {}", product.getName());
        return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar producto", description = "Modifica los datos de un producto existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Integer id, @RequestBody Product product) {
        if (product == null || product.getName() == null || product.getName().trim().isEmpty()) {
            throw new BadRequestException("El nombre del producto no puede ser vacío.");
        }
        Product existingProduct = productService.findById(id);
        if (existingProduct == null) {
            throw new ProductNotFoundException("Producto no encontrado con ID: " + id);
        }
        product.setId(id);
        return ResponseEntity.ok(productService.save(product));
    }

    @Operation(summary = "Listar todos los productos", description = "Devuelve un listado de todos los productos disponibles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<Iterable<Product>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @Operation(summary = "Buscar producto por ID", description = "Obtiene los detalles de un producto específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Integer id) {
        Product product = productService.findById(id);
        if (product == null) {
            throw new ProductNotFoundException("Producto no encontrado con ID: " + id);
        }
        return ResponseEntity.ok(product);
    }

    @Operation(summary = "Eliminar producto por ID", description = "Elimina un producto del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Integer id) {
        Product product = productService.findById(id);
        if (product == null) {
            throw new ProductNotFoundException("Producto no encontrado con ID: " + id);
        }
        productService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Buscar productos por nombre", description = "Filtra productos cuyo nombre contenga el texto ingresado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Búsqueda realizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Parámetro de búsqueda inválido"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String query) {
        if (query == null || query.trim().isEmpty()) {
            throw new BadRequestException("El parámetro de búsqueda no puede ser vacío.");
        }
        return productService.searchProducts(query);
    }
}
