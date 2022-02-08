package com.example.project1.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String fileKey;

    private String path;

    @Column(name = "user_id")
    private long userId;

    public Song() {

    }


    public Song(String base64SongById) {

    }
}

