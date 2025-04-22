package com.ecommerce2025.infrastructure.config;

import com.ecommerce2025.application.CategoryService;
import com.ecommerce2025.application.OrderService;
import com.ecommerce2025.application.ProductService;
import com.ecommerce2025.application.UserService;
import com.ecommerce2025.domain.port.ICategoryRepository;
import com.ecommerce2025.domain.port.IOrderRepository;
import com.ecommerce2025.domain.port.IProductRepository;
import com.ecommerce2025.domain.port.IUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Clase de configuración para registrar los servicios (casos de uso) como beans en el contexto de Spring.
 * Aquí se definen explícitamente las instancias de los servicios y sus dependencias.
 */
@Configuration // Indica que esta clase contiene configuraciones de beans de Spring
public class BeanConfiguration {

    /**
     * Bean para el servicio de categorías.
     * @param iCategoryRepository Repositorio de categorías
     * @return una instancia de CategoryService
     */
    @Bean
    public CategoryService categoryService(ICategoryRepository iCategoryRepository){
        return new CategoryService(iCategoryRepository);
    }

    /**
     * Bean para el servicio de productos.
     * @param iProductRepository Repositorio de productos
     * @return una instancia de ProductService
     */
    @Bean
    public ProductService productService(IProductRepository iProductRepository){
        return new ProductService(iProductRepository);
    }

    /**
     * Bean para el servicio de usuarios.
     * @param iUserRepository Repositorio de usuarios
     * @return una instancia de UserService
     */
    @Bean
    public UserService userService(IUserRepository iUserRepository){
        return new UserService(iUserRepository);
    }

    /**
     * Bean para el servicio de órdenes.
     * @param iOrderRepository Repositorio de órdenes
     * @return una instancia de OrderService
     */
    @Bean
    public OrderService orderService(IOrderRepository iOrderRepository, IProductRepository iProductRepository){
        return new OrderService(iOrderRepository, iProductRepository);
    }
}
