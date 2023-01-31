package com.example.car_parking_backend_api.enums;

public enum EventLog {
    // for all users
    LOGIN,

    // for admin
    LOCK_USER, UNLOCK_USER, REGISTER_MANAGER,

    // for drivers
    REGISTER, PARK_CAR, BACK_OUT,

    // for manager
    ADD_ZONE, CHANGE_PARKING_FEE, ADD_SPOT, ADD_MULTIPLE_SPOTS, LOCK_SPOT, UNLOCK_SPOT,

    // for spot
    PARKED, BACKED_OUT
}
