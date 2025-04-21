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

    /**
     * Sube una imagen a Cloudinary y retorna la URL segura generada.
     * Validaque el archivo no esté vacío y sea una imagen.
     *
     * @param file Imagen enviada desde el cliente en formato multipart/form-data.
     * @return URL segura (HTTPS) donde está alojada la imagen.
     * @throws IOException Si el archivo no es válido o ocurre un error en la subida.
     */
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

        // Retorna la URL segura (HTTPS) del archivo subido
        return result.get("secure_url").toString();
    }
}
