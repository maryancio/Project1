package com.example.project1.controllers;

import com.example.project1.dto.SongDto;
import com.example.project1.repository.SongRepository;
import com.example.project1.service.StorageService;

import com.example.project1.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@SuppressWarnings("All")
@RestController
@RequiredArgsConstructor
public class SongController {

    private final StorageService storageService;

    private final SongRepository songRepository;

    private final UserService userService;

    @GetMapping("/currentUserSongs")
    public ResponseEntity<List<SongDto>> currentSongs(){
        return new ResponseEntity<>(storageService.getSongList(userService.getCurrentUser().getId()), HttpStatus.OK);
    }
}
