package com.example.project1.service;

import com.example.project1.dto.SongDto;
import com.example.project1.entity.Song;
import com.example.project1.exceptions.FileNotFoundException;
import com.example.project1.exceptions.OutOfStorageException;

import com.example.project1.properties.StorageProperties;
import com.example.project1.repository.SongRepository;

import org.apache.commons.io.FileUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StorageServiceImpl implements StorageService {

    private final UserService userService;

    private final Path rootLocation;

    private final SongRepository songRepository;

    @Autowired
    public StorageServiceImpl(UserService userService, StorageProperties properties, SongRepository songRepository) {
        this.userService = userService;
        this.rootLocation = Paths.get(properties.getLocation());
        this.songRepository = songRepository;
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new OutOfStorageException("Could not initialize storage", e);
        }
    }

    @Override
    public Long store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new OutOfStorageException("Cant save an empty file");
            }
            Path destinationFile = this.rootLocation.resolve(
                            Paths.get(file.getOriginalFilename()))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new OutOfStorageException(
                        "Impossible to save file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream,
                        destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
                Song song = new Song();
                song.setName(file.getOriginalFilename());
                song.setPath("upload");
                song.setFileKey(file.getOriginalFilename() + new Date().getTime());
                song.setUserId(userService.getCurrentUser().getId());
                song = songRepository.save(song);
                return song.getId();
            }
        } catch (IOException e) {
            throw new OutOfStorageException("Failed to save file.", e);
        }
    }

    @Override
    public List<SongDto> getSongList(Long id) {
        return songRepository.findAllByUserId(id).stream().map(song -> {
            SongDto songDto = new SongDto();
            songDto.setPath("/file/" + song.getId());
            songDto.setName(song.getName());
            songDto.setId(song.getId());
            songDto.setFileKey(song.getFileKey());
            songDto.setUserId(song.getUserId());
            return songDto;
        }).collect(Collectors.toList());
    }

    @Override
    public Stream<Path> uploadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        } catch (IOException e) {
            throw new OutOfStorageException("Failed to read saved files", e);
        }
    }

    @Override
    public Path upload(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public UrlResource uploadAsResource(String filename) {
        try {
            Path file = upload(filename);
            UrlResource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new FileNotFoundException(
                        "Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new FileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public byte[] getByteArrayByFileId(Long fileId) throws IOException {
        Optional<Song> songOptional = songRepository.findById(fileId);
        if (songOptional.isPresent()) {
            Song song = songOptional.get();
            Path destinationFile = this.rootLocation.resolve(
                            Paths.get(song.getName()))
                    .normalize().toAbsolutePath();
            byte[] fileContent = FileUtils.readFileToByteArray(destinationFile.toFile());
            return fileContent;
        }
        return null;
    }

    @Override
    public String getBase64SongById(Long id) throws IOException {
        Song song = songRepository.getById(id);
        File saved = new File(song.getPath());
        byte[] songContent = FileUtils.readFileToByteArray(saved);
        return String.valueOf(songContent);

    }
}
