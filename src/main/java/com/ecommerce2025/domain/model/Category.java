package com.ecommerce2025.domain.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Category {
    private Integer id;
    private String name;
    private String description;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;

}

