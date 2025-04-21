package com.ecommerce2025.infrastructure.imageCloudinary.config;

// Importaciones necesarias para configurar Cloudinary y Spring
import com.cloudinary.Cloudinary; // Clase principal de la librería Cloudinary
import com.cloudinary.utils.ObjectUtils; // Utilidad para crear mapas de configuración
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value; // Para inyectar valores desde el archivo application.properties
import org.springframework.context.annotation.Bean; // Para indicar que un método produce un bean manejado por Spring
import org.springframework.context.annotation.Configuration; // Para marcar esta clase como clase de configuración

// Anotación que indica que esta clase se utiliza para configurar beans en Spring
@Configuration
public class CloudinaryConfig {

    // Inyecta el valor de la propiedad cloudinary.cloud_name desde application.properties
    @Value("${cloudinary.cloud_name}")
    private String cloudName;

    // Inyecta el valor de la propiedad cloudinary.api_key desde application.properties
    @Value("${cloudinary.api_key}")
    private String apiKey;

    // Inyecta el valor de la propiedad cloudinary.api_secret desde application.properties
    @Value("${cloudinary.api_secret}")
    private String apiSecret;

    // Define un bean de tipo Cloudinary que estará disponible para ser inyectado en otras partes del proyecto
    @Bean
    public Cloudinary cloudinary() {
        // Retorna una instancia de Cloudinary configurada con los valores necesarios
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,  // Nombre de la cuenta Cloudinary
                "api_key", apiKey,        // API Key de autenticación
                "api_secret", apiSecret,  // API Secret de autenticación
                "secure", true            // Indica que se use HTTPS para lasURLs
        ));
    }
}
