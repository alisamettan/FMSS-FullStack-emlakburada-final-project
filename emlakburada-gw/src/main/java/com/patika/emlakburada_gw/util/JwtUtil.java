package com.patika.emlakburada_gw.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
@Slf4j
public class JwtUtil {

	private static final String SECRET_KEY = "emlakburada-patika-secret-key-emlakburada-patika-secret-key-emlakburada-patika-secret-key";
	private static final long VALIDITY_DURATION_MS = 24 * 60 * 60 * 1000; // 24 hours in milliseconds
	private Key key;

	@PostConstruct
	public void init() {
		this.key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	}

	public String generateToken(String userName) {
		Date now = new Date();
		Date validity = new Date(now.getTime() + VALIDITY_DURATION_MS);

		return Jwts.builder()
				.setSubject(userName)
				.setIssuedAt(now)
				.setExpiration(validity)
				.signWith(key)
				.compact();
	}

	public Claims getAllClaimsFromToken(String token) {

		//// @formatter:off
		return Jwts
				.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();
		// @formatter:on

	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	public String getUserName(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getExpirationDate(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public boolean isTokenExpired(String token) {

		return this.getExpirationDate(token).before(new Date());

	}

	public boolean isValidToken(String token) {
		try {
			return isTokenExpired(token); // Token geçerliyse false döndürmeli
		} catch (Exception e) {
			log.error("Error while validating token", e);
			return false; // Hata oluştuğunda token geçersiz sayılmalı
		}
	}

}