package com.ecommerce2025.application;

import com.ecommerce2025.domain.model.Product;
import com.ecommerce2025.domain.port.IProductRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio para manejar operaciones de productos.
 * Permite la creación, lectura, eliminación y búsqueda de productos.
 */
@Service
public class ProductService {

    private final IProductRepository productRepository;

    public ProductService(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Guarda un producto en el repositorio.
     * Si ocurre un error de integridad, se lanza una excepción con un mensaje detallado.
     * @param product el producto a guardar
     * @return el producto guardado
     */
    public Product save(Product product) {
        try {
            return productRepository.save(product);
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("Error de integridad de datos al guardar el producto", ex);
        }
    }

    /**
     * Recupera todos los productos.
     * @return todos los productos
     */
    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    /**
     * Busca un producto por su ID.
     * @param id el ID del producto
     * @return el producto encontrado
     * @throws RuntimeException si no se encuentra el producto con el ID proporcionado
     */
    public Product findById(Integer id) {
        Product product = productRepository.findById(id);
        if (product == null) {
            throw new RuntimeException("Producto con ID " + id + " no encontrado.");
        }
        return product;
    }

    /**
     * Elimina un producto por su ID.
     * @param id el ID del producto a eliminar
     */
    public void deleteById(Integer id) {
        productRepository.deleteById(id);
    }

    /**
     * Busca productos por nombre.
     * @param query la cadena de texto para buscar en los nombres de los productos
     * @return una lista de productos que contienen elquery en su nombre
     */
    public List<Product> searchProducts(String query) {
        return productRepository.findByNameContainingIgnoreCase(query);
    }
}
