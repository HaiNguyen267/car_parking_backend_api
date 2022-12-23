package com.example.car_parking_backend_api.repository;

import com.example.car_parking_backend_api.model.Spot;
import com.example.car_parking_backend_api.mapper.SpotMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SpotRepository {
    @Autowired
    private SpotMapper spotMapper;

    public Spot update(Spot spot) {
        return spotMapper.update(spot);

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
}
