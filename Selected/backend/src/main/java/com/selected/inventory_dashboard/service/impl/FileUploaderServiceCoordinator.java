package com.selected.inventory_dashboard.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploaderServiceCoordinator {
    private final LocalPictureFileUploadServiceImpl localPictureFileUploadService;

    @Value("${pictureFile.storage.type}")
    private String storageType;
    @Value("${pictureFile.local_upload_base_url}")
    private String localUploadBaseUrl;

    public FileUploaderServiceCoordinator(LocalPictureFileUploadServiceImpl localPictureFileUploadService) {
        this.localPictureFileUploadService = localPictureFileUploadService;
    }

    //Returns the url for of the picture
    public String uploadPicture(final MultipartFile file, final String fileName) {
        if (storageType.equalsIgnoreCase("local")) {
            return (localUploadBaseUrl + localPictureFileUploadService.savePictureFile(file, fileName));
        } else {
            throw new RuntimeException("Unsupported storage type: " + storageType);
        }
    }
}
