package com.example.car_parking_backend_api.mapper;

import com.example.car_parking_backend_api.domain.Zone;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ZoneMapper {
    String[] findAllZoneNames();

    @Select("SELECT * FROM zone WHERE name = #{zoneName} LIMIT 1")
    Optional<Zone> findZoneByName(String zoneName);

    List<Zone> findAll();

    @Insert("INSERT INTO zone(name, parking_fee) VALUES(#{name}, #{parkingFee})")
    void saveZone(Zone zone);
}
