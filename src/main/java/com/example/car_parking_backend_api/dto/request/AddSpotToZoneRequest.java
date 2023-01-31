package com.example.car_parking_backend_api.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AddSpotToZoneRequest {
    private String zoneName;
    private Long spotCode;
}
