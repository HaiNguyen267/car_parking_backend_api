package com.example.car_parking_backend_api.exception;

//TODO: catch this exception using ControllerAdvice
public class WrongCredentialsException extends RuntimeException {
    public WrongCredentialsException(String message) {
        super(message);
    }
}
