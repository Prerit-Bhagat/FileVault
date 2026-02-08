package com.fileservice.controller;

import java.io.File;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

import jakarta.inject.Inject;

import com.fileservice.service.FileStorageService;

@Controller("/files")
public class FileController {

    @Inject
    private FileStorageService fileStorageService;

    // 🔥 MUST run on BLOCKING executor
    @Post(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA)
    @ExecuteOn(TaskExecutors.BLOCKING)
    public HttpResponse<String> uploadfile(CompletedFileUpload file) {
        System.out.println("Received file: " + file.getFilename() + ", size: " + file.getSize());
        String fileId = fileStorageService.storeFile(file);
        return HttpResponse.ok(fileId);
    }

    // 🔥 File IO → also BLOCKING
    @Get(value = "/download/{fileId}")
    @ExecuteOn(TaskExecutors.BLOCKING)
    public HttpResponse<File> download(String fileId) {
        System.out.println("Downloading file with ID: " + fileId);
        File file = fileStorageService.loadFile(fileId);

        return HttpResponse.ok(file)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(
                    "Content-Disposition",
                    "attachment; filename=\"" + file.getName() + "\""
                );
    }
}
