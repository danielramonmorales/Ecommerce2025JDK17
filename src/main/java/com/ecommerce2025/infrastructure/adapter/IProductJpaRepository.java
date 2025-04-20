package com.ecommerce2025.infrastructure.adapter;

import com.ecommerce2025.infrastructure.dto.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IProductJpaRepository extends JpaRepository<ProductEntity, Integer> {

    List<ProductEntity> findByNameContainingIgnoreCase(String query);
}
