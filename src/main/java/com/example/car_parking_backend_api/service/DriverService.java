package com.example.car_parking_backend_api.service;

import com.example.car_parking_backend_api.dto.request.ParkingRequest;
import com.example.car_parking_backend_api.dto.response.SuccessResponse;
import com.example.car_parking_backend_api.enums.SpotStatus;
import com.example.car_parking_backend_api.exception.ParkingException;
import com.example.car_parking_backend_api.domain.Spot;
import com.example.car_parking_backend_api.domain.User;
import com.example.car_parking_backend_api.domain.Zone;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@AllArgsConstructor
public class DriverService {
    private final SpotService spotService;
    private final ZoneService zoneService;

    public ResponseEntity<?> park(User user, ParkingRequest parkingRequest) {
        if (checkIfUserHasParked(user)) {
            throw new ParkingException("User has already parked a car");
        }

        if (!checkIfSpotAvailable(parkingRequest.getZoneName(), parkingRequest.getSpotCode())) {
            throw new ParkingException("Spot is not available");
        }



        Spot spot = spotService.getSpot(parkingRequest.getZoneName(), parkingRequest.getSpotCode());

        if (!spot.getStatus().equals(SpotStatus.EMPTY.name())) {
            throw new ParkingException("Spot is not available");
        }

        spot.setUserId(user.getId());
        spot.setStartParkingTime(currentLocalTime());
        spot.setStatus(SpotStatus.PARKED.name());

        spotService.saveSpot(spot);
        //TODO: save log for driver and log for spot
        SuccessResponse successResponse = new SuccessResponse(200, "Car parked successfully");
        return ResponseEntity.ok(successResponse);

    }

    private String currentLocalTime() {
        LocalDateTime now = LocalDateTime.now();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    private boolean checkIfSpotAvailable(String zoneName, Long spotCode) {
        Spot spot = spotService.getSpot(zoneName, spotCode);
        return spot.getStatus().equals(SpotStatus.EMPTY.name());
    }

    private boolean checkIfUserHasParked(User user) {
        return spotService.getSpotByUserId(user.getId()).isPresent();
    }


    public ResponseEntity<?> getParkingBill(User user) {
        if (checkIfUserHasParked(user)) {
            throw new ParkingException("User has not parked a car");
        }

        double total = calculateParkingFee(user);

        SuccessResponse successResponse = new SuccessResponse(200, "Retrieve parking bill successfuly", total);
        return ResponseEntity.ok(successResponse);
        
    }

    private double calculateParkingFee(User user) {
        Spot spot = spotService.getParkedSpotByUserId(user.getId());
        double parkingFee = getParkingFeeOfSpot(spot);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startParkingTime = LocalDateTime.parse(spot.getStartParkingTime());

        long minutes = ChronoUnit.MINUTES.between(startParkingTime, now);
        return Math.ceil(minutes / 60.0) * parkingFee;
    }


    private double getParkingFeeOfSpot(Spot spot) {
        Zone zone = zoneService.getZoneByName(spot.getZoneName());
        return zone.getParkingFee();
    }

    public ResponseEntity<?> backOut(User user) {
        if (checkIfUserHasParked(user)) {
            throw new ParkingException("User has not parked a car");
        }

        Spot spot = spotService.getParkedSpotByUserId(user.getId());
        spot.setUserId(null);
        spot.setStartParkingTime(null);
        spot.setStatus(SpotStatus.EMPTY.name());

        spotService.saveSpot(spot);
        return ResponseEntity.ok(new SuccessResponse(200, "Back out car successfully"));
    }
}
