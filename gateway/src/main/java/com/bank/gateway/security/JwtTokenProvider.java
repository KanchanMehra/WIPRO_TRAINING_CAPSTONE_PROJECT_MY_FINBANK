package com.bank.gateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT Token Provider for generating and validating JWT tokens
 */
@Component
public class JwtTokenProvider {

	@Value("${jwt.secret:CodingIsNotAPieceOfCake404ERROR}")
	private String jwtSecret;

	@Value("${jwt.expiration:86400000}")
	private long jwtExpirationMs; // 24 hours default

	/**
	 * Generate JWT token
	 */
	public String generateToken(Long userId, String userType, String userName) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("userId", userId);
		claims.put("userType", userType); // CUSTOMER or ADMIN
		claims.put("userName", userName);

		return createToken(claims, userId.toString());
	}

	/**
	 * Create JWT token with claims
	 */
	private String createToken(Map<String, Object> claims, String subject) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

		SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(now)
				.setExpiration(expiryDate)
				.signWith(key, SignatureAlgorithm.HS512)
				.compact();
	}

	/**
	 * Validate JWT token
	 */
	public boolean validateToken(String token) {
		try {
			SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
			Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			System.err.println("JWT validation failed: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Get claims from JWT token
	 */
	public Claims getClaimsFromToken(String token) {
		try {
			SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
			return Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(token)
					.getBody();
		} catch (Exception e) {
			System.err.println("Failed to get claims from token: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Get user ID from token
	 */
	public Long getUserIdFromToken(String token) {
		Claims claims = getClaimsFromToken(token);
		if (claims != null) {
			return ((Number) claims.get("userId")).longValue();
		}
		return null;
	}

	/**
	 * Get user type from token
	 */
	public String getUserTypeFromToken(String token) {
		Claims claims = getClaimsFromToken(token);
		if (claims != null) {
			return (String) claims.get("userType");
		}
		return null;
	}

	/**
	 * Get username from token
	 */
	public String getUserNameFromToken(String token) {
		Claims claims = getClaimsFromToken(token);
		if (claims != null) {
			return (String) claims.get("userName");
		}
		return null;
	}

	/**
	 * Check if token is expired
	 */
	public boolean isTokenExpired(String token) {
		Claims claims = getClaimsFromToken(token);
		if (claims != null) {
			return claims.getExpiration().before(new Date());
		}
		return true;
	}
}

