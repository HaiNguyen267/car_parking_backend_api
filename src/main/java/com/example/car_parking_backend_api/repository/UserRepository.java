package com.example.car_parking_backend_api.repository;

import com.example.car_parking_backend_api.domain.User;
import com.example.car_parking_backend_api.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    @Autowired
    private UserMapper userMapper;


    public Optional<User> findByEmail(String email) {
        return userMapper.findByEmail(email); // this method include joining
    }

    public void save(User user) {
        userMapper.save(user);
    }

    public boolean existsByEmail(String email) {
        return userMapper.existsByEmail(email) != null; // this method include no joining
    }

    public Optional<User> findById(Long userId) {
        return userMapper.findById(userId);
    }

    public List<User> findAll() {
        return userMapper.findAll();
    }
}
