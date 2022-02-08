package com.example.project1.dto;

import com.example.project1.enums.Roles;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class UserDto {

    private Long id;
    private Integer age;
    private String email;
    private Set<Roles> role;
    private String password;
    private String lastName;
    private String firstName;

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", password='" + password + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                '}';
    }
}
