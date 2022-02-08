package com.example.project1.service;

import com.example.project1.dto.UserDto;
import com.example.project1.entity.User;
import com.example.project1.exceptions.UserNotFoundException;
import com.example.project1.mappers.UserMapper;
import com.example.project1.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
@Service
public class AbstractService {
    private final UserRepository userRepository;

    public AbstractService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    protected UserDto getAuthUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String login = auth.getName();
        User user = userRepository.findByEmail(login);
        if (Objects.isNull(user)) {
            throw new UserNotFoundException("User not found");
        }
        return UserMapper.toDto(user);
    }
}
