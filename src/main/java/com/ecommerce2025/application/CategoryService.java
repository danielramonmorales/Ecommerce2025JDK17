package com.ecommerce2025.application;

import com.ecommerce2025.domain.model.Category;
import com.ecommerce2025.domain.port.ICategoryRepository;

/**
 * Servicio de aplicación para operaciones relacionadas con la entidad Category.
 * Actúa como orquestador entre los controladores y los repositorios del dominio.
 */
public class CategoryService {

    private final ICategoryRepository categoryRepository;

    public CategoryService(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Guarda una nueva categoría o actualiza una existente.
     * @param category la categoría a guardar
     * @return la categoría persistida
     */
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    /**
     * Recupera todas las categorías.
     * @return lista iterable de categorías
     */
    public Iterable<Category> findAll() {
        return categoryRepository.findAll();
    }

    /**
     * Busca una categoría por su ID.
     * @param id el ID de la categoría
     * @return la categoría encontrada
     * @throws RuntimeException si no se encuentra la categoría
     */
    public Category findById(Integer id) {
        Category category = categoryRepository.findById(id);
        if (category == null) {
            throw new RuntimeException("Categoría con ID " + id + " no encontrada.");
        }
        return category;
    }

    /**
     * Elimina una categoría por su ID.
     * @param id el ID de la categoría a eliminar
     */
    public void deleteById(Integer id) {
        categoryRepository.deleteById(id);
    }

    /**
     * Actualiza una categoría existente.
     * @param id el ID de la categoría a actualizar
     * @param category los nuevos datos de la categoría
     * @return la categoría actualizada
     */
    public Category updateCategory(Integer id, Category category) {
        return categoryRepository.updateCategory(id, category);
    }
}
