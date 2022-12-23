package com.example.car_parking_backend_api.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Spot {
    private int id;
    private Long userId;
    private String code;
    private LocalDateTime startParkingTime;
    private String zoneName;

    @Override
    public String toString() {
        return "Spot{" +
                "id=" + id +
                ", userId=" + userId +
                ", code=" + code +
                ", startParkingTime=" + startParkingTime +
                ", zoneName='" + zoneName + '\'' +
                '}';
    }
}
