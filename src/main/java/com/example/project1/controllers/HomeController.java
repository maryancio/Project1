package com.example.project1.controllers;

import com.example.project1.dto.UserDto;
import com.example.project1.exceptions.UserNotFoundException;
import com.example.project1.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public String homePage(Model model) {
        model.addAttribute("name", userService.getCurrentUser().getFirstName());
        // Тут помилка падає
        try {

            UserDto currentUser = userService.getCurrentUser();
            if (currentUser.getRole().equals("ROLE_ADMIN")) {
                model.addAttribute("allow", true);
            } else {
                model.addAttribute("allow", false);
            }
        } catch (UserNotFoundException e) {
            model.addAttribute("allow", false);
        }
        return "/home";
    }
}