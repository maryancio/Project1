package com.example.project1.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagesController {

    @GetMapping("/songs")
    public String getSongById() {
        return "songs";
    }

}
