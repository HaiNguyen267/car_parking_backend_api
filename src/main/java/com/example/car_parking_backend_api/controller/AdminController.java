package com.example.car_parking_backend_api.controller;

import com.example.car_parking_backend_api.domain.User;
import com.example.car_parking_backend_api.dto.request.RegistrationRequest;
import com.example.car_parking_backend_api.service.AdminService;
import com.example.car_parking_backend_api.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final UserService userService;



    @PostMapping("/register-manager")
    public ResponseEntity<?> registerManager(@RequestBody RegistrationRequest registrationRequest) {
        //TODO: get current admin
        User currentAdmin = getCurrentAdmin();
        return adminService.registerManager(currentAdmin, registrationRequest);
    }


    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        return adminService.getAllUsers();
    }

    @PostMapping("/users/{userId}/lock")
    public ResponseEntity<?> lockUser(@PathVariable Long userId) {
        User currentAdmin = getCurrentAdmin();
        return adminService.lockUser(currentAdmin, userId);
    }


    @PostMapping("/users/{userId}/unlock")
    public ResponseEntity<?> unlockUser(@PathVariable Long userId) {
        User currentAdmin = getCurrentAdmin();
        return adminService.unlockUser(currentAdmin, userId);
    }

    @GetMapping("/users/{userId}/logs")
    public ResponseEntity<?> getUserLogs(@PathVariable Long userId) {
        return adminService.getUserLogs(userId);
    }


    private User getCurrentAdmin() {
        String currentEmail = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        return userService.getUserByEmail(currentEmail);
    }

}
