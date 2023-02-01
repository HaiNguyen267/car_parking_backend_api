package com.example.car_parking_backend_api.controller;

import com.example.car_parking_backend_api.service.ZoneService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class HomeController {

    private final ZoneService zoneService;

    @GetMapping("/emptySpots")
    public ResponseEntity<?> emptySpots() {
        return zoneService.getAllEmptySpots();
    }


    @GetMapping("/emptySpots/{zoneType}")
    public ResponseEntity<?> emptySpotsByType(@PathVariable String zoneType) {
        return zoneService.getAllEmptySpotsByName(zoneType);
    }

    @GetMapping("/pricingDetails")
    public ResponseEntity<?> getPricingDetails() {
        return zoneService.getPricingDetails();
    }



}
