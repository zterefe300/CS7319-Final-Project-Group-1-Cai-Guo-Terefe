package com.unselected.inventory_dashboard.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
}
