package com.ecommerce2025.infrastructure.controller;

import com.ecommerce2025.application.ProductService;
import com.ecommerce2025.domain.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile; // No se está utilizando en este código pero puede ser útil para cargar archivos.

import java.io.IOException;
import java.math.BigDecimal; // No se está utilizando en este código pero puede ser útil si hay una lógica de precios.
import java.util.List;

// Controlador REST para manejar las solicitudes relacionadas con los productos
@RestController
@RequestMapping("api/v1/admin/products") // Base URL para este controlador
@Slf4j // Anotación para habilitar el registro de logs con SLF4J
@CrossOrigin(origins = "http://localhost:4200") // Permite solicitudes desde el frontend en el puerto 4200
public class ProductController {

    private final ProductService productService; // Servicio que maneja la lógica de negocios de productos

    // Constructor de la clase, con inyección de dependencias de ProductService
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Método para crear un nuevo producto.
     * @param product El producto a crear, proporcionado en el cuerpo de la solicitud.
     * @return El producto creado con código HTTP 201 (CREATED).
     */
    @PostMapping // Mapeo para la creación de un producto mediante POST
    public ResponseEntity<Product> save(@RequestBody Product product) {
        log.info("Nombre producto: {}", product.getName()); // Loguea el nombre del producto
        // Llama al servicio para guardar el producto y devuelve la respuesta con el estado CREATED
        return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
    }

    /**
     * Método para actualizar un producto existente.
     * @param id El ID del producto a actualizar.
     * @param product El producto con los nuevos valores.
     * @return El producto actualizado.
     */
    @PutMapping("/{id}") // Mapeo para actualizar un producto por su ID mediante PUT
    public ResponseEntity<Product> update(@PathVariable Integer id, @RequestBody Product product) {
        product.setId(id); // Asegura que el ID que se está actualizando sea el proporcionado en la URL
        // Llama al servicio para actualizar el producto y devuelve la respuesta con el producto actualizado
        return ResponseEntity.ok(productService.save(product));
    }

    /**
     * Método para obtener todos los productos.
     * @return Una lista de todos los productos con código HTTP 200 (OK).
     */
    @GetMapping // Mapeo para obtener todos los productos mediante GET
    public ResponseEntity<Iterable<Product>> findAll(){
        // Llama al servicio para obtener todos los productos y devuelve la respuesta con los productos
        return ResponseEntity.ok(productService.findAll());
    }

    /**
     * Método para obtener un producto por su ID.
     * @param id El ID del producto a buscar.
     * @return El producto correspondiente con código HTTP 200 (OK).
     */
    @GetMapping("/{id}") // Mapeo para obtener un producto por su ID mediante GET
    public ResponseEntity<Product> findById(@PathVariable Integer id){
        // Llama al servicio para obtener el producto por su ID y devuelve la respuesta con el producto
        return ResponseEntity.ok(productService.findById(id));
    }

    /**
     * Método para eliminar un producto por su ID.
     * @param id El ID del producto a eliminar.
     * @return Respuesta vacía con código HTTP 200 (OK) si se elimina correctamente.
     */
    @DeleteMapping("/{id}") // Mapeo para eliminar un producto por su ID mediante DELETE
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Integer id){
        // Llama al servicio para eliminar el producto por su ID
        productService.deleteById(id);
        // Devuelve una respuesta vacía con código HTTP 200 (OK)
        return ResponseEntity.ok().build();
    }

    /**
     * Método para buscar productos por nombre.
     * @param query La cadena de búsqueda para encontrar productos cuyo nombre contenga la consulta.
     * @return Lista de productos que coinciden con la búsqueda.
     */
    @GetMapping("/search") // Mapeo para buscar productos mediante GET
    public List<Product> searchProducts(@RequestParam String query) {
        // Llama al servicio para buscar productos según la consulta
        return productService.searchProducts(query);
    }
}
