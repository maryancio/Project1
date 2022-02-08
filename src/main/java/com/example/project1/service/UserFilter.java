package com.example.project1.service;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserFilter {

    private String firstName;

    private String lastName;

    private Integer age;

    private String email;

    private String name;

    private String role;

    public UserFilter() {
    }
}