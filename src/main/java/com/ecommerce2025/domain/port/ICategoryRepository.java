package com.ecommerce2025.domain.port;

import com.ecommerce2025.domain.model.Category;
import org.springframework.context.annotation.Bean;


public interface ICategoryRepository {
    Category save(Category category);
    Iterable<Category> findAll();
    Category findById(Integer id);
    void deleteById(Integer id);
}