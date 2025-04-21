package com.ecommerce2025.infrastructure.imageCloudinary.controller;

import com.ecommerce2025.infrastructure.imageCloudinary.service.CloudinaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;

/**
 * Controlador REST para manejar operaciones relacionadas con la subida de imágenes.
 * Utiliza el servicio de Cloudinary para almacenar imágenes en la nube.
 */
@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private CloudinaryService cloudinaryService;

    /**
     * Endpoint para subir una imagen a Cloudinary.
     *
     * @param file Imagen en formato multipart enviada desde el cliente.
     * @return URL pública de la imagen si la subida es exitosa, o un mensaje de error.
     */
    @Operation(summary = "Sube una imagen a Cloudinary", description = "Permite subir un archivo de imagen y retorna la URL pública de la imagen almacenada" +
            " en Cloudinary.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imagen subida exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{\"url\": \"https://res.cloudinary.com/...\"}"))),
            @ApiResponse(responseCode = "400", description = "Archivo inválido",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(example = "El archivo está vacío o no es una imagen válida"))),
            @ApiResponse(responseCode = "500", description = "Error interno al subir imagen",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(example = "Error al subir imagen")))
    })
    @PostMapping("/upload")
    public ResponseEntity<?> upload(
            @Parameter(description = "Archivo de imagen que se desea subir", required = true)
            @RequestParam("file") MultipartFile file) {
        try {
            String imageUrl = cloudinaryService.uploadImage(file);
            return ResponseEntity.ok(Collections.singletonMap("url", imageUrl));
        } catch (IOException e) {
            String message = e.getMessage();
            // Si el mensaje de la excepción es de validación, se responde con 400 Bad Request
            if ("El archivo está vacío".equals(message) || "El archivo debe ser una imagen válida".equals(message)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
            }
            // Para otros errores, se responde con 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al subir imagen");
        }
    }
}
