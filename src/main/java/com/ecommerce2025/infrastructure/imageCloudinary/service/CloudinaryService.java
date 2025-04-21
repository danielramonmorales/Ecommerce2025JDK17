package com.ecommerce2025.infrastructure.imageCloudinary.service;


// Importa la clase principal de Cloudinary para interactuar con elAPI
import com.cloudinary.Cloudinary;
// Utilidad para crear mapas que Cloudinary usa como parámetros
import com.cloudinary.utils.ObjectUtils;

// Anotaciones de Spring para inyección de dependencias y definición de servicios
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Para manejar archivos enviados en solicitudes HTTP
import org.springframework.web.multipart.MultipartFile;

// Librerías estándar para manejar excepciones y mapas
import java.io.IOException;
import java.util.Map;

// Marca esta clase como un componente de servicio, para que Spring la detecte automáticamente
@Service
public class CloudinaryService {

    // Inyección del objeto Cloudinary configurado en la clase CloudinaryConfig
    @Autowired
    private Cloudinary cloudinary;

    // Método público que sube una imagen a Cloudinary y retorna la URL de acceso seguro
    public String uploadImage(MultipartFile file) throws IOException {
        // Sube la imagen usando el método `upload` del uploader de Cloudinary
        // Se pasa el contenido en bytes del archivo y un mapa vacío de opciones
        Map<?, ?> result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

        // Extrae la URL segura del resultado (acceso HTTPS a la imagen)
        return result.get("secure_url").toString();
    }
}
