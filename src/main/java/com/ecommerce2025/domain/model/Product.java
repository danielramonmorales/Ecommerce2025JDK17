package com.ecommerce2025.domain.model;

import jakarta.persistence.Entity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Product {
    private Integer id;
    private String name;
    private String code;
    private String description;
    private String urlImage;

    private BigDecimal price;

    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;

    private  Integer userId;
    private Integer categoryId;
}
