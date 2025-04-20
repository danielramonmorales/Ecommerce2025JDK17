package com.ecommerce2025.domain.model;

/**
 * Enum que representa los distintos tipos de usuarios del sistema.
 */
public enum UserType {

    /**
     * Usuario con privilegios administrativos.
     */
    ADMIN,

    /**
     * Usuario estándar o cliente final.
     */
    USER;

    // Opcional: Si querés mostrar un texto más legible o amigable (ej. en español), podés hacerlo así:
    // private final String displayName;
    //
    // UserType(String displayName) {
    //     this.displayName = displayName;
    // }
    //
    // public String getDisplayName() {
    //     return displayName;
    // }
}
