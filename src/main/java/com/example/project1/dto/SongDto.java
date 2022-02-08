package com.example.project1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SongDto {

    private Long id;

    private String name;

    private String fileKey;

    private String path;

    private long userId;



}
