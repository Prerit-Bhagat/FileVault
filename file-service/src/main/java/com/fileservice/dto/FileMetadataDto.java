package com.fileservice.dto;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Serdeable
public class FileMetadataDto {

    @NotBlank
    private String fileId;

    @NotBlank
    private String originalName;

    @NotBlank
    private String storedName;

    @NotNull
    private Long size;

    @NotBlank
    private String contentType;

    // ✅ No-args constructor (required for serialization)
    public FileMetadataDto() {
    }

    // ✅ All-args constructor (convenience)
    public FileMetadataDto(String fileId,
                           String originalName,
                           String storedName,
                           Long size,
                           String contentType) {
        System.out.println("Creating FileMetadataDto: " + fileId + ", " + originalName + ", " + storedName + ", " + size + ", " + contentType);
        this.fileId = fileId;
        this.originalName = originalName;
        this.storedName = storedName;
        this.size = size;
        this.contentType = contentType;
    }

    // Getters & Setters
    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getStoredName() {
        return storedName;
    }

    public void setStoredName(String storedName) {
        this.storedName = storedName;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    // ✅ Optional but recommended (debugging & logs)
    @Override
    public String toString() {
        return "FileMetadataDto{" +
                "fileId='" + fileId + '\'' +
                ", originalName='" + originalName + '\'' +
                ", storedName='" + storedName + '\'' +
                ", size=" + size +
                ", contentType='" + contentType + '\'' +
                '}';
    }
}
