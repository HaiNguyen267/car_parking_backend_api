package com.example.car_parking_backend_api.mapper;

import com.example.car_parking_backend_api.domain.Spot;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Optional;

@Mapper
public interface SpotMapper {
    @Update("UPDATE spot SET user_id = #{userId}, start_parking_time = #{startParkingTime}, status = #{status} WHERE code = #{code}")
    void update(Spot spot);

    @Select("SELECT * FROM spot WHERE id = #{id}")
    Spot findById(long id);

    List<Spot> findAll();

    Spot[] findEmptySpotsByZoneName(String zoneName);

    @Select("SELECT * FROM spot WHERE code = #{code}")
    Spot findByCode(String code);


    @Select("SELECT * FROM spot WHERE user_id = #{id}")
    Spot findByUserId(long id);

    @Select("SELECT * FROM spot WHERE zone_name = #{zoneName} AND code = #{spotCode}")
    Optional<Spot> findByZoneNameAndCode(String zoneName, Long spotCode);

    @Select("SELECT * FROM spot WHERE user_id = #{userId} LIMIT 1")
    Optional<Spot> findSpotByUserId(Long userId);

    @Select("INSERT INTO spot (zone_name, code, status, user_id, start_parking_time) VALUES (#{zoneName}, #{code}, #{status}, #{userId}, #{startParkingTime})")
    void save(Spot spot);
}
