package com.ecommerce2025.infrastructure.imageCloudinary.controller;

import com.ecommerce2025.domain.model.Product;
import com.ecommerce2025.domain.port.IProductRepository;
import com.ecommerce2025.infrastructure.imageCloudinary.service.CloudinaryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * Controlador REST para manejar operaciones relacionadas con la subida de imágenes.
 * Utiliza el servicio de Cloudinary para almacenar imágenes en la nube.
 */
@RestController
@RequestMapping("/api/images")
public class ImageController {


    private CloudinaryService cloudinaryService;

    private IProductRepository iProductRepository;


    public ImageController(CloudinaryService cloudinaryService, IProductRepository iProductRepository) {
        this.cloudinaryService = cloudinaryService;
        this.iProductRepository = iProductRepository;
    }

    /**
     * Sube una imagen a Cloudinary y guarda solo el public_id en la base de datos.
     */
    @Operation(summary = "Sube una imagen a Cloudinary")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imagen subida exitosamente"),
            @ApiResponse(responseCode = "400", description = "Archivo inválido"),
            @ApiResponse(responseCode = "500", description = "Error interno al subir imagen")
    })
    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            Map<String, String> result = cloudinaryService.uploadImage(file);
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al subir la imagen: " + e.getMessage());
        }
    }

    /**
     * Elimina una imagen de Cloudinary usando el public_id.
     */
    @Operation(summary = "Elimina una imagen de Cloudinary")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imagen eliminada exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error al eliminar la imagen")
    })
    @DeleteMapping("/delete/{publicId}")
    public ResponseEntity<?> deleteImage(@PathVariable String publicId) {
        try {
            cloudinaryService.deleteImage(publicId);
            return ResponseEntity.ok("Imagen eliminada exitosamente.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar la imagen: " + e.getMessage());
        }
    }

    /**
     * Reemplaza la imagen en Cloudinary y actualiza el public_id del producto en la base de datos.
     */
    @Operation(summary = "Reemplaza una imagen existente en Cloudinary")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imagen reemplazada exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error al reemplazar la imagen")
    })
    @PutMapping("/replace/{publicId}")
    public ResponseEntity<?> replaceImage(
            @PathVariable String publicId,
            @RequestParam("file") MultipartFile file) {
        try {
            // Llamamos al servicio para reemplazar la imagen en Cloudinary
            Map<String, String> result = cloudinaryService.replaceImage(publicId, file);

            // Extraemos la nueva URL y el public_id de la respuesta
            String newUrl = result.get("urlImage");
            String newPublicId = result.get("imagePublicId");

            // Ahora actualizamos la base de datos
            // Buscamos el producto por su publicId actual (deberías tenerlo en tu base de datos)
            Optional<Product> optionalProducto = iProductRepository.findByImagePublicId(publicId);

            if (optionalProducto.isPresent()) {
                Product product = optionalProducto.get();
                // Actualizamos los valores de la imagen
                product.setUrlImage(newUrl);
                product.setImagePublicId(newPublicId);

                // Guardamos los cambios en la base de datos
                iProductRepository.save(product);

                return ResponseEntity.ok("Imagen reemplazada y datos actualizados correctamente.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Producto con la imagen especificada no encontrado.");
            }

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al reemplazar la imagen: " + e.getMessage());
        }
    }

    /**
     * Genera la URL de la imagen a partir del public_id.
     */
    @Operation(summary = "Obtiene la URL de la imagen desde Cloudinary")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "URL de la imagen obtenida exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error al obtener la URL de la imagen")
    })
    @GetMapping("/url/{publicId}")
    public ResponseEntity<?> getImageUrl(@PathVariable String publicId) {
        try {
            String imageUrl = cloudinaryService.getImageUrl(publicId);
            return ResponseEntity.ok(Collections.singletonMap("imageUrl", imageUrl));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener la URL de la imagen: " + e.getMessage());
        }
    }
}
