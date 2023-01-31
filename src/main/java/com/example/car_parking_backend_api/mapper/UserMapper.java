package com.example.car_parking_backend_api.mapper;

import com.example.car_parking_backend_api.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {
    Optional<User> findByEmail(String email);

    void save(User user);


    Optional<User> findById(Long userId);

    List<User> findAll();

    @Update("UPDATE user SET first_name = #{firstName}, last_name = #{lastName}, email = #{email}, password = #{password}, role = #{role}, is_locked = #{isLocked} WHERE id = #{id}")
    void update(User user);
}
