package com.bank.admin_service.security;

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
 * JWT Token Provider for admin service
 */
@Component
public class JwtTokenProvider {

	@Value("${jwt.secret:CodingIsNotAPieceOfCake404ERRORR}")
	private String jwtSecret;

	@Value("${jwt.expiration:86400000}")
	private long jwtExpirationMs;

	public String generateToken(String adminId, String userType, String userName) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("adminId", adminId);
		claims.put("userType", userType);
		claims.put("userName", userName);

		return createToken(claims, adminId);
	}

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

	public boolean validateToken(String token) {
		try {
			SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
			Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Claims getClaimsFromToken(String token) {
		try {
			SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
			return Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(token)
					.getBody();
		} catch (Exception e) {
			return null;
		}
	}

	public String getAdminIdFromToken(String token) {
		Claims claims = getClaimsFromToken(token);
		if (claims != null) {
			return (String) claims.get("adminId");
		}
		return null;
	}

	public String getUserTypeFromToken(String token) {
		Claims claims = getClaimsFromToken(token);
		if (claims != null) {
			return (String) claims.get("userType");
		}
		return null;
	}
}

