package com.example.car_parking_backend_api.security;

import com.example.car_parking_backend_api.domain.AccessToken;
import com.example.car_parking_backend_api.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class JwtProvider {
    private static final String SECRET_KEY = "23402523535203523kfsmdfmdfmadomoqmoimiom1241424";
    private static final long EXPIRATION_INTERVAL = 1000L * 60 * 60 * 24 * 30;// 30 day
    public static AccessToken generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole());
        claims.put("email", user.getEmail());

        long now = new Date().getTime();

        String accessToken = Jwts.builder()
                .setSubject(user.getEmail())
                .setClaims(claims)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + EXPIRATION_INTERVAL))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
                .compact();

        return new AccessToken(accessToken);
    }

    public static Jws<Claims> getClaims(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
        return claimsJws;
    }
}
