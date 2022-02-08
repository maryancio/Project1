package com.example.project1.service;

import com.example.project1.dto.SongDto;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    Long store(MultipartFile file);

    Stream<Path> uploadAll();

    Path upload (String filename);

    UrlResource uploadAsResource(String filename);

    void deleteAll();

    byte[] getByteArrayByFileId(Long fileId) throws IOException;

    String getBase64SongById(Long id) throws IOException;


    List<SongDto> getSongList(Long id);

}
