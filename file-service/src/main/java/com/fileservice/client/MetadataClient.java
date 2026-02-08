package com.fileservice.client;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.*;
import com.fileservice.dto.FileMetadataDto;
import io.micronaut.http.client.annotation.Client;


// @Client("http://localhost:8082")
// public interface MetadataClient {

//     @Post("/metadata")
//     void saveMetadata(@Body FileMetadataDto metadata);
// }


@Client("http://localhost:8082")
public interface MetadataClient {

    @Post("/metadata")
    void saveMetadata(@Body FileMetadataDto metadata);
}
