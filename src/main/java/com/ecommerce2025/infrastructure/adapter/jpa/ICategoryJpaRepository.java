package com.ecommerce2025.infrastructure.adapter.jpa;

import com.ecommerce2025.infrastructure.dto.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryJpaRepository extends JpaRepository<CategoryEntity, Integer> {
}
