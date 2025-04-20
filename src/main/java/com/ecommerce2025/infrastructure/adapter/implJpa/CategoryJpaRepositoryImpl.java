package com.ecommerce2025.infrastructure.adapter.implJpa;

import com.ecommerce2025.domain.model.Category;
import com.ecommerce2025.domain.port.ICategoryRepository;
import com.ecommerce2025.infrastructure.adapter.jpa.ICategoryJpaRepository;
import com.ecommerce2025.infrastructure.mapper.CategoryMapper;

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
}
