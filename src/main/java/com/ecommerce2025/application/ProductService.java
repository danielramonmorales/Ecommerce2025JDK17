package com.ecommerce2025.application;

import com.ecommerce2025.domain.model.Product;
import com.ecommerce2025.domain.port.IProductRepository;

import java.io.IOException;
import java.util.List;

public class ProductService {
    private final IProductRepository iProductRepository;


    public ProductService(IProductRepository iProductRepository) {
        this.iProductRepository = iProductRepository;
    }


    public Product save(Product product) throws IOException {
        return this.iProductRepository.save(product);
    }


    public Iterable<Product> findAll(){
        return this.iProductRepository.findAll();
    }

    public Product findById(Integer id){
        return this.iProductRepository.findById(id);
    }
    public void deleteById(Integer id){
        this.iProductRepository.deleteById(id);
    }



    public List<Product> searchProducts(String query) {
        return iProductRepository.findByNameContainingIgnoreCase(query);
    }
}
