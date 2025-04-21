package com.ecommerce2025.infrastructure.imageCloudinary.config;

// Importaciones necesarias para la configuración de Cloudinary y para el uso de anotaciones de Spring
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Clase de configuración de Cloudinary.
 * Se encarga de definir el bean necesario para interactuar con el servicio de Cloudinary
 * utilizando las credenciales definidas en el archivo application.properties o application.yml.
 */
@Configuration
public class CloudinaryConfig {

    /**
     * Nombre de la cuenta de Cloudinary, inyectado desde las propiedades del entorno.
     */
    @Value("${cloudinary.cloud_name}")
    private String cloudName;

    /**
     * Clave de API de Cloudinary, inyectada desde las propiedades del entorno.
     */
    @Value("${cloudinary.api_key}")
    private String apiKey;

    /**
     * Secreto de API de Cloudinary, inyectado desde las propiedades del entorno.
     */
    @Value("${cloudinary.api_secret}")
    private String apiSecret;

    /**
     * Método que define un bean de tipo Cloudinary.
     * Este bean estará disponible para ser inyectado en cualquier componente que requiera acceso
     * a los servicios de Cloudinary.
     *
     * @return una instancia de Cloudinary configurada con las credenciales y opciones necesarias.
     */
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,   // Nombre de la cuenta de Cloudinary
                "api_key", apiKey,         // Clave de la API para autenticación
                "api_secret", apiSecret,   // Secreto de la API para autenticación
                "secure", true             // Utilizar HTTPS para todas las URLs generadas
        ));
    }
}
