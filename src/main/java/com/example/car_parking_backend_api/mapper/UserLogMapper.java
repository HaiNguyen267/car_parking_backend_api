package com.example.car_parking_backend_api.mapper;

import com.example.car_parking_backend_api.domain.UserLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.Mapping;

import java.util.List;


@Mapper
public interface UserLogMapper {

    List<UserLog> getUserLogs(Long userId);

    @Insert("INSERT INTO user_log (user_id, event, created_at) VALUES (#{userId}, #{event}, #{createdAt})")
    void saveUserLog(UserLog userLog);
}
