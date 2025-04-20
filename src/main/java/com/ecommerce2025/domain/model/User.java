package com.ecommerce2025.domain.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Modelo de dominio que representa un usuario del sistema.
 */
@Data
public class User {

    /**
     * Identificador único del usuario.
     */
    private Integer id;

    /**
     * Nombre de usuario único (usado para login u otras operaciones).
     */
    private String username;

    /**
     * Nombre del usuario.
     */
    private String firstName;

    /**
     * Apellido del usuario.
     */
    private String lastName;

    /**
     * Correo electrónico del usuario.
     */
    private String email;

    /**
     * Dirección física o domicilio.
     */
    private String address;

    /**
     * Número de celular del usuario.
     */
    private String cellphone;

    /**
     * Contraseña (idealmente debería estar encriptada).
     */
    private String password;

    /**
     * Tipo de usuario (ej. ADMIN, CLIENT, etc.).
     */
    private UserType userType;

    /**
     * Fecha de creación del usuario.
     */
    private LocalDateTime dateCreated;

    /**
     * Fecha de la última actualización del usuario.
     */
    private LocalDateTime dateUpdated;
}
