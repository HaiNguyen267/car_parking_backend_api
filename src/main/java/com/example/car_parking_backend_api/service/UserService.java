package com.example.car_parking_backend_api.service;

import com.example.car_parking_backend_api.dto.request.ParkingRequest;
import com.example.car_parking_backend_api.dto.response.ParkingBill;
import com.example.car_parking_backend_api.dto.response.SuccessResponse;
import com.example.car_parking_backend_api.model.Spot;
import com.example.car_parking_backend_api.model.User;
import com.example.car_parking_backend_api.model.Zone;
import com.example.car_parking_backend_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SpotService spotService;

    @Autowired
    private ZoneService zoneService;

    public User getUserByEmail(String userEmail) {
        return userRepository.getUserByEmailIgnoreCase(userEmail);
    }

    public ResponseEntity<?> park(User user, ParkingRequest parkingRequest) {

        //TODO: check if user is already parked, change the method
        checkAndThrowExceptionIfUserHaveAlreadyParkedCar(user);

        String spotCode = parkingRequest.getSpotCode();

        checkAndThrowExceptionIfSpotIsUnavailable(spotCode);

        Spot spot = spotService.getSpotByCode(spotCode);
        checkAndThrowExceptionIfUserHaveNoRightToPark(user, spot.getZoneName());

        spot.setUserId(user.getId());

        spotService.update(spot);
        return createSuccessResponse("Car parked successfully");
    }

    private void checkAndThrowExceptionIfUserHaveNoRightToPark(User user, String zoneName) {
        List<Zone> zones = getAllZonesUserCanPark(user);

        boolean hasRight = zones.stream().anyMatch(zone -> zone.getName().equals(zoneName));

        if (!hasRight) {
            //TODO: NORIGHTTOPARKEXCEPTION
            throw new RuntimeException("You have no right to park in this zone");
        }

    }

    private List<Zone> getAllZonesUserCanPark(User user) {
        return userRepository.getAllZonesUserCanPark(user);
    }


    public ResponseEntity<?> getParkingBill(User user) {
        ParkingBill parkingBill = getBill(user);
        return ResponseEntity.ok(parkingBill);
    }

    private ParkingBill getBill(User user) {
        checkAndThrowExceptionIfUserHaveNotParkedCar(user);

        Spot spot = spotService.getParkedSpotByUserId(user.getId());
        ParkingBill parkingBill = createParkingBill(spot);
        return parkingBill;
    }

    private ParkingBill createParkingBill(Spot spot) {
        LocalDateTime startParkingTime = spot.getStartParkingTime();

        int totalParkHours = (LocalDateTime.now().getHour() - startParkingTime.getHour());
        int price = calculatePrice(startParkingTime);

        return new ParkingBill(totalParkHours, price);
    }

    private int calculatePrice(LocalDateTime startParkingTime) {
        //TODO: implement this method
        return 0;
    }


    public ResponseEntity<?> backOut(User user) {
        ParkingBill parkingBill = getBill(user);

        Spot spot = spotService.getParkedSpotByUserId(user.getId());
        spot.setUserId(null);
        spot.setStartParkingTime(null);

        spotService.update(spot);
        return ResponseEntity.ok(parkingBill);
    }


    private ResponseEntity<?> createSuccessResponse(String msg) {
        SuccessResponse response = new SuccessResponse(msg);
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
}
