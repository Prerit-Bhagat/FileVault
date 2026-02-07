package com.fileservice.controller;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;

@Controller("/files")
public class FileController {
    @Post(value = "/upload", consumes = "multipart/form-data")
    public String uploadFile() {
        // Logic to handle file upload
        return "File uploaded successfully!";
    }

    @Get(value = "/download/{fileName}")
    public String downloadFile(String fileName) {
        // Logic to handle file download
        return "File downloaded successfully!";
    }
}
