package com.example.car_parking_backend_api.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Spot {
    private Long id;
    private String zoneName;
    private Long code;
    private Long userId;
    private String startParkingTime; //TODO: this should be String
    private String status;

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
