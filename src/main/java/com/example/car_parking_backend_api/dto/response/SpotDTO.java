package com.example.car_parking_backend_api.dto.response;

import com.example.car_parking_backend_api.domain.Spot;
import lombok.Data;

@Data
public class SpotDTO {
    private Long id;
    private String code;

    public SpotDTO(Spot spot) {
        this.id = spot.getId();
        this.code = spot.getCode().toString();
    }
}
