package com.example.project1.service;

import com.example.project1.dto.SearchRequest;
import com.example.project1.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto findUserByEmail(SearchRequest searchRequest);

    UserDto createUser(UserDto userDto);

    List<UserDto> getAll();

    UserDto getById(Long id);

    void deleteUserById(Long id);

    UserDto getCurrentUser();

    List<UserDto> searchUsers(UserFilter userFilter);

    void activateUser(String code);
}
