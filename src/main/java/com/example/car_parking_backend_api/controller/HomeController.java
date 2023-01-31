package com.example.car_parking_backend_api.controller;

import com.example.car_parking_backend_api.dto.request.DateTimeInfo;
import com.example.car_parking_backend_api.dto.request.LoginRequest;
import com.example.car_parking_backend_api.dto.response.*;
import com.example.car_parking_backend_api.service.AuthService;
import com.example.car_parking_backend_api.service.ParkingLotService;
import com.example.car_parking_backend_api.service.ZoneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class HomeController {

    private final ParkingLotService parkingLotService;
    private final AuthService authService;
    private final ZoneService zoneService;

    @GetMapping("/emptySpots")
    public ResponseEntity<?> emptySpots() {
        return zoneService.getAllEmptySpots();
    }


    @GetMapping("/emptySpots/{zoneType}")
    public ResponseEntity<?> emptySpotsByType(@PathVariable String zoneType) {
        return parkingLotService.getEmptySpotsByType(zoneType);
    }


    @GetMapping("/pricingDetails")
    public ResponseEntity<?> getPricingDetails() {
        return zoneService.getPricingDetails();
    }



}
