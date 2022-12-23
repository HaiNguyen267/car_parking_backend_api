package com.example.car_parking_backend_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingBill {
    private int totalParkingHours;
    private int fee;
}
