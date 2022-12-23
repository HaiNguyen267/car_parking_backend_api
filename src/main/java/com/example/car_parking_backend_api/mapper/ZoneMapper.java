package com.example.car_parking_backend_api.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ZoneMapper {
    String[] findAllZoneNames();
}
