package com.ecommerce2025.infrastructure.adapter;

import com.ecommerce2025.infrastructure.dto.entity.CategoryEntity; // Importa la entidad que representa una categoría
import org.springframework.data.jpa.repository.JpaRepository; // Importa JpaRepository, que proporciona métodos CRUD básicos
import org.springframework.stereotype.Repository; // Importa la anotación que marca la clase como un repositorio

/**
 * Interfaz para interactuar con la base de datos y realizar operaciones CRUD sobre las categorías.
 * Esta interfaz extiende JpaRepository, lo que proporciona implementación automática de los métodos básicos
 * para trabajar con la entidad CategoryEntity.
 */
@Repository // Marca la interfaz como un componente de repositorio en Spring.
public interface ICategoryJpaRepository extends JpaRepository<CategoryEntity, Integer> {
    // No es necesario escribir código adicional. JpaRepository ya ofrece métodos como:
    // - findAll()
    // - findById()
    // - save()
    // - deleteById()
    // y muchos otros de forma automática.
}
