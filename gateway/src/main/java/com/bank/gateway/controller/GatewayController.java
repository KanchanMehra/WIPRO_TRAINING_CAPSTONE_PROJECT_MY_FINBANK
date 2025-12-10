package com.bank.gateway.controller;

import com.bank.gateway.security.JwtTokenProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Gateway controller for routing requests to backend services
 * Validates JWT tokens before routing to protected endpoints
 */
@Controller
public class GatewayController {

	private final DiscoveryClient discoveryClient;
	private final JwtTokenProvider jwtTokenProvider;

	public GatewayController(DiscoveryClient discoveryClient, JwtTokenProvider jwtTokenProvider) {
		this.discoveryClient = discoveryClient;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	/**
	 * Redirect all /admin/** requests to admin-service
	 * Validates JWT token and checks if user is ADMIN
	 */
	@RequestMapping("/admin/**")
	public void forwardToAdminService(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// Validate JWT token
		if (!validateTokenAndRole(request, response, "ADMIN")) {
			return;
		}

		String path = request.getRequestURI();
		String queryString = request.getQueryString();

		// Get admin-service instance from Eureka
		List<ServiceInstance> instances = discoveryClient.getInstances("admin-service");
		if (instances.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			response.getWriter().write("{\"error\": \"admin-service not available\"}");
			return;
		}

		ServiceInstance instance = instances.get(0);
		String targetUrl = instance.getUri().toString() + path;
		if (queryString != null) {
			targetUrl += "?" + queryString;
		}

		// Forward JWT token to backend service
		response.addHeader("Authorization", request.getHeader("Authorization"));
		response.sendRedirect(targetUrl);
	}

	/**
	 * Redirect all /customer/** requests to customer-service
	 * Validates JWT token and checks if user is CUSTOMER
	 */
	@RequestMapping("/customer/**")
	public void forwardToCustomerService(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// Validate JWT token
		if (!validateTokenAndRole(request, response, "CUSTOMER")) {
			return;
		}

		String path = request.getRequestURI();
		String queryString = request.getQueryString();

		// Get customer-service instance from Eureka
		List<ServiceInstance> instances = discoveryClient.getInstances("customer-service");
		if (instances.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			response.getWriter().write("{\"error\": \"customer-service not available\"}");
			return;
		}

		ServiceInstance instance = instances.get(0);
		String targetUrl = instance.getUri().toString() + path;
		if (queryString != null) {
			targetUrl += "?" + queryString;
		}

		// Forward JWT token to backend service
		response.addHeader("Authorization", request.getHeader("Authorization"));
		response.sendRedirect(targetUrl);
	}

	/**
	 * Validate JWT token and check user role
	 */
	private boolean validateTokenAndRole(HttpServletRequest request, HttpServletResponse response, String requiredRole) throws IOException {
		// Get token from Authorization header
		String authHeader = request.getHeader("Authorization");

		// Public endpoints that don't require authentication
		String path = request.getRequestURI();
		if (isPublicEndpoint(path)) {
			return true;
		}

		// Check if token exists
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("{\"error\": \"Missing or invalid Authorization header\"}");
			return false;
		}

		String token = authHeader.substring(7);

		// Validate token
		if (!jwtTokenProvider.validateToken(token)) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("{\"error\": \"Invalid or expired token\"}");
			return false;
		}

		// Check user role
		String userType = jwtTokenProvider.getUserTypeFromToken(token);
		if (userType == null || !userType.equals(requiredRole)) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.getWriter().write("{\"error\": \"Insufficient permissions\"}");
			return false;
		}

		return true;
	}

	/**
	 * Check if endpoint is public (doesn't require JWT token)
	 */
	private boolean isPublicEndpoint(String path) {
		return path.endsWith("/login") ||
		       path.endsWith("/register") ||
		       path.endsWith("/health") ||
		       path.endsWith("/api/notifications/balance-zero-alert");
	}
}

