package com.metadataservice.entity;

import jakarta.persistence.*;
import java.time.Instant;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
@Entity
@Table(name = "file_metadata")
public class FileMetadata {

    @Id
    private String fileId;

    private String originalName;
    private String storedName;
    private Long size;
    private String contentType;
    private Instant uploadedAt;

    @PrePersist
    public void onCreate() {
        uploadedAt = Instant.now();
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

    public Instant getUploadedAt() {
        return uploadedAt;
    }
}
