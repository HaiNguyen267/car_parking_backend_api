package com.example.car_parking_backend_api.service;

import com.example.car_parking_backend_api.dto.request.LoginRequest;
import com.example.car_parking_backend_api.dto.response.LoginResponse;
import com.example.car_parking_backend_api.model.User;
import com.example.car_parking_backend_api.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<?> login(LoginRequest loginRequest) {
        String email = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        UsernamePasswordAuthenticationToken token
                = new UsernamePasswordAuthenticationToken(email, password);

        Authentication authentication = authenticationManager.authenticate(token);
        if (authentication.isAuthenticated()) {
            User user = userService.getUserByEmail(email);
            return createResponseWithAccessToken(user);
        } else {
            //TODO: WrongUsernameOrPassword
            throw new RuntimeException("Wrong username or password");
        }
    }

    public ResponseEntity<LoginResponse> createResponseWithAccessToken(User user) {
        String accessToken = JwtProvider.generateToken(user);
        LoginResponse loginResponse = new LoginResponse(accessToken);
        return ResponseEntity.ok(loginResponse);
    }
}
