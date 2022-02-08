package com.example.project1.configs;


import com.example.project1.entity.User;
import com.example.project1.entity.UserRole;
import com.example.project1.repository.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;


@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(userName);
        return getByUser(user);
    }

    private Principle getByUser(User user) {
        Set<UserRole> roles = user.getRoles();
        return new Principle(user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPassword(),
                roles);
    }


}
