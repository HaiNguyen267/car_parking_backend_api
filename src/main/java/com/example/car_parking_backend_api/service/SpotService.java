package com.example.car_parking_backend_api.service;

import com.example.car_parking_backend_api.domain.Spot;
import com.example.car_parking_backend_api.repository.SpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class SpotService {

    @Autowired
    private SpotRepository spotRepository;

    public Spot getSpotByCode(String spotCode) {
        return spotRepository.findByCode(spotCode);
    }

    public void update(Spot spot) {
        spotRepository.update(spot);
    }

    public Spot getParkedSpotByUserId(long id) {
        return spotRepository.findByUserId(id);
    }

    public List<Spot> getAllSpots() {
        return spotRepository.findAll();
    }

    public List<Spot> getEmptySpotsByZoneName(String zoneName) {
        return Arrays.stream(spotRepository.findEmptySpotsByZoneName(zoneName)).toList();
    }

    public Spot getSpot(String zoneName, Long spotCode) {
        return spotRepository.findByZoneNameAndCode(zoneName, spotCode);
    }

    public Optional<Spot> getSpotByUserId(Long userId) {
        return spotRepository.findSpotByUserId(userId);
    }

    public void saveSpot(Spot spot) {
        spotRepository.save(spot);
    }

    public boolean existsByCodeAndZoneName(String zoneName, Long spotCode) {
        return spotRepository.existsByCodeAndZoneName(zoneName, spotCode);
    }
}
