package com.example.car_parking_backend_api.dto.response;

import com.example.car_parking_backend_api.domain.SimpleSpotInfo;
import com.example.car_parking_backend_api.domain.Spot;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ParkingLotWithEmptySpotsResponse {
    private Map<String, List<SimpleSpotInfo>> emptySpots = new HashMap<>();

    public void addEmptySpotsByZone(String zoneName, List<Spot> emptySpots) {
        List<SimpleSpotInfo> simpleSpotInfos = emptySpots.stream()
                .map(SimpleSpotInfo::new)
                .collect(Collectors.toList());

        this.emptySpots.put(zoneName, simpleSpotInfos);
    }


}
