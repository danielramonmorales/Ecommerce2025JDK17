package com.ecommerce2025.infrastructure.metodoDePago.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 * Entidad que representa un pago realizado con PayPal.
 * Esta clase se mapea a la tabla "payments" en la base de datos.
 */
@Data
@Entity
@Table(name = "payments")
public class PaymentEntity {

    /**
     * ID único de la entidad de pago.
     * Este campo se autogenera cuando se guarda en la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * ID del pago en PayPal.
     */
    private String paymentId;

    /**
     * ID del pagador (Payer ID) en PayPal.
     */
    private String payerId;

    /**
     * Estado del pago (e.g., "approved", "pending").
     */
    private String state;

    /**
     * Moneda en la que se realizó el pago (e.g., "USD").
     */
    private String currency;

    /**
     * Monto total del pago.
     */
    private String total;

    /**
     * Fecha y hora en la que se registró el pago en el sistema.
     * Este campo se inicializa automáticamente con la fecha y hora actuales.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

}
