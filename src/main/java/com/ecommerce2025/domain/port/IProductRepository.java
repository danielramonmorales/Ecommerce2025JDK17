package com.ecommerce2025.domain.port;

import com.ecommerce2025.domain.model.Product;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de acceso al repositorio de productos.
 * Define las operaciones del dominio relacionadas con la entidad Product.
 */
public interface IProductRepository {

    /**
     * Guarda un nuevo producto o actualiza uno existente.
     * @param product el producto a guardar
     * @return el producto persistido
     */
    Product save(Product product);

    /**
     * Obtiene todos los productos.
     * @return una lista iterable de productos
     */
    Iterable<Product> findAll();

    /**
     * Busca un producto por su ID.
     * @param id el ID del producto
     * @return el producto encontrado
     */
    Product findById(Integer id);

    /**
     * Elimina un producto por su ID.
     * @param id el ID del producto a eliminar
     */
    void deleteById(Integer id);

    /**
     * Busca productos cuyo nombre contenga la cadena especificada (sin importar mayúsculas o minúsculas).
     * @param query cadena de búsqueda parcial
     * @return una lista de productos coincidentes
     */
    List<Product> findByNameContainingIgnoreCase(String query);

    Optional<Product> findByImagePublicId(String publicId);
}
