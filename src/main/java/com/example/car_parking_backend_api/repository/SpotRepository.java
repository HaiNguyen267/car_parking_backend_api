package com.example.car_parking_backend_api.repository;

import com.example.car_parking_backend_api.exception.NotFoundException;
import com.example.car_parking_backend_api.domain.Spot;
import com.example.car_parking_backend_api.mapper.SpotMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SpotRepository {
    @Autowired
    private SpotMapper spotMapper;

    public void update(Spot spot) {
        spotMapper.update(spot);
    }

    public void save(Spot spot) {
        spotMapper.save(spot);
    }

    public Spot findById(long id) {
        return spotMapper.findById(id);
    }

    public List<Spot> findAll() {

        return spotMapper.findAll();
    }

    public Spot[] findEmptySpotsByZoneName(String zoneName) {
        return spotMapper.findEmptySpotsByZoneName(zoneName);
    }

    public Spot findByCode(String code) {
        return spotMapper.findByCode(code);

    }

    public Spot findByUserId(long id) {
        return spotMapper.findByUserId(id);
    }

    public Spot findByZoneNameAndCode(String zoneName, Long spotCode) {
        return spotMapper
                .findByZoneNameAndCode(zoneName, spotCode)
                .orElseThrow(() -> new NotFoundException("Spot not found for zone " + zoneName + " and code " + spotCode + ""));
    }

    public boolean existsByCodeAndZoneName(String zoneName, Long spotCode) {
        return spotMapper.findByZoneNameAndCode(zoneName, spotCode).isPresent();
    }
    public Optional<Spot> findSpotByUserId(Long userId) {
        return spotMapper.findSpotByUserId(userId);
    }
}
