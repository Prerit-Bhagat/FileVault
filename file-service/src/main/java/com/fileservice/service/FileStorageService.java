package com.fileservice.service;

import com.fileservice.client.MetadataClient;
import com.fileservice.dto.FileMetadataDto;

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

    private final MetadataClient metadataClient;

    // 🔥 Inject MetadataClient
    public FileStorageService(MetadataClient metadataClient) {
        this.metadataClient = metadataClient;
    }

    // Generate unique filename
    private String generateFileName(String originalName) {
        String extension = "";
        int index = originalName.lastIndexOf(".");
        if (index > 0) {
            extension = originalName.substring(index);
        }
        return UUID.randomUUID() + extension;
    }

    // Store file on disk + save metadata
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

            inputStream.transferTo(outputStream);

        } catch (IOException e) {
            throw new RuntimeException("File upload failed", e);
        }

        // 🔥 CREATE METADATA DTO
        FileMetadataDto dto = new FileMetadataDto();
        dto.setFileId(storedName);
        dto.setOriginalName(file.getFilename());
        dto.setStoredName(storedName);
        dto.setSize(file.getSize());
        dto.setContentType(
                file.getContentType()
                        .map(Object::toString)
                        .orElse("application/octet-stream")
        );

        // 🔥 SAVE METADATA (THIS WAS MISSING)
        metadataClient.saveMetadata(dto);

        return storedName;
    }

    // Load file from disk
    public File loadFile(String fileId) {
        File file = new File(UPLOAD_DIR + "/" + fileId);
        if (!file.exists()) {
            throw new RuntimeException("File not found");
        }
        return file;
    }
    public void deleteFile(String fileId) {
        File file = new File(UPLOAD_DIR + "/" + fileId);
        if (!file.exists()) {
            throw new RuntimeException("File not found");
        }
        if (!file.delete()) {
            throw new RuntimeException("Failed to delete file");
        }
    }
}
