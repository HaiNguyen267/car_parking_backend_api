package com.example.car_parking_backend_api.service;

import com.example.car_parking_backend_api.domain.Spot;
import com.example.car_parking_backend_api.domain.SpotLog;
import com.example.car_parking_backend_api.domain.User;
import com.example.car_parking_backend_api.domain.Zone;
import com.example.car_parking_backend_api.dto.request.AddSpotToZoneRequest;
import com.example.car_parking_backend_api.dto.request.AddZoneRequest;
import com.example.car_parking_backend_api.dto.request.ChangeParkingFeeRequest;
import com.example.car_parking_backend_api.dto.request.ChangeSpotStatusRequest;
import com.example.car_parking_backend_api.dto.response.SuccessResponse;
import com.example.car_parking_backend_api.enums.SpotStatus;
import com.example.car_parking_backend_api.exception.NotFoundException;
import com.example.car_parking_backend_api.exception.SpotException;
import com.example.car_parking_backend_api.exception.ZoneException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ManagerService {

    private final ZoneService zoneService;
    private final SpotService spotService;
    private final SpotLogService spotLogService;
    public ResponseEntity<?> addZone(User currentManager, AddZoneRequest addZoneRequest) {
        String zoneName = addZoneRequest.getZoneName();

        if (zoneService.existsByName(zoneName)) {
            throw new ZoneException("Zone name already exists");
        }

        double parkingFee = addZoneRequest.getParkingFee();
        Zone zone = Zone.builder()
                .name(zoneName)
                .parkingFee(parkingFee)
                .build();

        zoneService.saveZone(zone);

        // TODO: log the current manager for add a zone
        SuccessResponse successResponse = new SuccessResponse(200, "Zone added successfully");
        return ResponseEntity.ok(successResponse);
    }


    public ResponseEntity<?> setParkingFeeForZone(User currentManager, ChangeParkingFeeRequest changeParkingFeeRequest) {
        String zoneName = changeParkingFeeRequest.getZoneName();
        checkAndThrowExceptionIfZoneNotExist(zoneName);

        double parkingFee = changeParkingFeeRequest.getParkingFee();
        Zone zone = zoneService.getZoneByName(zoneName);
        zone.setParkingFee(parkingFee);
        zoneService.saveZone(zone);

        SuccessResponse successResponse = new SuccessResponse(200, "Parking fee changed successfully");
        return ResponseEntity.ok(successResponse);
    }

    public ResponseEntity<?> addSpotToZone(User currentManager, AddSpotToZoneRequest addSpotToZoneRequest) {
        String zoneName = addSpotToZoneRequest.getZoneName();
        checkAndThrowExceptionIfZoneNotExist(zoneName);

        long spotCode = addSpotToZoneRequest.getSpotCode();
        checkAndThrowExceptionIfSpotNotExists(zoneName, spotCode);


        Spot spot = Spot.builder()
                .code(spotCode)
                .zoneName(zoneName)
                .build();

        spotService.saveSpot(spot);
        //TODO: log the manager for adding spot to zone
        SuccessResponse successResponse = new SuccessResponse(200, "Spot added successfully");
        return ResponseEntity.ok(successResponse);
    }

    @Transactional
    public ResponseEntity<?> addSpotsToZoneFromTo(User currentManager, AddSpotToZoneRequest addSpotToZoneRequest, Long from, Long to) {
        String zoneName = addSpotToZoneRequest.getZoneName();
        checkAndThrowExceptionIfZoneNotExist(zoneName);

        for (long i = from; i <= to; i++) {
            if (!spotService.existsByCodeAndZoneName(zoneName, i)) {
                Spot spot = Spot.builder()
                        .code(i)
                        .zoneName(zoneName)
                        .status(SpotStatus.EMPTY.name())
                        .build();

                spotService.saveSpot(spot);
            }
        }

        //TODO: log the manager for adding multiple spots to zone
        SuccessResponse successResponse = new SuccessResponse(200, "Spots added successfully");
        return ResponseEntity.ok(successResponse);
    }

    public ResponseEntity<?> lockSpot(User currentManager, ChangeSpotStatusRequest lockSpotRequest) {
        String zoneName = lockSpotRequest.getZoneName();
        checkAndThrowExceptionIfZoneNotExist(zoneName);

        long spotCode = lockSpotRequest.getSpotCode();
        checkAndThrowExceptionIfSpotNotExists(zoneName, spotCode);

        Spot spot = spotService.getSpot(zoneName, spotCode);

        if (spot.getStatus().equals(SpotStatus.LOCKED.name())) {
            throw new SpotException("Spot is already locked");
        }

        if (spot.getStatus().equals(SpotStatus.PARKED.name())) {
            throw new SpotException("Spot is being parked, cannot lock it");
        }

        spot.setStatus(SpotStatus.LOCKED.name());
        spotService.update(spot);
        //TODO: log the manager for locking a spot

        SuccessResponse successResponse = new SuccessResponse(200, "Spot locked successfully");
        return ResponseEntity.ok(successResponse);
    }



    public ResponseEntity<?> unlockSpot(User currentManager, ChangeSpotStatusRequest unlockSpotRequest) {
        String zoneName = unlockSpotRequest.getZoneName();
        checkAndThrowExceptionIfZoneNotExist(zoneName);

        long spotCode = unlockSpotRequest.getSpotCode();
        checkAndThrowExceptionIfSpotNotExists(zoneName, spotCode);

        Spot spot = spotService.getSpot(zoneName, spotCode);

        if (!spot.getStatus().equals(SpotStatus.LOCKED.name())) {
            throw new SpotException("Spot is already unlocked");
        }

        spot.setStatus(SpotStatus.EMPTY.name());
        spotService.update(spot);
        //TODO: log the manager for unlocking a spot

        SuccessResponse successResponse = new SuccessResponse(200, "Spot unlocked successfully");
        return ResponseEntity.ok(successResponse);
    }

    private void checkAndThrowExceptionIfZoneNotExist(String zoneName) {
        if (!zoneService.existsByName(zoneName)) {
            throw new NotFoundException("Zone not found for name: " + zoneName);
        }
    }

    private void checkAndThrowExceptionIfSpotNotExists(String zoneName, long spotCode) {
        if (!spotService.existsByCodeAndZoneName(zoneName, spotCode)) {
            throw new NotFoundException("No spot found for zone: " + zoneName);
        }
    }

    public ResponseEntity<?> viewParkingHistoryOfUser(Long userId) {
        List<SpotLog> spotLogs = spotLogService.findSpotLogsByUserId(userId);
        SuccessResponse successResponse = new SuccessResponse(200, "Retrieve parking history of user successfully", spotLogs);
        return ResponseEntity.ok(successResponse);
    }

    public ResponseEntity<?> viewParkingHistoryOfSpot(Long spotId) {
        List<SpotLog> spotLogs = spotLogService.findSpotLogsBySpotId(spotId);
        SuccessResponse successResponse = new SuccessResponse(200, "Retrieve parking history of spot successfully", spotLogs);
        return ResponseEntity.ok(successResponse);
    }
}
