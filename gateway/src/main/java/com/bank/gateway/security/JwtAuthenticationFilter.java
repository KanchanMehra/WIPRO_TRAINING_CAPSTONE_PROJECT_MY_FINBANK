package com.bank.gateway.security;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT Authentication Filter
 * Validates JWT tokens and extracts user information
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;

	public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
			// Extract JWT token from Authorization header
			String token = getJwtFromRequest(request);

			if (token != null && jwtTokenProvider.validateToken(token)) {
				// Token is valid, extract user info and set in request attributes
				Long userId = jwtTokenProvider.getUserIdFromToken(token);
				String userType = jwtTokenProvider.getUserTypeFromToken(token);
				String userName = jwtTokenProvider.getUserNameFromToken(token);

				// Set attributes for use in controllers
				request.setAttribute("userId", userId);
				request.setAttribute("userType", userType);
				request.setAttribute("userName", userName);

				logger.info("JWT Token validated for user: " + userName + " (Type: " + userType + ")");
			} else if (token != null) {
				logger.warn("JWT Token validation failed");
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().write("{\"error\": \"Invalid or expired token\"}");
				return;
			}
			// If no token is present, continue (public endpoints)

		} catch (Exception e) {
			logger.error("JWT authentication error: " + e.getMessage());
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("{\"error\": \"Authentication failed\"}");
			return;
		}

		filterChain.doFilter(request, response);
	}

	/**
	 * Extract JWT token from Authorization header
	 */
	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
}

