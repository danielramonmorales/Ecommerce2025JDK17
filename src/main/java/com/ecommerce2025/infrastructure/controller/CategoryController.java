package com.ecommerce2025.infrastructure.controller;

import com.ecommerce2025.application.CategoryService;
import com.ecommerce2025.domain.model.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// URL base para este controlador: http://localhost:8080/api/v1/admin/categories
@RestController // Indica que esta clase es un controlador de Spring que maneja solicitudes HTTP
@RequestMapping("/api/v1/admin/categories") // Define la URL de la API para las categorías
@Slf4j // Habilita el registro de logs mediante SLF4J (útil para depuración y auditoría)
@CrossOrigin(origins = "http://localhost:4200") // Permite solicitudes desde el frontend en el puerto 4200
public class CategoryController {

    private final CategoryService categoryService; // El servicio que maneja las operaciones de categorías

    // Constructor de la clase, con inyección de dependencias del CategoryService
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Crea una nueva categoría.
     * @param category La categoría a guardar, proporcionada en el cuerpo de la solicitud.
     * @return La categoría creada, con código de estado 201 (CREATED).
     */
    @PostMapping // Mapeo para la creación de una categoría mediante POST
    public ResponseEntity<Category> save(@RequestBody Category category){
        // Llama al servicio para guardar la categoría y devuelve una respuesta con el estado CREATED
        return new ResponseEntity<>(categoryService.save(category), HttpStatus.CREATED);
    }

    /**
     * Obtiene todas las categorías.
     * @return Una lista iterable de todas las categorías.
     */
    @GetMapping // Mapeo para obtener todas las categorías mediante GET
    public ResponseEntity<Iterable<Category>> findAll(){
        // Llama al servicio para obtener todas las categorías y devuelve una respuesta OK
        return ResponseEntity.ok(categoryService.findAll());
    }

    /**
     * Obtiene una categoría por su ID.
     * @param id El ID de la categoría a buscar.
     * @return La categoría correspondiente al ID.
     */
    @GetMapping("/{id}") // Mapeo para obtener una categoría por su ID mediante GET
    public ResponseEntity<Category> findById(@PathVariable Integer id){
        // Llama al servicio para obtener la categoría por ID y devuelve una respuesta OK
        return ResponseEntity.ok(categoryService.findById(id));
    }

    /**
     * Elimina una categoría por su ID.
     * @param id El ID de la categoría a eliminar.
     * @return Una respuesta con código de estado OK si la eliminación fue exitosa.
     */
    @DeleteMapping("/{id}") // Mapeo para eliminar una categoría por su ID mediante DELETE
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Integer id){
        // Llama al servicio para eliminar la categoría por ID
        categoryService.deleteById(id);
        // Devuelve una respuesta sin cuerpo pero con el estado OK
        return ResponseEntity.ok().build();
    }

    /**
     * Actualiza una categoría existente por su ID.
     * @param id El ID de la categoría a actualizar.
     * @param category La nueva categoría con los datos actualizados.
     * @return La categoría actualizada.
     */
    @PutMapping("/{id}") // Mapeo para actualizar una categoría por su ID mediante PUT
    public ResponseEntity<Category> updateCategory(@PathVariable Integer id, @RequestBody Category category) {
        // Llama al servicio para actualizar la categoría y devuelve una respuesta OK con la categoría actualizada
        Category updatedCategory = categoryService.updateCategory(id, category);
        return ResponseEntity.ok(updatedCategory);
    }
}
