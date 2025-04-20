package com.ecommerce2025.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductoDTO {
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
