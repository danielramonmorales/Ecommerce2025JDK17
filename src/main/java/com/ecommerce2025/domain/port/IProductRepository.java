package com.ecommerce2025.domain.port;

import com.ecommerce2025.domain.model.Product;

import java.util.List;

public interface IProductRepository {
    Product save(Product product);
    Iterable<Product> findAll();
    Product findById(Integer id);
    void deleteById(Integer id);


    List<Product> findByNameContainingIgnoreCase(String query);
}