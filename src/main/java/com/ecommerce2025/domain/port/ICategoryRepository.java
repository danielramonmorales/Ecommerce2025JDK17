package com.ecommerce2025.domain.port;

import com.ecommerce2025.domain.model.Category;

/**
 * Puerto de acceso a datos para la entidad Category.
 * Define las operaciones del dominio relacionadas con categorías.
 */
public interface ICategoryRepository {

    /**
     * Guarda una nueva categoría o actualiza una existente.
     * @param category la categoría a guardar
     * @return la categoría persistida
     */
    Category save(Category category);

    /**
     * Obtiene todas las categorías.
     * @return una lista iterable de categorías
     */
    Iterable<Category> findAll();

    /**
     * Busca una categoría por su ID.
     * @param id el ID de la categoría
     * @return la categoría encontrada
     */
    Category findById(Integer id);

    /**
     * Elimina una categoría por su ID.
     * @param id el ID de la categoría
     */
    void deleteById(Integer id);

    /**
     * Actualiza una categoría existente.
     * @param id ID de la categoría a actualizar
     * @param updatedCategory los nuevos datos de la categoría
     * @return la categoría actualizada
     */
    Category updateCategory(Integer id, Category updatedCategory);
}
