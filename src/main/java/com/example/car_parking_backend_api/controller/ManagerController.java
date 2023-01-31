package com.example.car_parking_backend_api.controller;

import com.example.car_parking_backend_api.domain.User;
import com.example.car_parking_backend_api.dto.request.AddSpotToZoneRequest;
import com.example.car_parking_backend_api.dto.request.AddZoneRequest;
import com.example.car_parking_backend_api.dto.request.ChangeParkingFeeRequest;
import com.example.car_parking_backend_api.dto.request.ChangeSpotStatusRequest;
import com.example.car_parking_backend_api.service.ManagerService;
import com.example.car_parking_backend_api.service.UserService;
import com.example.car_parking_backend_api.service.ZoneService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/manager")
@AllArgsConstructor
public class ManagerController {

    private final ManagerService managerService;
    private final UserService userService;


    @PostMapping("/add-zone")
    public ResponseEntity<?> addZone(@RequestBody AddZoneRequest addZoneRequest) {
        User currentManager = getCurrentManager();
        return managerService.addZone(currentManager, addZoneRequest);
    }


    @PostMapping("/change-zone-parking-fee")
    public ResponseEntity<?> changeZoneParkingFee(@RequestBody ChangeParkingFeeRequest changeParkingFeeRequest) {
        User currentManager = getCurrentManager();
        return managerService.setParkingFeeForZone(currentManager, changeParkingFeeRequest);
    }



    @PostMapping("/add-spot-to-zone")
    public ResponseEntity<?> addSpotToZone(@RequestBody AddSpotToZoneRequest addSpotToZoneRequest) {
        User currentManager = getCurrentManager();
        return managerService.addSpotToZone(currentManager, addSpotToZoneRequest);
    }


    @PostMapping("/add-spots-to-zone/from/{from}/to/{to}")
    public ResponseEntity<?> addSpotsToZoneFromTo(@RequestBody AddSpotToZoneRequest addSpotToZoneRequest,
                                                  @PathVariable Long from,
                                                  @PathVariable Long to) {
        User currentManager = getCurrentManager();
        return managerService.addSpotsToZoneFromTo(currentManager, addSpotToZoneRequest, from, to);
    }


    //lock spot
    @PostMapping("/lock-spot")
    public ResponseEntity<?> lockSpot(@RequestBody ChangeSpotStatusRequest lockSpotRequest) {
        User currentManager = getCurrentManager();
        return managerService.lockSpot(currentManager, lockSpotRequest);
    }

    // unlock spot
    @PostMapping("/unlock-spot")
    public ResponseEntity<?> unlockSpot(@RequestBody ChangeSpotStatusRequest lockSpotRequest) {
        User currentManager = getCurrentManager();
        return managerService.unlockSpot(currentManager, lockSpotRequest);
    }


    @GetMapping("/view-parking-history-of-user/{userId}")
    public ResponseEntity<?> viewParkingHistoryOfUser(@PathVariable Long userId) {
        return managerService.viewParkingHistoryOfUser(userId);
    }

    @GetMapping("/view-parking-history-of-spot/{zoneName}/{spotCode}")
    public ResponseEntity<?> viewParkingHistoryOfSpot(@PathVariable String zoneName, @PathVariable Long spotCode) {
        return managerService.viewParkingHistoryOfSpot(zoneName, spotCode);
    }

    private User getCurrentManager() {
        String email = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        return userService.getUserByEmail(email);
    }
}
