package com.ecommerce2025.domain.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Modelo de dominio que representa una categoría de productos.
 * Este objeto se usa en la capa de dominio y no tiene acoplamiento con frameworks de persistencia.
 */
@Data
public class Category {

    /**
     * Identificador único de la categoría.
     */
    private Integer id;

    /**
     * Nombre de la categoría (ej. Electrónica, Hogar, Ropa, etc.).
     */
    private String name;

    /**
     * Descripción opcional de la categoría.
     */
    private String description;

    /**
     * Fecha de creación del registro.
     */
    private LocalDateTime dateCreated;

    /**
     * Fecha de la última modificación.
     */
    private LocalDateTime dateUpdated;
}
