package com.example.car_parking_backend_api.security;

import com.example.car_parking_backend_api.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;


public class JwtProvider {

    private static final long INTERVAL = 1000 * 60 * 60 * 24 * 30; // 30 days
    private static final String SECRET_KEY = "o92-m9caj2042j2l421";

    public static String generateToken(User user) {
        String email = user.getEmail();
        String role = user.getRole();

        Map<String, String> claims = Map.of(
                "email", email,
                "role", role
        );
        long now = new Date().getTime();
        String accessToken = Jwts.builder()
                .setSubject(email)
                .setClaims(claims)
                .setIssuedAt(new java.util.Date())
                .setExpiration(new Date(now + INTERVAL))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
        return accessToken;
    }

    public static Claims getClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            //TODO: InValidTokenException
            return null;
        }
    }


}
