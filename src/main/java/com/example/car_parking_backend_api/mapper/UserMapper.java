package com.example.car_parking_backend_api.mapper;

import com.example.car_parking_backend_api.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {
    Optional<User> findByEmail(String email);

    void save(User user);

    Integer existsByEmail(String email);

    Optional<User> findById(Long userId);

    @Select("SELECT * FROM user")
    List<User> findAll();
}
