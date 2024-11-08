package com.selected.inventory_dashboard.service.impl;

import com.selected.inventory_dashboard.exception.PictureFileUploadException;
import com.selected.inventory_dashboard.persistence.file_upload.PictureFileUploader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class LocalPictureFileUploadServiceImpl implements PictureFileUploader {
    private static final Logger logger = LoggerFactory.getLogger(LocalPictureFileUploadServiceImpl.class);

    @Value("${pictureFile.local_upload_dir}")
    private String uploadDirectory;

    ///This method will replace existing picture file when saving
    @Override
    public String savePictureFile(final MultipartFile file, final String customFileName) {

        final Path path = Path.of(uploadDirectory);
        try {
            createDIrIfNotExists(path);
        } catch (IOException e) {
            throw PictureFileUploadException.failedToCreateDirException(e.getMessage());
        }

        final String fileName = (customFileName == null || customFileName.isEmpty()) ?
                file.getOriginalFilename() : customFileName;

        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("File name cannot be null or empty");
        }

        final Path filePath = path.resolve(fileName);

        try {
            Files.copy(file.getInputStream(), filePath);
            logger.debug("File saved successfully: {}", filePath);
        } catch (IOException e) {
            logger.error("Failed to save picture file", e);
            throw PictureFileUploadException.failedToSaveFileException(e.getMessage());
        }

        return filePath.toString();
    }

    @Override
    public boolean deletePictureFile(String fileName) {
        final Path dirPath = Path.of(uploadDirectory);
        final Path filePath = dirPath.resolve(fileName);
        try {
            Files.delete(filePath);
        } catch (IOException e) {
            logger.error("Failed to delete picture file", e);
            throw PictureFileUploadException.failedToDeleteFileException(e.getMessage());
        }

        return Files.exists(filePath);
    }

    private void createDIrIfNotExists(final Path path) throws IOException {
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }
}