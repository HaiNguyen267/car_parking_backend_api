package com.example.car_parking_backend_api.service;

import com.example.car_parking_backend_api.dto.request.ParkingRequest;
import com.example.car_parking_backend_api.dto.response.ParkingBill;
import com.example.car_parking_backend_api.dto.response.SuccessResponse;
import com.example.car_parking_backend_api.exception.NotFoundException;
import com.example.car_parking_backend_api.domain.Spot;
import com.example.car_parking_backend_api.domain.User;
import com.example.car_parking_backend_api.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {


    private final UserRepository userRepository;


    public User getUserByEmail(String userEmail) {
        return userRepository
                .findByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException("User not found for email: " + userEmail));
    }

    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public boolean existsById(Long userId) {
        return userRepository.findById(userId).isPresent();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found for id: " + userId));
    }

    public void update(User user) {
        userRepository.update(user);
    }
}
