package com.example.car_parking_backend_api.service;

import com.example.car_parking_backend_api.dto.request.DateTimeInfo;
import com.example.car_parking_backend_api.dto.response.ParkingLotWithEmptySpotsResponse;
import com.example.car_parking_backend_api.dto.response.ZoneWithEmptySpotsResponse;
import com.example.car_parking_backend_api.model.Spot;
import com.example.car_parking_backend_api.model.Zone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ParkingLotService {

    @Autowired
    private SpotService spotService;

    @Autowired
    private ZoneService zoneService;

    public ResponseEntity<?> getEmptySpots() {
        //TODO: return list;
        List<String> zoneNames = zoneService.getAllZoneNames();
        ParkingLotWithEmptySpotsResponse parkingLotInfo = new ParkingLotWithEmptySpotsResponse();

        for (String zoneName : zoneNames) {
            List<Spot> emptySpots = spotService.getEmptySpotsByZoneName(zoneName);
            parkingLotInfo.addEmptySpotsByZone(zoneName, emptySpots);
        }

        return ResponseEntity.ok(parkingLotInfo);
    }

    public ResponseEntity<?> getPricingTo(DateTimeInfo dateTimeInfo) {
        return null;
    }

    public ResponseEntity<?> getEmptySpotsByType(String zoneName) {
        List<Spot> emptySpots = spotService.getEmptySpotsByZoneName(zoneName);
        ZoneWithEmptySpotsResponse zoneInfo = new ZoneWithEmptySpotsResponse(emptySpots);

        return ResponseEntity.ok(zoneInfo);
    }


    public ResponseEntity<?> getPricingDetails() {
        return null;
    }
}
