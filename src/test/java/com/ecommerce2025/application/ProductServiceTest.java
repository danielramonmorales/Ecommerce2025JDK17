package com.ecommerce2025.application;

import com.ecommerce2025.domain.model.Product;
import com.ecommerce2025.domain.port.IProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceTest {

    private IProductRepository productRepository;
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        // Creamos el mock del repositorio
        productRepository = Mockito.mock(IProductRepository.class);
        productService = new ProductService(productRepository);
    }

    @Test
    public void testSaveProduct_Success() {
        // Creamos un producto para guardar
        Product product = new Product();
        product.setId(1);
        product.setName("Producto A");

        // Simulamos que el repositorio guarda el producto correctamente
        when(productRepository.save(product)).thenReturn(product);

        // Llamamos al servicio
        Product savedProduct = productService.save(product);

        // Verificamos que el producto devuelto es el mismo que se guardó
        assertNotNull(savedProduct);
        assertEquals(product.getId(), savedProduct.getId());
        assertEquals(product.getName(), savedProduct.getName());
    }

    @Test
    public void testSaveProduct_DataIntegrityViolation() {
        // Creamos un producto con una id existente para simular un error de integridad
        Product product = new Product();
        product.setId(1);
        product.setName("Producto B");

        // Simulamos que el repositorio lanza una excepción de integridad de datos
        when(productRepository.save(product)).thenThrow(DataIntegrityViolationException.class);

        // Llamamos al servicio y verificamos que se lanza una excepción
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.save(product);
        });

        // Verificamos que el mensaje de la excepción es el esperado
        assertTrue(exception.getMessage().contains("Error de integridad de datos al guardar el producto"));
    }

    @Test
    public void testFindById_Success() {
        // Creamos un producto para devolver
        Product product = new Product();
        product.setId(1);
        product.setName("Producto A");

        // Simulamos que el repositorio encuentra el producto
        when(productRepository.findById(1)).thenReturn(product);

        // Llamamos al servicio
        Product foundProduct = productService.findById(1);

        // Verificamos que el producto encontrado es el correcto
        assertNotNull(foundProduct);
        assertEquals(1, foundProduct.getId());
        assertEquals("Producto A", foundProduct.getName());
    }

    @Test
    public void testFindById_NotFound() {
        // Simulamos que el repositorio no encuentra el producto
        when(productRepository.findById(1)).thenReturn(null);

        // Llamamos al servicio y verificamos que se lanza una excepción
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.findById(1);
        });

        // Verificamos que el mensaje de la excepción es el esperado
        assertTrue(exception.getMessage().contains("Producto con ID 1 no encontrado."));
    }

    @Test
    public void testDeleteById() {
        // Llamamos al servicio para eliminar el producto
        productService.deleteById(1);

        // Verificamos que el repositorio fue llamado con el ID correcto
        verify(productRepository, times(1)).deleteById(1);
    }

    @Test
    public void testSearchProducts() {
        // Creamos algunos productos para devolver en la búsqueda
        Product product1 = new Product();
        product1.setId(1);
        product1.setName("Producto A");

        Product product2 = new Product();
        product2.setId(2);
        product2.setName("Producto B");

        // Simulamos que el repositorio devuelve una lista de productos
        when(productRepository.findByNameContainingIgnoreCase("Producto")).thenReturn(List.of(product1, product2));

        // Llamamos al servicio
        List<Product> foundProducts = productService.searchProducts("Producto");

        // Verificamos que la lista de productos no esté vacía
        assertNotNull(foundProducts);
        assertEquals(2, foundProducts.size());
    }
}
