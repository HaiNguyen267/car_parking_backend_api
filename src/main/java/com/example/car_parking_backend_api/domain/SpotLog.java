package com.example.car_parking_backend_api.domain;

import com.example.car_parking_backend_api.utils.TimeUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpotLog {
    private Long id;
    private Long spotId;
    private Long userId;
    private String event;
    private String createdAt;

    public SpotLog(Long spotId, Long userId, String event) {
        this.spotId = spotId;
        this.userId = userId;
        this.event = event;
        this.createdAt = TimeUtils.getCurrentTimeString();
    }

}
