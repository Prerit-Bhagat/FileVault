package com.metadataservice.service;

import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import com.metadataservice.entity.FileMetadata;
import com.metadataservice.repository.FileMetadataRepository;

@Singleton
public class MetadataService {

    private static final Logger LOG =
            LoggerFactory.getLogger(MetadataService.class);

    private final FileMetadataRepository repository;

    public MetadataService(FileMetadataRepository repository) {
        this.repository = repository;
    }

    public FileMetadata saveMetadata(FileMetadata metadata) {
        LOG.info("Saving metadata for fileId={}", metadata.getFileId());
        return repository.save(metadata);
    }

    public Optional<FileMetadata> getByFileId(String fileId) {
        LOG.info("Fetching metadata for fileId={}", fileId);
        return repository.findById(fileId);
    }

    public void deleteByFileId(String fileId) {
        repository.deleteById(fileId);
    }

}
