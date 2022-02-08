package com.example.project1.controllers;

import org.springframework.web.bind.annotation.GetMapping;

public class AdminController {

    @GetMapping("/adminPage")
    public String goToAdminPage() {
        return "adminPage";
    }

}
