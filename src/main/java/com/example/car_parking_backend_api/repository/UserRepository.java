package com.example.car_parking_backend_api.repository;

import com.example.car_parking_backend_api.model.User;
import com.example.car_parking_backend_api.model.Zone;
import com.example.car_parking_backend_api.mapper.UserMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private UserMapper userMapper;

    public User getUserByEmailIgnoreCase(String userEmail) {
        //TODO: implement this method
        return null;
    }


    public List<Zone> getAllZonesUserCanPark(User user) {
        //TODO: implement this method
        return null;
    }

    public Optional<User> findByEmail(String email) {
        return userMapper.findByEmail(email);
    }
}
