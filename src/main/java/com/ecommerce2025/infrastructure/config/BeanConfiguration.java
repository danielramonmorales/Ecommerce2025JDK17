package com.ecommerce2025.infrastructure.config;


import com.ecommerce2025.application.CategoryService;
import com.ecommerce2025.domain.port.ICategoryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {



    @Bean
    public CategoryService categoryService(ICategoryRepository iCategoryRepository){
        return new CategoryService(iCategoryRepository);

    }





}

