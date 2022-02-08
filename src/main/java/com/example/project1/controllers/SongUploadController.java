package com.example.project1.controllers;

import com.example.project1.exceptions.OutOfStorageException;
import com.example.project1.service.StorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class SongUploadController {


    private final StorageService storageService;

    @Autowired
    public SongUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/uploadForm")
    public String listUploadedFiles(Model model) {
        return "uploadForm";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<UrlResource> serveFile(@PathVariable String filename) {
        UrlResource file = storageService.uploadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/uploadForm")
    @ResponseBody
    public Long handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        return storageService.store(file);

    }

    @ExceptionHandler(OutOfStorageException.class)
    public ResponseEntity<?> handleStorageFileNotFound(OutOfStorageException outOfStorageException) {
        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/file/{id}")
    @ResponseBody
   public byte[] getFileByName(@PathVariable Long id) throws IOException {
        return storageService.getByteArrayByFileId(id);
    }
}
