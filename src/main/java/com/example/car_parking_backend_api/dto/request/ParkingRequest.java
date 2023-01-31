package com.example.car_parking_backend_api.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingRequest {

    private String zoneName;
    private Long spotCode;
    @JsonIgnore
    private LocalDateTime startTime;
}
