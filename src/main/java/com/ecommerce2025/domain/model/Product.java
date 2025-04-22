package com.ecommerce2025.domain.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Modelo de dominio que representa un producto en el sistema.
 * No contiene lógica de infraestructura ni depende de frameworks.
 */
@Data
public class Product {

    /**
     * Identificador único del producto.
     */
    private Integer id;

    /**
     * Nombre del producto.
     */
    private String name;

    /**
     * Código interno o SKU del producto.
     */
    private String code;

    /**
     * Descripción detallada del producto.
     */
    private String description;

    /**
     * URL o ruta de la imagen del producto.
     */
    private String urlImage;

    /**
     * URL o ruta de la imagen del producto.
     */
    private String imagePublicId;

    /**
     * Precio del producto.
     */
    private BigDecimal price;

    /**
     * Fecha de creación del producto.
     */
    private LocalDateTime dateCreated;

    /**
     * Fecha de última actualización del producto.
     */
    private LocalDateTime dateUpdated;

    /**
     * IDdel usuario que creó o administra el producto.
     */
    private Integer userId;

    /**
     * IDde la categoría a la que pertenece el producto.
     */
    private Integer categoryId;
}
