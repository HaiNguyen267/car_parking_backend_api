package com.example.car_parking_backend_api.mapper;

import com.example.car_parking_backend_api.domain.Spot;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Optional;

@Mapper
public interface SpotMapper {

    @Select("SELECT * FROM spot WHERE id = #{id}")
    Spot findById(long id);

    List<Spot> findAll();

    List<Spot> findEmptySpotsByZoneName(String zoneName);

    @Select("SELECT * FROM spot WHERE code = #{code}")
    Spot findByCode(String code);


    Optional<Spot> findByUserId(Long userId);

    Optional<Spot> findByZoneNameAndCode(String zoneName, Long spotCode);


    @Select("INSERT INTO spot (zone_name, code, status, user_id, start_parking_time) VALUES (#{zoneName}, #{code}, #{status}, #{userId}, #{startParkingTime})")
    void save(Spot spot);

    @Update("UPDATE spot SET user_id = #{userId}, start_parking_time = #{startParkingTime}, status = #{status} WHERE code = #{code} AND zone_name=#{zoneName}")
    void update(Spot spot);
}
