package com.ecommerce2025.infrastructure.dto.entity;

import com.ecommerce2025.domain.model.OrderState;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotNull(message = "El estado de la orden no puede ser nulo") // Validamos que el estado de la orden no sea nulo
    private OrderState orderState;

    /**
     * Usuario al que pertenece esta orden.
     * Muchas órdenes pueden estar asociadas a un solo usuario.
     */
    @ManyToOne
    @NotNull(message = "La orden debe estar asociada a un usuario") // Validamos que la orden esté asociada a un usuario
    private UserEntity userEntity;

    /**
     * Lista de productos asociados a esta orden.
     * Una orden puede contener múltiples productos.
     * La relación es bidireccional y se gestiona desde OrderProductEntity.
     */
    @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.PERSIST)
    @Size(min = 1, message = "La orden debe contener al menos un producto") // Validamos que haya al menos un producto en la orden
    private List<OrderProductEntity> orderProducts;
}
