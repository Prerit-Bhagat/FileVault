package com.metadataservice.service;

import jakarta.inject.Singleton;

import java.io.File;
import java.net.http.HttpResponse;
import java.util.Optional;

import com.metadataservice.entity.FileMetadata;
import com.metadataservice.repository.FileMetadataRepository;
// import io.micronaut.http.HttpResponse;÷


@Singleton
public class MetadataService {
    private final FileMetadataRepository fileMetadataRepository;

    public MetadataService(FileMetadataRepository fileMetadataRepository) {
        this.fileMetadataRepository = fileMetadataRepository;
    }

    public FileMetadata saveMetadata(FileMetadata metadata) {
        return fileMetadataRepository.save(metadata);
    }

    public Optional<FileMetadata> getMetadata(String fileId) {
        return fileMetadataRepository.findById(fileId);
    }

    public long countFiles() {
        return fileMetadataRepository.count();
    }
}
