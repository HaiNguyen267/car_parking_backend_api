package com.example.car_parking_backend_api.domain;

import com.example.car_parking_backend_api.utils.TimeUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLog {
    private Long id;
    private Long userId;
    private String event;
    private String createdAt;

    public UserLog(Long userId, String event) {
        this.userId = userId;
        this.event = event;
        this.createdAt = TimeUtils.getCurrentTimeString();
    }

}
