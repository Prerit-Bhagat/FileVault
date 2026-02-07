package com.fileservice.service;

import io.micronaut.http.MediaType;
import io.micronaut.http.multipart.CompletedFileUpload;
import jakarta.inject.Singleton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Singleton
public class FileStorageService {

    private static final String UPLOAD_DIR = "uploads";

    // Generate unique filename
    private String generateFileName(String originalName) {
        String extension = "";
        int index = originalName.lastIndexOf(".");
        if (index > 0) {
            extension = originalName.substring(index);
        }
        return UUID.randomUUID() + extension;
    }

    // Store file on disk
    public String storeFile(CompletedFileUpload file) {

        if (file.getFilename() == null || file.getFilename().isEmpty()) {
            throw new RuntimeException("Invalid file");
        }

        if (file.getContentType().isPresent()
                && !file.getContentType().get().equals(MediaType.APPLICATION_PDF_TYPE)) {
            throw new RuntimeException("Only PDF files allowed");
        }

        File dir = new File(UPLOAD_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String storedName = generateFileName(file.getFilename());
        File destination = new File(dir, storedName);

        try (InputStream inputStream = file.getInputStream();
             FileOutputStream outputStream = new FileOutputStream(destination)) {

            inputStream.transferTo(outputStream); // ✅ Java 9+

        } catch (IOException e) {
            throw new RuntimeException("File upload failed", e);
        }

        return storedName; // return stored file name or ID
    }
}
