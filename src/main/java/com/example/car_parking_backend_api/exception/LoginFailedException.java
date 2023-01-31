package com.example.car_parking_backend_api.exception;

//TODO: catch this exception using ControllerAdvice
public class LoginFailedException extends RuntimeException {
    public LoginFailedException(String message) {
        super(message);
    }
}
