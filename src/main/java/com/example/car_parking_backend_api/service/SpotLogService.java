package com.example.car_parking_backend_api.service;

import com.example.car_parking_backend_api.domain.SpotLog;
import com.example.car_parking_backend_api.repository.SpotLogRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SpotLogService {
    private final SpotLogRepository spotLogRepository;

    public List<SpotLog> findSpotLogsByUserId(Long userId) {
        return spotLogRepository.findSpotLogsByUserId(userId);
    }

    public List<SpotLog> findSpotLogsBySpotId(Long spotId) {
        return spotLogRepository.findSpotLogsBySpotId(spotId);
    }


    public void saveSpotLog(SpotLog spotLog) {
        spotLogRepository.save(spotLog);
    }

    public List<SpotLog> findSpotLogsByZoneNameAndSpotCode(String zoneName, Long spotCode) {
        return spotLogRepository.findSpotLogsByZoneNameAndSpotCode(zoneName, spotCode);
    }
}
