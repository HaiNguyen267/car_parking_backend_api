package com.example.car_parking_backend_api.mapper;

import com.example.car_parking_backend_api.model.Spot;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
public interface SpotMapper {
    @Update("UPDATE spot SET user.id = #{userId}, start_parking_time = #{startParkingTime}  WHERE spot.code = #{spotCode}")
    Spot update(Spot spot);

    @Select("SELECT * FORM spot WHERE id = #{id}")
    Spot findById(long id);

    List<Spot> findAll();

    Spot[] findEmptySpotsByZoneName(String zoneName);
}
