package com.example.car_parking_backend_api.service;

import com.example.car_parking_backend_api.model.Spot;
import com.example.car_parking_backend_api.repository.SpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class SpotService {

    @Autowired
    private SpotRepository spotRepository;

    public Spot getSpotByCode(String spotCode) {
        return null;
    }

    public void update(Spot spot) {
        spotRepository.update(spot);
    }

    public Spot getParkedSpotByUserId(long id) {
        return spotRepository.findById(id);
    }

    public List<Spot> getAllSpots() {

        return spotRepository.findAll();
    }

    public List<Spot> getEmptySpotsByZoneName(String zoneName) {
        return Arrays.stream(spotRepository.findEmptySpotsByZoneName(zoneName)).toList();
    }
}
