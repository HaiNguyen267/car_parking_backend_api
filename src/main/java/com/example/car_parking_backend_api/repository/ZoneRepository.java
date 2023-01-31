package com.example.car_parking_backend_api.repository;

import com.example.car_parking_backend_api.exception.NotFoundException;
import com.example.car_parking_backend_api.mapper.ZoneMapper;
import com.example.car_parking_backend_api.domain.Zone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class ZoneRepository {

    @Autowired
    private ZoneMapper zoneMapper;

    public List<String> findAllZoneNames() {
        return zoneMapper.findAllZoneNames();

    }

    public boolean existsByName(String zoneName) {
        return zoneMapper.findZoneByName(zoneName).isPresent();
    }
    public Zone findZoneByName(String zoneName) {
        return zoneMapper
                .findZoneByName(zoneName)
                .orElseThrow(() -> new NotFoundException("Zone not found for name " + zoneName + ""));
    }

    public List<Zone> findAll() {
        return zoneMapper.findAll();
    }

    public void saveZone(Zone zone) {
        zoneMapper.saveZone(zone);
    }
}
