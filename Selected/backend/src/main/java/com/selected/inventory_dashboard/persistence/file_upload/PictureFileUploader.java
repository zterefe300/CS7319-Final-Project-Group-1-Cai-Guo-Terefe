package com.selected.inventory_dashboard.persistence.file_upload;

import org.springframework.web.multipart.MultipartFile;

public interface PictureFileUploader {
    String savePictureFile(final MultipartFile file, final String customFileName);
    boolean deletePictureFile(final String fileName);
}
