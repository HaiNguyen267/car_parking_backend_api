package com.example.car_parking_backend_api.service;

import com.example.car_parking_backend_api.mapper.ZoneMapper;
import com.example.car_parking_backend_api.repository.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZoneService {

    @Autowired
    private ZoneRepository zoneRepository;

    public List<String> getAllZoneNames() {
        return zoneRepository.findAllZoneNames();
    }


}
