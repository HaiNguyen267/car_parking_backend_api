package com.example.car_parking_backend_api.dto.request;

import lombok.Data;

@Data
public class AddZoneRequest {
    private String zoneName;
    private double parkingFee;
}
