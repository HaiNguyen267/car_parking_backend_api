package com.example.car_parking_backend_api.service;

import com.example.car_parking_backend_api.domain.*;
import com.example.car_parking_backend_api.dto.request.ParkingRequest;
import com.example.car_parking_backend_api.dto.response.SuccessResponse;
import com.example.car_parking_backend_api.enums.EventLog;
import com.example.car_parking_backend_api.enums.SpotStatus;
import com.example.car_parking_backend_api.exception.ParkingException;
import com.example.car_parking_backend_api.utils.TimeUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@AllArgsConstructor
public class DriverService {
    private final SpotService spotService;
    private final ZoneService zoneService;
    private final UserLogService userLogService;
    private final SpotLogService spotLogService;

    public ResponseEntity<?> park(User user, ParkingRequest parkingRequest) {
        if (checkIfUserHasParked(user)) {
            throw new ParkingException("You has already parked a car");
        }

        if (!checkIfSpotAvailable(parkingRequest.getZoneName(), parkingRequest.getSpotCode())) {
            throw new ParkingException("Spot is not available");
        }

        Spot spot = spotService.getSpot(parkingRequest.getZoneName(), parkingRequest.getSpotCode());

        if (!spot.getStatus().equals(SpotStatus.EMPTY.name())) {
            throw new ParkingException("Spot is not available");
        }

        spot.setUserId(user.getId());
        spot.setStartParkingTime(TimeUtils.getCurrentTimeString());
        spot.setStatus(SpotStatus.PARKED.name());
        spotService.update(spot);

        // create log for driver
        String userEvent = EventLog.PARK_CAR + " with spot id=" + spot.getId();
        UserLog userLog = new UserLog(user.getId(), userEvent);
        userLogService.saveUserLog(userLog);


        // create log for spot
        String spotEvent = EventLog.PARKED + " by user id=" + user.getId();
        SpotLog spotLog = new SpotLog(spot.getId(), user.getId(), spotEvent);
        spotLogService.saveSpotLog(spotLog);

        SuccessResponse successResponse = new SuccessResponse(200, "Car parked successfully");
        return ResponseEntity.ok(successResponse);

    }



    private boolean checkIfSpotAvailable(String zoneName, Long spotCode) {
        Spot spot = spotService.getSpot(zoneName, spotCode);
        return spot.getStatus().equals(SpotStatus.EMPTY.name());
    }

    private boolean checkIfUserHasParked(User user) {
        return spotService.getSpotByUserId(user.getId()).isPresent();
    }


    public ResponseEntity<?> getParkingBill(User user) {

        double total = calculateParkingFee(user);

        SuccessResponse successResponse = new SuccessResponse(200, "Retrieve parking bill successfuly", total);
        return ResponseEntity.ok(successResponse);
        
    }

    private double calculateParkingFee(User user) {
        Spot spot = spotService.getParkedSpotByUserId(user.getId());
        double parkingFee = getParkingFeeOfSpot(spot);

        LocalDateTime now = LocalDateTime.now();

        LocalDateTime startParkingTime = TimeUtils.convertToLocalDateTime(spot.getStartParkingTime());

        long minutes = ChronoUnit.MINUTES.between(startParkingTime, now);
        return Math.ceil(minutes / 60.0) * parkingFee;
    }


    private double getParkingFeeOfSpot(Spot spot) {
        Zone zone = zoneService.getZoneByName(spot.getZoneName());
        return zone.getParkingFee();
    }

    public ResponseEntity<?> backOut(User user) {
        if (!checkIfUserHasParked(user)) {
            throw new ParkingException("You has not parked a car");
        }

        Spot spot = spotService.getParkedSpotByUserId(user.getId());
        spot.setUserId(null);
        spot.setStartParkingTime(null);
        spot.setStatus(SpotStatus.EMPTY.name());

        spotService.update(spot);

        // create user log
        String userEvent = EventLog.BACK_OUT + " with spot id=" + spot.getId();
        UserLog userLog = new UserLog(user.getId(), userEvent);
        userLogService.saveUserLog(userLog);

        // create spot log
        String spotEvent = EventLog.BACKED_OUT + " by user id=" + user.getId();
        SpotLog spotLog = new SpotLog(spot.getId(), user.getId(), spotEvent);
        spotLogService.saveSpotLog(spotLog);

        return ResponseEntity.ok(new SuccessResponse(200, "Back out car successfully"));
    }
}
