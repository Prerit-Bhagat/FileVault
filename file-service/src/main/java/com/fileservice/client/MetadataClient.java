package com.fileservice.client;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.ClientFilter;
import io.micronaut.http.annotation.Post;
import com.fileservice.dto.FileMetadataDto;

@ClientFilter("http://localhost:8082")
public interface MetadataClient {

    @Post("/metadata/")
    void saveMetadata(@Body FileMetadataDto metadata);
}