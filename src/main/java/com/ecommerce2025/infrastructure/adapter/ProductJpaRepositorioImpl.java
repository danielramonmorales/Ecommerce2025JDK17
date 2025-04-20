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

@Component
public class ProductJpaRepositorioImpl implements IProductRepository {

    private final IProductJpaRepository iProductJpaRepository;
    private final ProductMapper productMapper;

    @PersistenceContext
    private EntityManager entityManager;

    public ProductJpaRepositorioImpl(IProductJpaRepository iProductJpaRepository, ProductMapper productMapper) {
        this.iProductJpaRepository = iProductJpaRepository;
        this.productMapper = productMapper;
    }

    @Override
    public Product save(Product product) {
        ProductEntity entity = productMapper.toProductEntity(product);

        // Asegurar que las relaciones están sincronizadas con el contexto
        entity.setUserEntity(entityManager.getReference(UserEntity.class, product.getUserId()));
        entity.setCategoryEntity(entityManager.getReference(CategoryEntity.class, product.getCategoryId()));

        return productMapper.toProduct(iProductJpaRepository.save(entity));
    }

    @Override
    public Iterable<Product> findAll() {
        return productMapper.toProductList(iProductJpaRepository.findAll());
    }

    @Override
    public Product findById(Integer id) {
        return productMapper.toProduct(iProductJpaRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Producto con id: " + id + " no existe")
        ));
    }

    @Override
    public void deleteById(Integer id) {
        iProductJpaRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Producto con id: " + id + " no existe")
        );
        iProductJpaRepository.deleteById(id);
    }

    @Override
    public List<Product> findByNameContainingIgnoreCase(String query) {
        List<ProductEntity> productEntities = iProductJpaRepository.findByNameContainingIgnoreCase(query);
        return (List<Product>) productMapper.toProductList(productEntities);
    }
}
