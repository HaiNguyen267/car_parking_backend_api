package com.example.car_parking_backend_api.service;

import com.example.car_parking_backend_api.dto.request.RegistrationRequest;
import com.example.car_parking_backend_api.dto.response.SuccessResponse;
import com.example.car_parking_backend_api.dto.response.UserDTO;
import com.example.car_parking_backend_api.enums.EventLog;
import com.example.car_parking_backend_api.exception.NotFoundException;
import com.example.car_parking_backend_api.exception.RegistrationException;
import com.example.car_parking_backend_api.exception.UserChangeAccessException;
import com.example.car_parking_backend_api.domain.User;
import com.example.car_parking_backend_api.domain.UserLog;
import com.example.car_parking_backend_api.security.Role;
import com.example.car_parking_backend_api.utils.TimeUtils;
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

    public ResponseEntity<?> registerManager(User currentAdmin, RegistrationRequest registrationRequest) {
        String email = registrationRequest.getEmail();

        if (userService.existsByEmail(email)) {
            throw new RegistrationException("Email already exists");
        }

        User user = new User(registrationRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.MANAGER.name());

        userService.save(user);

        // create log
        String event = EventLog.REGISTER_MANAGER + " with id=" + user.getId();
        UserLog userLog = new UserLog(currentAdmin.getId(), event);
        userLogService.saveUserLog(userLog);

        SuccessResponse response = new SuccessResponse(200, "Registration successful");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDTO> userDTOS = users.stream().map(UserDTO::new).toList();

        SuccessResponse response = new SuccessResponse(200, "Get all users successfully", userDTOS);
        return ResponseEntity.ok(response);
    }


    public ResponseEntity<?> lockUser(User currentAdmin, Long userId) {
        checkAndThrowExceptionIfUserNotFound(userId);
        User user = userService.getUserById(userId);

        if (user.isLocked()) {
            throw new UserChangeAccessException("User with id " + userId + " is already locked");
        }

        user.setLocked(true);
        userService.update(user);

        // create log
        String event = EventLog.LOCK_USER + " with id=" + userId;
        UserLog userLog = new UserLog(currentAdmin.getId(), event);
        userLogService.saveUserLog(userLog);

        SuccessResponse response = new SuccessResponse(200, "User is locked successfully");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> unlockUser(User currentAdmin, Long userId) {
        checkAndThrowExceptionIfUserNotFound(userId);
        User user = userService.getUserById(userId);

        if (!user.isLocked()) {
            throw new UserChangeAccessException("User with id " + userId + " is already unlocked");
        }

        user.setLocked(false);

        userService.update(user);

        // create log
        String event = EventLog.UNLOCK_USER + " with id=" + userId;
        UserLog userLog = new UserLog(currentAdmin.getId(), event);
        userLogService.saveUserLog(userLog);

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
