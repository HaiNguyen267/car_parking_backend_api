package com.example.car_parking_backend_api.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SimpleSpotInfo {
    private String code;
    private boolean isAvailable;

    public SimpleSpotInfo(Spot spot) {
        this.code = spot.getCode().toString();
        this.isAvailable = spot.getUserId() == null;
    }
}