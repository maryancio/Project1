package com.example.project1.dto;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SongsDto {

    private String base64File;



    public SongsDto(String base64File) {
        this.base64File = base64File;
    }

    public SongsDto() {
    }
}
