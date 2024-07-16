package com.patika.emlakburada_user.util;


import com.patika.emlakburada_user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;


import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "emlakburada-patika-secret-key-emlakburada-patika-secret-key-emlakburada-patika-secret-key";

    private static final long EXPIRATION_TIME = 3000_000;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    private Claims getAllClaimsFromToken(String token) {
        //// @formatter:off
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        // @formatter:on
    }

    public String getUserName(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public Date getExpirationDate(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    public boolean isTokenExpired(String token) {
        return this.getExpirationDate(token).before(new Date());
    }

    public String generateToken(User user) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", user.getEmail());
        claims.put("id", user.getId());

        long now = System.currentTimeMillis();

        // @formatter:off

        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setIssuer("emlakburada")
                .setExpiration( new Date(now + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        // @formatter:on
    }

}