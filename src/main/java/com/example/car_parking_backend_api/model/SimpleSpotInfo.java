package com.example.car_parking_backend_api.model;

import com.example.car_parking_backend_api.model.Spot;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SimpleSpotInfo {
    private String code;
    private boolean isAvailable;

    public SimpleSpotInfo(Spot spot) {
        this.code = spot.getCode();
        this.isAvailable = spot.getUserId() == null;
    }
}