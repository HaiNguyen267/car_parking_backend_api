package com.example.car_parking_backend_api.repository;

import com.example.car_parking_backend_api.domain.SpotLog;
import com.example.car_parking_backend_api.mapper.SpotLogMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class SpotLogRepository {

    private final SpotLogMapper spotLogMapper;

    public List<SpotLog> findSpotLogsByUserId(Long userId) {
        return spotLogMapper.findSpotLogsByUserId(userId);
    }

    public List<SpotLog> findSpotLogsBySpotId(Long spotId) {
        return spotLogMapper.findSpotLogsBySpotId(spotId);
    }
}
