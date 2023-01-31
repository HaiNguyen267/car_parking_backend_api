package com.example.car_parking_backend_api.controller;

import com.example.car_parking_backend_api.dto.request.RegistrationRequest;
import com.example.car_parking_backend_api.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    //TODO: swagger for this controller
    // register manager
    // lock user
    // unlock user
    // view all users
    // view logs of user (user can be driver or manager)

    @PostMapping("/register-manager")
    public ResponseEntity<?> registerManager(@RequestBody RegistrationRequest registrationRequest) {
        //TODO: get current admin
        return adminService.registerManager(registrationRequest);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        return adminService.getAllUsers();
    }

    @PostMapping("/users/{userId}/lock")
    public ResponseEntity<?> lockUser(@PathVariable Long userId) {
        return adminService.lockUser(userId);
    }


    @PostMapping("/users/{userId}/unlock")
    public ResponseEntity<?> unlockUser(@PathVariable Long userId) {
        return adminService.unlockUser(userId);
    }

    @PostMapping("/users/{userId}/logs")
    public ResponseEntity<?> getUserLogs(@PathVariable Long userId) {
        return adminService.getUserLogs(userId);
    }




}
