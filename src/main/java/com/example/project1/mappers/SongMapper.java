package com.example.project1.mappers;

import com.example.project1.dto.SongDto;

import com.example.project1.entity.Song;

public class SongMapper {

    public static Song toEntity(SongDto songDto) {
        Song song = new Song();
        song.setId(songDto.getId());
        song.setName(songDto.getName());
        song.setPath(songDto.getPath());
        song.setFileKey(songDto.getFileKey());
        song.setUserId(songDto.getUserId());
        return song;
    }

    public  static  SongDto toDto (Song song){
        SongDto songDto = new SongDto();
        songDto.setId(song.getId());
        songDto.setName(song.getName());
        songDto.setPath(song.getPath());
        songDto.setFileKey(song.getFileKey());
        songDto.setUserId(song.getUserId());
        return  songDto;
    }
}

