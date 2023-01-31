package com.example.car_parking_backend_api.exception;

//TODO: use ControllerAdvice to handle exception
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
