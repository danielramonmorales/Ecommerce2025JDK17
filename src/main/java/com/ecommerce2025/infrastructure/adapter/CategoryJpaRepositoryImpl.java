package com.ecommerce2025.infrastructure.adapter;

import com.ecommerce2025.domain.model.Category; // Importa el modelo de dominio Category
import com.ecommerce2025.domain.port.ICategoryRepository; // Importa la interfaz del repositorio de categorías en el dominio
import com.ecommerce2025.infrastructure.dto.entity.CategoryEntity; // Importa la entidad CategoryEntity que se usa en la base de datos
import com.ecommerce2025.infrastructure.mapper.CategoryMapper; // Importa el mapper para convertir entre entidades y modelos de dominio
import org.springframework.stereotype.Component;

/**
 * Implementación de la interfaz ICategoryRepository, que se encarga de realizar las operaciones CRUD
 * sobre las categorías utilizando JPA y un repositorio de base de datos.
 */
@Component // Marca esta clase como un componente de Spring para que sea manejada por el contenedor de Spring
public class CategoryJpaRepositoryImpl implements ICategoryRepository {

    // Inyección de dependencias para el repositorio de JPA y el mapper
    private final ICategoryJpaRepository iCategoryJpaRepository; // Repositorio de JPA para CategoryEntity
    private final CategoryMapper categoryMapper; // Mapper para convertir entre CategoryEntity y Category

    // Constructor con inyección de dependencias
    public CategoryJpaRepositoryImpl(ICategoryJpaRepository iCategoryJpaRepository, CategoryMapper categoryMapper) {
        this.iCategoryJpaRepository = iCategoryJpaRepository;
        this.categoryMapper = categoryMapper;
    }

    /**
     * Guarda una categoría en la base de datos.
     * Convierte el modelo de dominio Category a CategoryEntity, guarda la entidad en la base de datos,
     * y luego convierte de nuevo la entidad guardada a un modelo de dominio Category.
     *
     * @param category El modelo de dominio Category que se va a guardar.
     * @return La categoría guardada como un modelo de dominio Category.
     */
    @Override
    public Category save(Category category) {
        // Convierte Category a CategoryEntity, guarda la entidad, y convierte la entidad guardada de nuevo a Category
        return categoryMapper.toCategory(iCategoryJpaRepository.save(categoryMapper.toCategoryEntity(category)));
    }

    /**
     * Recupera todas las categorías desde la base de datos.
     * Utiliza el mapper para convertir una lista de CategoryEntity a una lista de Category.
     *
     * @return Una lista de todas las categorías como modelos de dominio Category.
     */
    @Override
    public Iterable<Category> findAll() {
        // Convierte todas las CategoryEntity a Category y devuelve una lista
        return categoryMapper.toCategoryList(iCategoryJpaRepository.findAll());
    }

    /**
     * Busca una categoría por su ID.
     * Si no se encuentra, lanza una excepción con un mensaje de error.
     *
     * @param id El ID de la categoría a buscar.
     * @return La categoría correspondiente al ID, como modelo de dominio Category.
     * @throws RuntimeException Si no se encuentra la categoría con el ID proporcionado.
     */
    @Override
    public Category findById(Integer id) {
        // Busca la categoría por ID, si no se encuentra, lanza una excepción con el mensaje de error
        return categoryMapper.toCategory(iCategoryJpaRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Categoria con id: " + id + " no existe")
        ));
    }

    /**
     * Elimina una categoría por su ID.
     * Si la categoría no existe, lanza una excepción con un mensaje de error.
     *
     * @param id El ID de la categoría a eliminar.
     * @throws RuntimeException Si no se encuentra la categoría con el ID proporcionado.
     */
    @Override
    public void deleteById(Integer id) {
        // Verifica si la categoría existe, si no, lanza una excepción
        iCategoryJpaRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Categoria con id: " + id + " no existe")
        );
        // Si existe, elimina la categoría
        iCategoryJpaRepository.deleteById(id);
    }

    /**
     * Actualiza una categoría existente.
     * Si la categoría con el ID especificado no existe, lanza una excepción.
     *
     * @param id             El ID de la categoría que se desea actualizar.
     * @param updatedCategory El modelo de dominio Category con los nuevos datos.
     * @return La categoría actualizada como un modelo de dominio Category.
     * @throws RuntimeException Si no se encuentra la categoría con el ID proporcionado.
     */
    @Override
    public Category updateCategory(Integer id, Category updatedCategory) {
        // Busca la categoría existente en la base de datos
        CategoryEntity existingEntity = iCategoryJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria con id: " + id + " no existe"));

        // Actualiza los campos de la categoría existente
        existingEntity.setName(updatedCategory.getName());
        existingEntity.setDescription(updatedCategory.getDescription());

        // Guarda la categoría actualizada en la base de datos
        CategoryEntity updatedEntity = iCategoryJpaRepository.save(existingEntity);

        // Convierte la entidad actualizada de vuelta a Category y la devuelve
        return categoryMapper.toCategory(updatedEntity);
    }
}
