package com.ecommerce2025.infrastructure.adapter;

import com.ecommerce2025.infrastructure.dto.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryJpaRepository extends JpaRepository<CategoryEntity, Integer> {
}
