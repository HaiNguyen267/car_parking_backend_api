package com.example.car_parking_backend_api.dto.request;

import lombok.Data;

@Data
public class ChangeParkingFeeRequest {
    private String zoneName;
    private double parkingFee;
}
