package com.example.car_parking_backend_api.exception;

import com.example.car_parking_backend_api.dto.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({
            NotFoundException.class,
            LoginFailedException.class,
            ParkingException.class,
            RegistrationException.class,
            SpotException.class,
            UserChangeAccessException.class,
            WrongCredentialsException.class,
            ZoneException.class
    })
    public ResponseEntity<?> handleException(RuntimeException e) {
        ErrorResponse errorResponse = new ErrorResponse(400, e.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleException(MethodArgumentNotValidException e) {
        ErrorResponse errorResponse = new ErrorResponse(400, "Fields should not be null or empty");
        return ResponseEntity.badRequest().body(errorResponse);
    }


}
