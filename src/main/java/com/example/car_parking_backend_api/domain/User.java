package com.example.car_parking_backend_api.domain;

import com.example.car_parking_backend_api.dto.request.RegistrationRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
    private boolean isLocked;

    public User(RegistrationRequest registrationRequest) {
        this.firstName = registrationRequest.getFirstName();
        this.lastName = registrationRequest.getLastName();
        this.password = registrationRequest.getPassword();
        this.email = registrationRequest.getEmail();
        this.isLocked = false;
    }

}
