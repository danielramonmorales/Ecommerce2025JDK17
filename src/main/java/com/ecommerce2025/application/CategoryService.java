package com.ecommerce2025.application;

import com.ecommerce2025.domain.model.Category;
import com.ecommerce2025.domain.port.ICategoryRepository;

public class CategoryService {
    private final ICategoryRepository iCategoryRepository;

    public CategoryService(ICategoryRepository iCategoryRepository) {
        this.iCategoryRepository = iCategoryRepository;
    }

    public Category save(Category category){
        return iCategoryRepository.save(category);
    }
    public Iterable<Category> findAll(){
        return iCategoryRepository.findAll();
    }

    public Category findById(Integer id){
        return iCategoryRepository.findById(id);
    }

    public void deleteById(Integer id){
        iCategoryRepository.deleteById(id);
    }

    public Category updateCategory(Integer id, Category category) {
        return iCategoryRepository.updateCategory(id, category);
    }


}
