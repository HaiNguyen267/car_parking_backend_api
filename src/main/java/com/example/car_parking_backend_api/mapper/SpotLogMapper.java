package com.example.car_parking_backend_api.mapper;

import com.example.car_parking_backend_api.domain.SpotLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SpotLogMapper {
    @Select("SELECT * FROM spot_logs WHERE user_id = #{userId}")
    List<SpotLog> findSpotLogsByUserId(Long userId);

    @Select("SELECT * FROM spot_logs WHERE spot_id = #{spotId}")
    List<SpotLog> findSpotLogsBySpotId(Long spotId);
}
