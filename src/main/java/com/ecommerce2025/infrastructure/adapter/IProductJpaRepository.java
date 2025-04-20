package com.ecommerce2025.infrastructure.adapter;

import com.ecommerce2025.infrastructure.dto.entity.ProductEntity; // Importa la entidad que representa un producto
import org.springframework.data.jpa.repository.JpaRepository; // Importa JpaRepository, que proporciona métodos CRUD básicos
import org.springframework.stereotype.Repository; // Importa la anotación que marca la clase como un repositorio

import java.util.List; // Importa List para poder retornar una lista de productos

/**
 * Interfaz para interactuar con la base de datos y realizar operaciones CRUD sobre los productos.
 * Esta interfaz extiende JpaRepository, lo que proporciona implementación automática de los métodos básicos
 * para trabajar con la entidad ProductEntity, además de un método personalizado para buscar productos por nombre.
 */
@Repository // Marca la interfaz como un componente de repositorio en Spring.
public interface IProductJpaRepository extends JpaRepository<ProductEntity, Integer> {

    /**
     * Busca productos cuyo nombre contenga el texto especificado (ignorando mayúsculas/minúsculas).
     * Este método usa la convención de nombres de Spring Data JPA para generar la consulta SQL automáticamente.
     *
     * @param query El texto que se buscará en los nombres de los productos.
     * @return Una lista de productos cuyo nombre contiene el texto proporcionado, sin importar mayúsculas o minúsculas.
     */
    List<ProductEntity> findByNameContainingIgnoreCase(String query);
}
