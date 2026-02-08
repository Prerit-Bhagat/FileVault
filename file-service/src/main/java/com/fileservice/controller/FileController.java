package com.fileservice.controller;

import java.io.File;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.multipart.CompletedFileUpload;
import jakarta.inject.Inject;
import com.fileservice.service.FileStorageService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;

@Controller("/files")
public class FileController {

    @Inject
    private FileStorageService fileStorageService;

    @Post(value = "/upload", consumes = "multipart/form-data")
    public HttpResponse<String> uploadfile(CompletedFileUpload file) {
        String fileId = fileStorageService.storeFile(file);
        return HttpResponse.ok(fileId);
    }

    @Get(value = "/download/{fileName}")
    public HttpResponse<File> download(String fileId) {
        File file = fileStorageService.loadFile(fileId);

        return HttpResponse.ok(file)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
    }
}
