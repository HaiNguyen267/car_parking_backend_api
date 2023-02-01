package com.example.car_parking_backend_api.service;

import com.example.car_parking_backend_api.domain.UserLog;
import com.example.car_parking_backend_api.dto.request.LoginRequest;
import com.example.car_parking_backend_api.dto.request.RegistrationRequest;
import com.example.car_parking_backend_api.dto.response.SuccessResponse;
import com.example.car_parking_backend_api.enums.EventLog;
import com.example.car_parking_backend_api.exception.LoginFailedException;
import com.example.car_parking_backend_api.exception.RegistrationException;
import com.example.car_parking_backend_api.domain.AccessToken;
import com.example.car_parking_backend_api.domain.User;
import com.example.car_parking_backend_api.exception.WrongCredentialsException;
import com.example.car_parking_backend_api.security.JwtProvider;
import com.example.car_parking_backend_api.security.Role;
import com.example.car_parking_backend_api.utils.TimeUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserLogService userLogService;
    public ResponseEntity<?> login(LoginRequest loginRequest) {
        String email = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        UsernamePasswordAuthenticationToken token
                = new UsernamePasswordAuthenticationToken(email, password);

        Authentication authentication = authenticationManager.authenticate(token);

        if (authentication.isAuthenticated()) {

            User user = userService.getUserByEmail(email);
            if (user.isLocked()) {
                throw new LoginFailedException("Account was locked");
            }
            AccessToken accessToken = JwtProvider.generateToken(user);

            SuccessResponse response = new SuccessResponse(200,"Login successful", accessToken);
            return ResponseEntity.ok(response);
        } else {
            throw new WrongCredentialsException("Wrong username or password");

        }
    }


    public ResponseEntity<?> register(RegistrationRequest registrationRequest) {
        String email = registrationRequest.getEmail();

        if (userService.existsByEmail(email)) {
            throw new RegistrationException("Email already exists");
        }

        User user = new User(registrationRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER.name());
        userService.save(user);

        // to get the id of the user after saving
        user = userService.getUserByEmail(email);

        // create user log
        String event = EventLog.REGISTER + " with email=" + user.getEmail();
        UserLog userLog = new UserLog(user.getId(), event);
        userLogService.saveUserLog(userLog);

        AccessToken accessToken = JwtProvider.generateToken(user);
        SuccessResponse response = new SuccessResponse(200, "Registration successful", accessToken);
        return ResponseEntity.ok(response);
    }
}
