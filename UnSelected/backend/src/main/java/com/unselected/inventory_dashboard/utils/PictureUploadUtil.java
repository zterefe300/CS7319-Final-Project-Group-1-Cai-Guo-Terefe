package com.unselected.inventory_dashboard.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

public class PictureUploadUtil {
    @Value("${pictureFile.local_upload_dir}")
    private static String uploadDirectory;

    public static String uploadPicture(final MultipartFile file, final String customFileName) {

        final Path path = Path.of(uploadDirectory);
        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        final String fileName = (customFileName == null || customFileName.isEmpty()) ?
                file.getOriginalFilename() : customFileName;

        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("File name cannot be null or empty");
        }

        final Path filePath = path.resolve(fileName);

        try {
            Files.copy(file.getInputStream(), filePath);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        return filePath.toString();
    }

    public static String uploadPicture(final String imageBase64, final  String customFileName) {
        final String base64ImageWithOutPrefix = imageBase64.startsWith("data:image") ?
                imageBase64.substring(imageBase64.indexOf(",") + 1) : imageBase64;

        final Path path = Path.of(uploadDirectory);
        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        final String fileName = customFileName == null || customFileName.isEmpty() ? String.format("default-image-name-%s.%s",
                System.currentTimeMillis(), "jpg") : customFileName;

        try {
            byte[] decBytes = Base64.getDecoder().decode(base64ImageWithOutPrefix);
            File outputeFile = new File(uploadDirectory + fileName);
            try(FileOutputStream fos = new FileOutputStream(outputeFile)) {
                fos.write(decBytes);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return fileName;
    }

    public static String createItemPictureName(final String itemName,final Integer vendorId) {
        return  String.format("%s-%s-%s.%s", itemName, vendorId, System.currentTimeMillis(), "jpg");
    }
}
