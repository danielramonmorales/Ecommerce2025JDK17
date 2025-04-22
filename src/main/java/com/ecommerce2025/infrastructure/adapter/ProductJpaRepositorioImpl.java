package com.ecommerce2025.infrastructure.adapter;

import com.ecommerce2025.domain.model.Product;
import com.ecommerce2025.domain.port.IProductRepository;
import com.ecommerce2025.infrastructure.dto.entity.CategoryEntity;
import com.ecommerce2025.infrastructure.dto.entity.ProductEntity;
import com.ecommerce2025.infrastructure.dto.entity.UserEntity;
import com.ecommerce2025.infrastructure.mapper.ProductMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component // Anotación para indicar que esta clase es un componente gestionado por Spring
public class ProductJpaRepositorioImpl implements IProductRepository {

    // Repositorio JPA para interactuar con la base de datos para operaciones de productos
    private final IProductJpaRepository iProductJpaRepository;

    // Mapper para convertir entre el modelo de dominio 'Product' y la entidad JPA 'ProductEntity'
    private final ProductMapper productMapper;

    // EntityManager para interactuar con la base de datos, utilizado para manejar relaciones entre entidades
    @PersistenceContext
    private EntityManager entityManager;

    // Constructor que inyecta las dependencias de los repositorios y el mapper
    public ProductJpaRepositorioImpl(IProductJpaRepository iProductJpaRepository, ProductMapper productMapper) {
        this.iProductJpaRepository = iProductJpaRepository;
        this.productMapper = productMapper;
    }

    /**
     * Guarda un producto en la base de datos.
     * Convierte el objeto de dominio a una entidad JPA, maneja las relaciones con User y Category,
     * y guarda el producto utilizando el repositorio JPA.
     * @param product El producto a guardar
     * @return El producto guardado convertido de nuevo a un objeto de dominio
     */
    @Override
    public Product save(Product product) {
        // Convertir el producto del modelo de dominio a una entidad JPA
        ProductEntity entity = productMapper.toProductEntity(product);

        // Asegurar que las relaciones con User y Category se gestionen correctamente, obteniendo referencias a las entidades
        // getReference() no carga la entidad completa, solo mantiene una referencia a ella, lo que es más eficiente
        entity.setUserEntity(entityManager.getReference(UserEntity.class, product.getUserId()));
        entity.setCategoryEntity(entityManager.getReference(CategoryEntity.class, product.getCategoryId()));

        // Guardar la entidad y convertirla de vuelta a un objeto de dominio
        return productMapper.toProduct(iProductJpaRepository.save(entity));
    }

    /**
     * Recupera todos los productos de la base de datos.
     * @return Una lista de productos, convertidos desde las entidades a objetos de dominio
     */
    @Override
    public Iterable<Product> findAll() {
        // Buscar todos los productos y convertir las entidades a objetos de dominio
        return productMapper.toProductList(iProductJpaRepository.findAll());
    }

    /**
     * Busca un producto por su ID.
     * Si no se encuentra, lanza una excepción.
     * @param id El ID del producto
     * @return El producto correspondiente al ID, convertido a un objeto de dominio
     * @throws RuntimeException Si no se encuentra el producto
     */
    @Override
    public Product findById(Integer id) {
        // Buscar el producto por su ID y convertirlo al modelo de dominio
        // Si no se encuentra, se lanza una excepción RuntimeException
        return productMapper.toProduct(iProductJpaRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Producto con id: " + id + " no existe")
        ));
    }

    /**
     * Elimina un producto de la base de datos por su ID.
     * Si no existe el producto, lanza una excepción.
     * @param id El ID del producto a eliminar
     * @throws RuntimeException Si el producto no existe
     */
    @Override
    public void deleteById(Integer id) {
        // Verificar que el producto existe antes de eliminarlo
        // Si no existe, lanza una excepción RuntimeException
        iProductJpaRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Producto con id: " + id + " no existe")
        );
        // Eliminar el producto de la base de datos
        iProductJpaRepository.deleteById(id);
    }

    /**
     * Busca productos cuyo nombre contenga una cadena de texto específica (ignorando mayúsculas/minúsculas).
     * @param query La cadena de búsqueda
     * @return Una lista de productos que coinciden con la búsqueda
     */
    @Override
    public List<Product> findByNameContainingIgnoreCase(String query) {
        // Buscar productos que contengan la cadena de búsqueda en el nombre
        List<ProductEntity> productEntities = iProductJpaRepository.findByNameContainingIgnoreCase(query);
        // Convertir las entidades a objetos de dominio y devolver la lista
        return (List<Product>) productMapper.toProductList(productEntities);
    }

    @Override
    public Optional<Product> findByImagePublicId(String publicId) {
        ProductEntity entity = iProductJpaRepository.findByImagePublicId(publicId);
        return Optional.ofNullable(productMapper.toProduct(entity));
    }
}
