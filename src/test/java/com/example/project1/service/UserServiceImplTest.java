package com.example.project1.service;

import com.example.project1.dto.UserDto;
import com.example.project1.entity.User;
import com.example.project1.entity.UserRole;
import com.example.project1.enums.Roles;
import com.example.project1.exceptions.BadRequestException;
import com.example.project1.repository.UserRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;


    @Test
    void createUser_withOk() {
        String email = "mockEmail";
        UserDto userDto = new UserDto();
        userDto.setEmail(email);
        userDto.setAge(20);
        userDto.setPassword("password");
        User user = new User();
        Set<UserRole> userRoleSet = new HashSet<>();
        UserRole userRole =  new UserRole();
        userRole.setRole(Roles.ROLE_USER);
        userRoleSet.add(userRole);
        user.setRoles(userRoleSet);
        user.setEmail(email);
        Mockito.when(userRepository.existsByEmail(email)).thenReturn(false);
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        UserDto result = userService.createUser(userDto);
        assertEquals(result.getEmail(), userDto.getEmail());
    }

    @Test
    void createUser_withErrorEmail() {
        String email = "mockEmail";
        UserDto userDto = new UserDto();
        userDto.setEmail(email);
        userDto.setAge(20);
        userDto.setPassword("password");
        User user = new User();
        Set<UserRole> userRoleSet = new HashSet<>();
        UserRole userRole =  new UserRole();
        userRole.setRole(Roles.ROLE_USER);
        userRoleSet.add(userRole);
        user.setRoles(userRoleSet);
        user.setEmail(email);
        Mockito.when(userRepository.existsByEmail(email)).thenReturn(true);

        Assertions.assertThrows(BadRequestException.class, () -> {
            userService.createUser(userDto);
        });
    }


    @Test
    void createUser_withErrorAge() {
        String email = "mockEmail";
        UserDto userDto = new UserDto();
        userDto.setEmail(email);
        userDto.setAge(15);
        userDto.setPassword("password");
        User user = new User();
        Set<UserRole> userRoleSet = new HashSet<>();
        UserRole userRole =  new UserRole();
        userRole.setRole(Roles.ROLE_USER);
        userRoleSet.add(userRole);
        user.setRoles(userRoleSet);
        user.setEmail(email);
        Mockito.when(userRepository.existsByEmail(email)).thenReturn(false);

        Assertions.assertThrows(BadRequestException.class, () -> {
            userService.createUser(userDto);
        });
    }

}