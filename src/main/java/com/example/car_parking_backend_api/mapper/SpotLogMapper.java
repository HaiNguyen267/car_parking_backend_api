package com.example.car_parking_backend_api.mapper;

import com.example.car_parking_backend_api.domain.SpotLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SpotLogMapper {
    List<SpotLog> findSpotLogsByUserId(Long userId);

    List<SpotLog> findSpotLogsBySpotId(Long spotId);

    @Insert("INSERT INTO spot_log (user_id, spot_id, event, created_at) VALUES (#{userId}, #{spotId}, #{event}, #{createdAt})")
    void save(SpotLog spotLog);

    List<SpotLog> findSpotLogsByZoneNameAndSpotCode(String zoneName, Long spotCode);
}
