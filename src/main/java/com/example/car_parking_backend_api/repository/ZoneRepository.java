package com.example.car_parking_backend_api.repository;

import com.example.car_parking_backend_api.mapper.ZoneMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class ZoneRepository {

    @Autowired
    private ZoneMapper zoneMapper;

    public List<String> findAllZoneNames() {
        return Arrays.stream(zoneMapper.findAllZoneNames()).toList();

    }
}
