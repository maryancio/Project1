package com.example.project1.repository;

import com.example.project1.dto.UserDto;
import com.example.project1.entity.User;
import com.example.project1.mappers.UserMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
@Repository
public class UserDao {

    private final EntityManager entityManager;

    public UserDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void saveUser(UserDto userDto) {
        User user = UserMapper.toEntity(userDto);
        entityManager.persist(user);
    }

    public UserDto getUserById(Long id) {
        User user = entityManager.find(User.class, id);
        return UserMapper.toDto(user);
    }
}
