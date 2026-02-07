package com.metadataservice;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;

@Controller("/metadata")
public class MetadataController {
    @Post("/add")
    public String addMetadata() {
        // Logic to add metadata
        return "Metadata added successfully!";
    }

    @Get("/get/{fileId}")
    public String getMetadata(String fileId) {
        // Logic to retrieve metadata by file ID
        return "Metadata retrieved successfully for file ID: " + fileId;
    }
}