package com.example.car_parking_backend_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuccessResponse {
    private int status;
    private String message;
    private Object data;

    public SuccessResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.data = null;
    }
}
