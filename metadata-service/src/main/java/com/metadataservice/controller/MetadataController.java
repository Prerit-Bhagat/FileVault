package com.metadataservice.controller;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.HttpResponse;
import java.util.Optional;
import com.metadataservice.entity.FileMetadata;
import com.metadataservice.service.MetadataService;

// @Controller("/metadata")
// public class MetadataController {

//     private final MetadataService metadataService;

//     public MetadataController(MetadataService metadataService) {
//         this.metadataService = metadataService;
//     }

//     @Post("/")
//     public HttpResponse<FileMetadata> saveMetadata(@Body FileMetadata metadata) {
//         System.out.println("Received metadata for fileId: " + metadata.getFileId());
//         FileMetadata saved = metadataService.saveMetadata(metadata);
//         System.out.println("Metadata saved for fileId: " + saved.getFileId());
//         return HttpResponse.created(saved);
//     }

//     @Get("/{fileId}")
//     public HttpResponse<FileMetadata> get(String fileId) {
//         System.err.println("Received request for fileId: " + fileId);
//         Optional<FileMetadata> metadata = metadataService.getMetadata(fileId);
//         System.err.println("Metadata found: " + metadata.isPresent());
//         return metadata
//                 .map(HttpResponse::ok)
//                 .orElse(HttpResponse.notFound());
//     }

// }

@Controller("/metadata")
public class MetadataController {

    private final MetadataService metadataService;

    public MetadataController(MetadataService metadataService) {
        this.metadataService = metadataService;
    }

    // POST /metadata
    @Post
    public HttpResponse<FileMetadata> saveMetadata(@Body FileMetadata metadata) {
        FileMetadata saved = metadataService.saveMetadata(metadata);
        return HttpResponse.created(saved);
    }

    // GET /metadata/id/{fileId}
    @Get("/id/{fileId}")
    public HttpResponse<FileMetadata> getMetadata(String fileId) {
        System.out.println("Received request for fileId: " + fileId);
        return metadataService.getMetadata(fileId)
                .map(m -> {
                    System.out.println("Metadata found: true");
                    return HttpResponse.ok(m);
                })
                .orElseGet(() -> {
                    System.out.println("Metadata found: false");
                    return HttpResponse.notFound();
                });
    }

    // GET /metadata/count
    @Get("/count")
    public long count() {
        System.out.println("Received request to count files");
        return metadataService.countFiles();
    }
}
