package com.example.car_parking_backend_api.mapper;

import com.example.car_parking_backend_api.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {
    Optional<User> findByEmail(String email);
}
