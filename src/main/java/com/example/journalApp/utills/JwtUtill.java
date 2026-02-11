package com.example.journalApp.utills;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtill {
     public String generateToken(String username) {
         // you can send some email, or any additional information to create the token
         Map<String, Object> claims = new HashMap<>();
         return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
         return Jwts.builder().claims(claims)
                 .subject(subject)
                 .header().empty().add("typ", "JWT")
                 .and()
                 .issuedAt(new Date(System.currentTimeMillis()))
                 .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 60 minute expiration
                 .signWith(getSignKey())
                 .compact();

    }

    private String SECRET_KEY = "xxxx";
    private Key getSignKey() {
         return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String extractUsername(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }
}
