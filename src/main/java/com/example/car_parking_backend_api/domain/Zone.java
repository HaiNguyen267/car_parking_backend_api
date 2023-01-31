package com.example.car_parking_backend_api.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Zone {
    private Long id;
    private String name;
    private double parkingFee;
}
