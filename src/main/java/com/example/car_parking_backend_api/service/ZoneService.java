package com.example.car_parking_backend_api.service;

import com.example.car_parking_backend_api.domain.Spot;
import com.example.car_parking_backend_api.dto.request.AddSpotToZoneRequest;
import com.example.car_parking_backend_api.dto.request.AddZoneRequest;
import com.example.car_parking_backend_api.domain.Zone;
import com.example.car_parking_backend_api.dto.response.SpotDTO;
import com.example.car_parking_backend_api.dto.response.SuccessResponse;
import com.example.car_parking_backend_api.repository.ZoneRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ZoneService {


    private final ZoneRepository zoneRepository;

    private final SpotService spotService;

    public List<String> getAllZoneNames() {
        return zoneRepository.findAllZoneNames();
    }


    public ResponseEntity<?> addZone(AddZoneRequest addZoneRequest) {
        return null;
    }

    public ResponseEntity<?> addSpotToZone(AddSpotToZoneRequest addSpotToZoneRequest) {
        return null;
    }

    public Zone getZoneByName(String zoneName) {
        return zoneRepository.findZoneByName(zoneName);
    }

    public boolean existsByName(String zoneName) {
        return zoneRepository.existsByName(zoneName);
    }
    public ResponseEntity<?> getAllEmptySpots() {
        List<Zone> zones = zoneRepository.findAll();

        Map<String, List<SpotDTO>> map = new LinkedHashMap<>();

        for (Zone zone : zones) {
            List<Spot> emptySpots = spotService.getEmptySpotsByZoneName(zone.getName());
            List<SpotDTO> spotDTOs = emptySpots.stream().map(SpotDTO::new).collect(Collectors.toList());
            map.put(zone.getName(), spotDTOs);
        }
        SuccessResponse successResponse = new SuccessResponse(200, "Get all empty spots succesfully", map);
        return ResponseEntity.ok(successResponse);
    }

    public void saveZone(Zone zone) {
        zoneRepository.saveZone(zone);
    }

    public ResponseEntity<?> getPricingDetails() {
        Map<String, Double> pricingByZone = new LinkedHashMap<>();

        List<Zone> zones = zoneRepository.findAll();
        for (Zone zone : zones) {
            pricingByZone.put(zone.getName(), zone.getParkingFee());
        }

        SuccessResponse successResponse = new SuccessResponse(200, "Get pricing details successfully", pricingByZone);
        return ResponseEntity.ok(successResponse);
    }
}
