package com.example.car_parking_backend_api.repository;

import com.example.car_parking_backend_api.domain.UserLog;
import com.example.car_parking_backend_api.mapper.UserLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserLogRepository {

    @Autowired
    private UserLogMapper userLogMapper;

    public List<UserLog> getUserLogs(Long userId) {
        return userLogMapper.getUserLogs(userId);
    }

    public void saveUserLog(UserLog userLog) {
        userLogMapper.saveUserLog(userLog);
    }
}
