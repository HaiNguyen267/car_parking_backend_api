package com.example.car_parking_backend_api.dto.response;

import lombok.Data;

@Data
public class ErrorResponse {
    private int status;
    private String message;
}
