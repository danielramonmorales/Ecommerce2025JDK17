package com.ecommerce2025.infrastructure.imageCloudinary.controller;

// Paquete donde se encuentra esta clase
;

// Importaciones de Spring para controladores, respuestas HTTP, anotaciones, etc.
import com.ecommerce2025.infrastructure.imageCloudinary.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired; // Para inyectar dependencias automáticamente
import org.springframework.http.HttpStatus; // Enum para los códigos de estado HTTP
import org.springframework.http.ResponseEntity; // Representa toda la respuesta HTTP (cuerpo, estado, headers)
import org.springframework.web.bind.annotation.PostMapping; // Mapea solicitudes HTTP POST
import org.springframework.web.bind.annotation.RequestMapping; // Mapea rutas base a nivel de clase
import org.springframework.web.bind.annotation.RequestParam; // Permite obtener parámetros de la solicitud
import org.springframework.web.bind.annotation.RestController; // Marca esta clase como controlador REST
import org.springframework.web.multipart.MultipartFile; // Representa un archivo enviado en una solicitud

// Librerías estándar de Java
import java.io.IOException; // Excepción lanzada en operaciones de entrada/salida
import java.util.Collections; // Utilidad para crear colecciones inmutables

// Anotación que indica que esta clase es un controlador REST (retorna datos, no vistas)
@RestController
// Define la ruta base para todas las peticiones que maneje este controlador
@RequestMapping("/api/images")
public class ImageController {

    // Inyección automática del servicio de Cloudinary para manejar lógica de subida
    @Autowired
    private CloudinaryService cloudinaryService;

    // Mapea una solicitud HTTP POST a la ruta /upload
    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        try {
            // Intenta subir el archivo recibido al servicio de Cloudinary y obtener su URL
            String imageUrl = cloudinaryService.uploadImage(file);

            // Retorna una respuesta 200 OK con un mapa JSON conteniendo la URL de la imagen
            return ResponseEntity.ok(Collections.singletonMap("url", imageUrl));

        } catch (IOException e) {
            // En caso de error al subir la imagen, retorna un estado 500 con un mensaje de error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al subir imagen");
        }
    }
}

