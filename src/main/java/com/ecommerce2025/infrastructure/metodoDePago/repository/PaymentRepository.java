package com.ecommerce2025.infrastructure.metodoDePago.repository;

import com.ecommerce2025.infrastructure.metodoDePago.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para realizar operaciones CRUD en la tabla "payments" de la base de datos.
 * Extiende JpaRepository, lo que proporciona métodos como save(), findById(), delete(), etc.
 */
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    /**
     * El repositorio hereda automáticamente métodos de JpaRepository, tales como:
     *
     * - save(entity): Guarda una entidad.
     * - findById(id): Obtiene una entidad por su ID.
     * - findAll(): Obtiene todas las entidades de la tabla.
     * - deleteById(id): Elimina una entidad por su ID.
     *
     * Si necesitas realizar consultas más complejas, puedes añadir métodos personalizados aquí.
     */

}
