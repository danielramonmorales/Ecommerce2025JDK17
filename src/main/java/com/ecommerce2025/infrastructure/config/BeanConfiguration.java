package com.ecommerce2025.infrastructure.config;

import com.ecommerce2025.application.CategoryService;
import com.ecommerce2025.application.ProductService;
import com.ecommerce2025.application.UserService;
import com.ecommerce2025.domain.port.ICategoryRepository;
import com.ecommerce2025.domain.port.IProductRepository;
import com.ecommerce2025.domain.port.IUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // Indica que esta clase contiene configuraciones de beans de Spring
public class BeanConfiguration {

    /**
     * Crea un bean de tipo CategoryService que es gestionado por el contenedor de Spring.
     * Este bean depende de un repositorio de categoría.
     * @param iCategoryRepository El repositorio de categorías a inyectar en el servicio
     * @return Una instancia de CategoryService
     */
    @Bean
    public CategoryService categoryService(ICategoryRepository iCategoryRepository){
        // Devuelve una nueva instancia de CategoryService, inyectando el repositorio necesario
        return new CategoryService(iCategoryRepository);
    }

    /**
     * Crea un bean de tipo ProductService que es gestionado por el contenedor de Spring.
     * Este bean depende de un repositorio de productos.
     * @param iProductRepository El repositorio de productos a inyectar en el servicio
     * @return Una instancia de ProductService
     */
    @Bean
    public ProductService productService(IProductRepository iProductRepository){
        // Devuelve una nueva instancia de ProductService, inyectando el repositorio necesario
        return new ProductService(iProductRepository);
    }

    /**
     * Crea un bean de tipo UserService que es gestionado por el contenedor de Spring.
     * Este bean depende de un repositorio de usuarios.
     * @param iUserRepository El repositorio de usuarios a inyectar en el servicio
     * @return Una instancia de UserService
     */
    @Bean
    public UserService userService(IUserRepository iUserRepository){
        // Devuelve una nueva instancia de UserService, inyectando el repositorio necesario
        return new UserService(iUserRepository);
    }

}
