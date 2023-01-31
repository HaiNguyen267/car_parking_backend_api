package com.example.car_parking_backend_api.service;

import com.example.car_parking_backend_api.dto.request.ParkingRequest;
import com.example.car_parking_backend_api.dto.response.ParkingBill;
import com.example.car_parking_backend_api.dto.response.SuccessResponse;
import com.example.car_parking_backend_api.exception.NotFoundException;
import com.example.car_parking_backend_api.domain.Spot;
import com.example.car_parking_backend_api.domain.User;
import com.example.car_parking_backend_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SpotService spotService;

    @Autowired
    private ZoneService zoneService;


    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getUserByEmail(String userEmail) {
        //TODO: NOT FOUND EXCEPTION
        return userRepository
                .findByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException("User not found for email: " + userEmail));
    }

    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
//    public ResponseEntity<?> park(User user, ParkingRequest parkingRequest) {
//
//        //TODO: check if user is already parked, change the method
//        checkAndThrowExceptionIfUserHaveAlreadyParkedCar(user);
//
//        Long spotCode = parkingRequest.getSpotCode();
//
//        checkAndThrowExceptionIfSpotIsUnavailable(spotCode);
//
//        Spot spot = spotService.getSpotByCode(spotCode);
//
//        spot.setUserId(user.getId());
//        spot.setStartParkingTime(currentLocalTime());
//        System.out.println("spot in UserService= " + spot);
//
//        spotService.update(spot);
//        return createSuccessResponse();
//    }

    private static String currentLocalTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }


//    public ResponseEntity<?> getParkingBill(User user) {
//        ParkingBill parkingBill = getBill(user);
//        return ResponseEntity.ok(parkingBill);
//    }

    private ParkingBill getBill(User user) {
        checkAndThrowExceptionIfUserHaveNotParkedCar(user);

        Spot spot = spotService.getParkedSpotByUserId(user.getId());
        ParkingBill parkingBill = createParkingBill(spot);
        return parkingBill;
    }

    private ParkingBill createParkingBill(Spot spot) {
        // TODO: calculate the bill
        LocalDateTime startParkingTime = LocalDateTime.now();

        int totalParkHours = (LocalDateTime.now().getHour() - startParkingTime.getHour());
        int price = calculatePrice(startParkingTime);

        return new ParkingBill(totalParkHours, price);
    }

    private int calculatePrice(LocalDateTime startParkingTime) {
        //TODO: implement this method
        return 0;
    }


//    public ResponseEntity<?> backOut(User user) {
//        ParkingBill parkingBill = getBill(user);
//
//        Spot spot = spotService.getParkedSpotByUserId(user.getId());
//        spot.setUserId(null);
//        spot.setStartParkingTime(null);
//
//        spotService.update(spot);
//        return ResponseEntity.ok(parkingBill);
//    }

    private ResponseEntity<?> createSuccessResponse() {
        SuccessResponse response = new SuccessResponse(200, "Car parked successfully");
        return ResponseEntity.ok(response);
    }

    private void checkAndThrowExceptionIfSpotIsUnavailable(String spotCode) {
        Spot spot = spotService.getSpotByCode(spotCode);

        if (spot.getUserId() != null) {
            // this spot is already taken by other user
            // TODO; SPOTNOTAVAILABLEEXCEPTION
            throw new RuntimeException("Spot is not available");
        }
    }

    private void checkAndThrowExceptionIfUserHaveNotParkedCar(User user) {

        if (!checkIfUserHaveParkedACar(user)) {
            // TODO; NOTPARKINGEXCEPTION
            throw new RuntimeException("You have not parked a car");
        }
    }

    private void checkAndThrowExceptionIfUserHaveAlreadyParkedCar(User user) {

        if (checkIfUserHaveParkedACar(user)) {
            // TODO; ALREADYPARKED
            throw new RuntimeException("You have not parked a car");
        }
    }

    private boolean checkIfUserHaveParkedACar(User user) {

        Spot spot = spotService.getParkedSpotByUserId(user.getId());
        return spot != null;
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
}
