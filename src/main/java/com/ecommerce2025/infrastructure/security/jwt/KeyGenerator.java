package com.ecommerce2025.infrastructure.security.jwt;
import java.security.SecureRandom;
import java.util.Base64;

public class KeyGenerator {
    public static void main(String[] args) {
        // Crea una instancia de SecureRandom para generar una clave aleatoria
        SecureRandom random = new SecureRandom();

        // Genera un arreglo de bytes de 256 bits (32 bytes)
        byte[] key = new byte[32];  // 32 bytes * 8 bits = 256 bits
        random.nextBytes(key);

        // Codifica la clave en Base64
        String base64Key = Base64.getEncoder().encodeToString(key);

        // Imprime la clave generada en Base64
        System.out.println(base64Key);  // Este es el valor que debes usar como tu nueva clave secreta
    }
}

