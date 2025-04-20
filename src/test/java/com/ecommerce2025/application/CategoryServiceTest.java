package com.ecommerce2025.application;

import com.ecommerce2025.domain.model.Category;
import com.ecommerce2025.domain.port.ICategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CategoryServiceTest {

    private ICategoryRepository categoryRepository;
    private CategoryService categoryService;

    @BeforeEach
    public void setUp() {
        // Creamos el mock del repositorio
        categoryRepository = Mockito.mock(ICategoryRepository.class);
        categoryService = new CategoryService(categoryRepository);
    }

    @Test
    public void testSaveCategory_Success() {
        // Creamos una categoría para guardar
        Category category = new Category();
        category.setId(1);
        category.setName("Categoría A");

        // Simulamos que el repositorio guarda la categoría correctamente
        when(categoryRepository.save(category)).thenReturn(category);

        // Llamamos al servicio
        Category savedCategory = categoryService.save(category);

        // Verificamos que la categoría devuelta es la misma que la que se guardó
        assertNotNull(savedCategory);
        assertEquals(category.getId(), savedCategory.getId());
        assertEquals(category.getName(), savedCategory.getName());
    }

    @Test
    public void testFindById_Success() {
        // Creamos una categoría para devolver
        Category category = new Category();
        category.setId(1);
        category.setName("Categoría A");

        // Simulamos que el repositorio encuentra la categoría
        when(categoryRepository.findById(1)).thenReturn(category);

        // Llamamos al servicio
        Category foundCategory = categoryService.findById(1);

        // Verificamos que la categoría encontrada es la correcta
        assertNotNull(foundCategory);
        assertEquals(1, foundCategory.getId());
        assertEquals("Categoría A", foundCategory.getName());
    }

    @Test
    public void testFindById_NotFound() {
        // Simulamos que el repositorio no encuentra la categoría
        when(categoryRepository.findById(1)).thenReturn(null);

        // Llamamos al servicio y verificamos que se lanza una excepción
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            categoryService.findById(1);
        });

        // Verificamos que el mensaje de la excepción es el esperado
        assertTrue(exception.getMessage().contains("Categoría con ID 1 no encontrada."));
    }

    @Test
    public void testDeleteById() {
        // Llamamos al servicio para eliminar la categoría
        categoryService.deleteById(1);

        // Verificamos que el repositorio fue llamado con el ID correcto
        verify(categoryRepository, times(1)).deleteById(1);
    }

    @Test
    public void testUpdateCategory_Success() {
        // Creamos una categoría existente
        Category existingCategory = new Category();
        existingCategory.setId(1);
        existingCategory.setName("Categoría A");

        // Creamos una categoría para actualizar
        Category updatedCategory = new Category();
        updatedCategory.setName("Categoría Actualizada");

        // Simulamos que el repositorio devuelve la categoría actualizada
        when(categoryRepository.updateCategory(1, updatedCategory)).thenReturn(updatedCategory);

        // Llamamos al servicio para actualizar la categoría
        Category categoryAfterUpdate = categoryService.updateCategory(1, updatedCategory);

        // Verificamos que la categoría actualizada tiene los nuevos valores
        assertNotNull(categoryAfterUpdate);
        assertEquals(updatedCategory.getName(), categoryAfterUpdate.getName());
    }

    @Test
    public void testSaveCategory_Failure() {
        // Creamos una categoría con datos inválidos (por ejemplo, una categoría sin nombre)
        Category category = new Category();
        category.setId(1);
        category.setName(null);

        // Simulamos que el repositorio lanza una excepción de integridad de datos
        when(categoryRepository.save(category)).thenThrow(IllegalArgumentException.class);

        // Llamamos al servicio y verificamos que se lanza una excepción
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            categoryService.save(category);
        });

        // Verificamos que el mensaje de la excepción es el esperado
        assertTrue(exception.getMessage().contains("Error al guardar la categoría"));
    }
}
