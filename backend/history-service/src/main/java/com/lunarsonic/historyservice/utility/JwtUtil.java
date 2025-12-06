package com.lunarsonic.historyservice.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.enterprise.context.ApplicationScoped;

import javax.crypto.SecretKey;

@ApplicationScoped
public class JwtUtil {
    private static final SecretKey ACCESS_KEY;

    static {
        try {
            String accessSecret = System.getenv("JWT_ACCESS_SECRET");
            if (accessSecret == null){
                throw new RuntimeException("JWT секрета нет в переменных окружения");
            }
            ACCESS_KEY = Keys.hmacShaKeyFor(accessSecret.getBytes());
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при инициализации JwtUtil: " + e);
        }
    }

    public Long getUserId(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(ACCESS_KEY)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return claims.get("user_id", Long.class);
        } catch (Exception e) {
            return null;
        }
    }
}
