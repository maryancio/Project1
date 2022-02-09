package com.example.project1.controllers;

import com.example.project1.dto.SearchRequest;
import com.example.project1.dto.UserDto;
import com.example.project1.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/userPage")
    public String userPage(Model model) {
        UserDto currentUser = userService.getCurrentUser();
        model.addAttribute("user", currentUser);
        return "userInfo";
    }

    @PostMapping("/searchUser")
    public String userPageWithUser(@ModelAttribute("searchObject") SearchRequest searchRequest, Model model) {
        UserDto userByEmail = userService.findUserByEmail(searchRequest);
        model.addAttribute("user", userByEmail);
        return "userInfo";
    }

    @GetMapping("/user/{id}")
    public String usersPage(@PathVariable Long id, Model model) {
        UserDto user = userService.getById(id);
        model.addAttribute("user", user);
        return "userInfo";
    }

    @GetMapping("/user")
    public String usersPage1(@RequestParam(name = "id", required = false) Long id,
                             @RequestParam(name = "name", required = false) String name,
                             Model model) {
        UserDto user = userService.getById(id);
        model.addAttribute("user", user);
        return "userInfo";
    }
}

