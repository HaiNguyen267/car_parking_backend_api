package com.example.car_parking_backend_api.controller;

import com.example.car_parking_backend_api.dto.request.ParkingRequest;
import com.example.car_parking_backend_api.dto.response.ErrorResponse;
import com.example.car_parking_backend_api.dto.response.ParkingBill;
import com.example.car_parking_backend_api.dto.response.SuccessResponse;
import com.example.car_parking_backend_api.domain.User;
import com.example.car_parking_backend_api.service.DriverService;
import com.example.car_parking_backend_api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/driver")
@AllArgsConstructor
public class DriverController {
//TODO: swagger for this controller

    private final UserService userService;

    private final DriverService driverService;



    @PostMapping("/park")
    public ResponseEntity<?> park(@RequestBody ParkingRequest parkingRequest) {
        User user = getCurrentLoginUser();
        System.out.println("current login user = " + user);
        return driverService.park(user, parkingRequest);
    }



    @GetMapping("/bill")
    public ResponseEntity<?> getParkingBill() {
        User user = getCurrentLoginUser();
        return driverService.getParkingBill(user);
    }



    @PostMapping("/backout")
    public ResponseEntity<?> backOut() {
        User user = getCurrentLoginUser();
        return driverService.backOut(user);
    }



    private User getCurrentLoginUser() {
        String userEmail = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        return userService.getUserByEmail(userEmail);
    }

}
