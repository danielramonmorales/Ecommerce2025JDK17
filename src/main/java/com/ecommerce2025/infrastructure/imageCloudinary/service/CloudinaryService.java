package com.ecommerce2025.infrastructure.imageCloudinary.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * Servicio encargado de manejar la lógica de interacción con Cloudinary.
 * Permite subir imágenes al servicio en la nube y recuperar susURLs.
 */
@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    // Sube una imagen a Cloudinary y retorna el public_id de la imagen
    public String uploadImage(MultipartFile file) throws IOException {
        // Validación: archivo no puede estar vacío
        if (file.isEmpty()) {
            throw new IOException("El archivo está vacío");
        }

        // Validación: tipo de contenido debe ser imagen
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IOException("El archivo debe ser una imagen válida");
        }

        // Subida a Cloudinary sin opciones adicionales
        Map<?, ?> result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

        // Retorna el public_id de la imagen subida
        return result.get("public_id").toString();
    }

    // Elimina una imagen de Cloudinary usando el public_id
    public void deleteImage(String publicId) throws IOException {
        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }

    // Reemplaza una imagen en Cloudinary (sube una nueva y elimina la antigua)
    public String replaceImage(String publicId, MultipartFile file) throws IOException {
        // Elimina la imagen anterior
        deleteImage(publicId);

        // Sube la nueva imagen y obtiene el nuevo public_id
        return uploadImage(file);
    }

    // Genera la URL de la imagen a partir del public_id
    public String getImageUrl(String publicId) {
        return cloudinary.url().generate(publicId);
    }
}