package com.example.car_parking_backend_api.dto.response;

import com.example.car_parking_backend_api.model.SimpleSpotInfo;
import com.example.car_parking_backend_api.model.Spot;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ZoneWithEmptySpotsResponse {

    private List<SimpleSpotInfo> emptySpots;

    public ZoneWithEmptySpotsResponse(List<Spot> emptySpots) {
        this.emptySpots = emptySpots.stream()
                .map(SimpleSpotInfo::new)
                .collect(Collectors.toList());
    }
}
