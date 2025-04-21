package com.ecommerce2025.infrastructure.controller;

import com.ecommerce2025.application.CategoryService;
import com.ecommerce2025.domain.model.Category;
import com.ecommerce2025.infrastructure.exception.CategoryNotFoundException;
import com.ecommerce2025.infrastructure.exception.BadRequestException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/categories")
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
@Tag(name = "Categorías", description = "Operaciones relacionadas con la gestión de categorías del e-commerce")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Crear nueva categoría", description = "Permite registrar una nueva categoría")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoría creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<Category> save(@RequestBody Category category) {
        if (category == null || category.getName() == null || category.getName().trim().isEmpty()) {
            throw new BadRequestException("El nombre de la categoría no puede ser vacío.");
        }
        return new ResponseEntity<>(categoryService.save(category), HttpStatus.CREATED);
    }

    @Operation(summary = "Listar todas las categorías", description = "Retorna todas las categorías disponibles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<Iterable<Category>> findAll() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @Operation(summary = "Buscar categoría por ID", description = "Retorna los datos de una categoría específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoría encontrada"),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable Integer id) {
        Category category = categoryService.findById(id);
        if (category == null) {
            throw new CategoryNotFoundException("Categoría no encontrada con ID: " + id);
        }
        return ResponseEntity.ok(category);
    }

    @Operation(summary = "Eliminar categoría por ID", description = "Elimina una categoría existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoría eliminada"),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Integer id) {
        Category category = categoryService.findById(id);
        if (category == null) {
            throw new CategoryNotFoundException("Categoría no encontrada con ID: " + id);
        }
        categoryService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Actualizar categoría", description = "Modifica los datos de una categoría existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoría actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Integer id, @RequestBody Category category) {
        Category existingCategory = categoryService.findById(id);
        if (existingCategory == null) {
            throw new CategoryNotFoundException("Categoría no encontrada con ID: " + id);
        }
        if (category.getName() == null || category.getName().trim().isEmpty()) {
            throw new BadRequestException("El nombre de la categoría no puede ser vacío.");
        }
        category.setId(id); // Asegura que el ID sea el correcto
        return ResponseEntity.ok(categoryService.save(category));
    }
}
