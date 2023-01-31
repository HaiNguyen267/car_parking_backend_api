package com.example.car_parking_backend_api.service;

import com.example.car_parking_backend_api.dto.request.RegistrationRequest;
import com.example.car_parking_backend_api.dto.response.SuccessResponse;
import com.example.car_parking_backend_api.exception.NotFoundException;
import com.example.car_parking_backend_api.exception.RegistrationException;
import com.example.car_parking_backend_api.exception.UserChangeAccessException;
import com.example.car_parking_backend_api.domain.AccessToken;
import com.example.car_parking_backend_api.domain.User;
import com.example.car_parking_backend_api.domain.UserLog;
import com.example.car_parking_backend_api.security.JwtProvider;
import com.example.car_parking_backend_api.security.Role;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminService {

    private final UserLogService userLogService;

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<?> registerManager(RegistrationRequest registrationRequest) {
        String email = registrationRequest.getEmail();

        if (userService.existsByEmail(email)) {
            throw new RegistrationException("Email already exists");
        }

        User user = new User(registrationRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.MANAGER.name());

        userService.save(user);

        //TODO: log for admin when register a new manager
        SuccessResponse response = new SuccessResponse(200, "Registration successful");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }


    public ResponseEntity<?> lockUser(Long userId) {
        checkAndThrowExceptionIfUserNotFound(userId);
        User user = userService.getUserById(userId);

        if (user.isLocked()) {
            throw new UserChangeAccessException("User with id " + userId + " is already locked");
        }

        //tODO: log for admin when lock a user
        user.setLocked(true);
        userService.save(user);
        SuccessResponse response = new SuccessResponse(200, "User is locked successfully");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> unlockUser(Long userId) {
        checkAndThrowExceptionIfUserNotFound(userId);
        User user = userService.getUserById(userId);

        if (!user.isLocked()) {
            throw new UserChangeAccessException("User with id " + userId + " is already unlocked");
        }

        //TODO: log for admin when unlock a user
        user.setLocked(false);
        userService.save(user);
        SuccessResponse response = new SuccessResponse(200, "User is unlocked successfully");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> getUserLogs(Long userId) {
        checkAndThrowExceptionIfUserNotFound(userId);

        List<UserLog> userLogs = userLogService.getUserLogs(userId);
        SuccessResponse response = new SuccessResponse(200, "Retrieve user logs sucessfully", userLogs);
        return ResponseEntity.ok(response);
    }

    private void checkAndThrowExceptionIfUserNotFound(Long userId) {
        if (!userService.existsById(userId)) {
            throw new NotFoundException("User with id " + userId + " not found");
        }
    }





}
