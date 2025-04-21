package com.ecommerce2025.infrastructure.dto.entity;

import com.ecommerce2025.domain.model.OrderState;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Entidad JPA que representa la tabla 'orders' en la base de datos.
 * Esta entidad mapea las órdenes realizadas por los usuarios del sistema.
 */
@Data
@Entity
@Table(name = "orders")
public class OrderEntity {

    /** Identificador único de la orden (clave primaria, autogenerada) */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** Fecha de creación de la orden (se genera automáticamente al persistir) */
    @CreationTimestamp
    private LocalDateTime dateCreated;

    /** Estado actual de la orden (CONFIRMED, CANCELLED, etc.) */
    @Enumerated(value = EnumType.STRING)
    private OrderState orderState;

    /**
     * Usuario al que pertenece esta orden.
     * Muchas órdenes pueden estar asociadas a un solo usuario.
     */
    @ManyToOne
    private UserEntity userEntity;

    /**
     * Lista de productos asociados a esta orden.
     * Una orden puede contener múltiples productos.
     * La relación es bidireccional y se gestiona desde OrderProductEntity.
     */
    @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.PERSIST)
    private List<OrderProductEntity> orderProducts;
}
