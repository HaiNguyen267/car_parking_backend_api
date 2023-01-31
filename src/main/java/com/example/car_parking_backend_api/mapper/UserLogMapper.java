package com.example.car_parking_backend_api.mapper;

import com.example.car_parking_backend_api.domain.UserLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.Mapping;

import java.util.List;


@Mapper
public interface UserLogMapper {

    @Select("SELECT * FROM user_log WHERE user_id = #{userId}")
    List<UserLog> getUserLogs(Long userId);
}
