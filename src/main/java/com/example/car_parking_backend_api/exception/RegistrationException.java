package com.example.car_parking_backend_api.exception;

// TODO: Controller advice to catch this
public class RegistrationException extends RuntimeException {
    public RegistrationException(String message) {
        super(message);
    }
}
