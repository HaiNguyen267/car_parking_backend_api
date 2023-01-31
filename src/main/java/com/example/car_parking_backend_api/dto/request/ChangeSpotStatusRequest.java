package com.example.car_parking_backend_api.dto.request;

import lombok.Data;

@Data
public class ChangeSpotStatusRequest {
    private String zoneName;
    private Long spotCode;
}
