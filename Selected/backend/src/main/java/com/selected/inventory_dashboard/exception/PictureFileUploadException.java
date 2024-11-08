package com.selected.inventory_dashboard.exception;

public class PictureFileUploadException extends AppServiceException {
    public PictureFileUploadException(String message) {
        super(message);
    }

    static public PictureFileUploadException failedToCreateDirException(final String message) {
      return new PictureFileUploadException(String.format("Failed to create directory exception: %s", message));
    }

    static public PictureFileUploadException failedToSaveFileException(final String message) {
      return new PictureFileUploadException(String.format("Failed to save file exception: %s", message));
    }

    static public PictureFileUploadException failedToDeleteFileException(final String message) {
      return new PictureFileUploadException(String.format("Failed to delete file exception: %s", message));
    }
}
