package com.metadataservice.controller;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.HttpResponse;
import java.util.Optional;
import com.metadataservice.entity.FileMetadata;
import com.metadataservice.service.MetadataService;

@Controller("/metadata")
public class MetadataController {

    private final MetadataService metadataService;

    public MetadataController(MetadataService metadataService) {
        this.metadataService = metadataService;
    }

    @Post("/")
    public HttpResponse<FileMetadata> saveMetadata(@Body FileMetadata metadata) {
        FileMetadata saved = metadataService.saveMetadata(metadata);
        return HttpResponse.created(saved);
    }

    @Get("/{fileId}")
    public HttpResponse<FileMetadata> get(String fileId) {
        Optional<FileMetadata> metadata = metadataService.getMetadata(fileId);
        return metadata
                .map(HttpResponse::ok)
                .orElse(HttpResponse.notFound());
    }

}