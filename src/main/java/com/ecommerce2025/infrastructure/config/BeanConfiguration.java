package com.ecommerce2025.infrastructure.config;


import com.ecommerce2025.application.CategoryService;
import com.ecommerce2025.application.ProductService;
import com.ecommerce2025.application.UserService;
import com.ecommerce2025.domain.port.ICategoryRepository;
import com.ecommerce2025.domain.port.IProductRepository;
import com.ecommerce2025.domain.port.IUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {



    @Bean
    public CategoryService categoryService(ICategoryRepository iCategoryRepository){
        return new CategoryService(iCategoryRepository);

    }

   @Bean
    public ProductService productService(IProductRepository iProductRepository){
        return new ProductService(iProductRepository);
    }

    @Bean
    public UserService userService(IUserRepository iUserRepository){
        return new UserService(iUserRepository);
    }





}

