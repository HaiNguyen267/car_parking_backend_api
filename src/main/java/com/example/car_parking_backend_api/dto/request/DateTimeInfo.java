package com.example.car_parking_backend_api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public class DateTimeInfo {
    @Schema(description = "Start date and time of parking", example = "2021-05-01T12:00:00")
    private String date;
    private String day;
}
