package com.ecommerce2025.model;

import jakarta.persistence.Entity;
import lombok.Data;

import java.time.LocalDateTime;
@Entity
@Data
public class Producto {
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer stock;
    private String categoria;
    private String marca;
    private Boolean disponible;
    private LocalDateTime creadoEn;
}

