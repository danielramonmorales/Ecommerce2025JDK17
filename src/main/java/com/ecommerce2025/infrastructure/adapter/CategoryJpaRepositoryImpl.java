package com.ecommerce2025.infrastructure.adapter;

import com.ecommerce2025.domain.model.Category;
import com.ecommerce2025.domain.port.ICategoryRepository;
import com.ecommerce2025.infrastructure.dto.entity.CategoryEntity;
import com.ecommerce2025.infrastructure.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryJpaRepositoryImpl implements ICategoryRepository {

    private final ICategoryJpaRepository iCategoryJpaRepository;
    private final CategoryMapper categoryMapper;

    public CategoryJpaRepositoryImpl(ICategoryJpaRepository iCategoryJpaRepository, CategoryMapper categoryMapper) {
        this.iCategoryJpaRepository = iCategoryJpaRepository;
        this.categoryMapper = categoryMapper;
    }


    @Override//  aqui resibe un category
    public Category save(Category category) {          //       cambiamos a un categoryentity por quejpaesta trabajando con un CategoryEntity
        return categoryMapper.toCategory(iCategoryJpaRepository.save(categoryMapper.toCategoryEntity(category)));
        //aqui retorna un category

    }

    @Override
    public Iterable<Category> findAll() {
        return categoryMapper.toCategoryList(iCategoryJpaRepository.findAll());
    }

    @Override
    public Category findById(Integer id) {
        return categoryMapper.toCategory(iCategoryJpaRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Categoria con id: "+id+" no existe")
        ));
    }

    @Override
    public void deleteById(Integer id) {
        iCategoryJpaRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Categoria con id: "+id+" no existe")
        );

    }

    @Override
    public Category updateCategory(Integer id, Category updatedCategory) {
        CategoryEntity existingEntity = iCategoryJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria con id: " + id + " no existe"));

        // Actualizar los campos necesarios
        existingEntity.setName(updatedCategory.getName());
        existingEntity.setDescription(updatedCategory.getDescription());

        // Guardar la entidad actualizada
        CategoryEntity updatedEntity = iCategoryJpaRepository.save(existingEntity);
        return categoryMapper.toCategory(updatedEntity);
    }
}
