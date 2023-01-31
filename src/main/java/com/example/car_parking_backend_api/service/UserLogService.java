package com.example.car_parking_backend_api.service;

import com.example.car_parking_backend_api.domain.User;
import com.example.car_parking_backend_api.domain.UserLog;
import com.example.car_parking_backend_api.enums.EventLog;
import com.example.car_parking_backend_api.repository.UserLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserLogService {

    @Autowired
    private UserLogRepository userLogRepository;

    public List<UserLog> getUserLogs(Long userId) {
        return userLogRepository.getUserLogs(userId);
    }

    public void saveUserLog(UserLog userLog) {
        userLogRepository.saveUserLog(userLog);
    }
}
