package com.ecommerce2025.infrastructure.imageCloudinary.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Servicio encargado de manejar la lógica de interacción con Cloudinary.
 * Permite subir imágenes al servicio en la nube y recuperar susURLs.
 */
@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    public Map<String, String> uploadImage(MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

        String urlImage = (String) uploadResult.get("secure_url");
        String publicId = (String) uploadResult.get("public_id");

        Map<String, String> result = new HashMap<>();
        result.put("urlImage", urlImage);            // debe llamarse así
        result.put("imagePublicId", publicId);       // debe llamarse así

        return result;
    }



    // Elimina una imagen de Cloudinary usando el public_id
    public void deleteImage(String publicId) throws IOException {
        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }

    // Reemplaza una imagen en Cloudinary: sube una nueva y elimina la antigua
    public Map<String, String> replaceImage(String oldPublicId, MultipartFile file) throws IOException {
        // Primero eliminamos la imagen anterior si se proporcionó un publicId
        if (oldPublicId != null && !oldPublicId.isBlank()) {
            deleteImage(oldPublicId);
        }

        // Luego subimos la nueva imagen y retornamos la info (url y publicId)
        return uploadImage(file);
    }

    // Genera la URL de la imagen a partir del public_id
    public String getImageUrl(String publicId) {
        return cloudinary.url().generate(publicId);
    }
}