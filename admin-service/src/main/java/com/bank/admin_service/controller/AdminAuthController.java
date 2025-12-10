package com.bank.admin_service.controller;

import com.bank.admin_service.entity.Admin;
import com.bank.admin_service.exception.*;
import com.bank.admin_service.repository.AdminRepository;
import com.bank.admin_service.security.JwtTokenProvider;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * Authentication controller for admin service
 * Handles login and logout
 */
@Controller
public class AdminAuthController {

	private final AdminRepository adminRepository;
	private final JwtTokenProvider jwtTokenProvider;

	public AdminAuthController(AdminRepository adminRepository, JwtTokenProvider jwtTokenProvider) {
		this.adminRepository = adminRepository;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	/**
	 * Show admin login form (GET request)
	 */
	@GetMapping("/admin/login")
	public String showLoginForm(Model model) {
		return "admin/login";
	}

	/**
	 * Handle admin login (POST request)
	 */
	@PostMapping("/admin/login")
	public String handleLogin(@RequestParam String username,
	                           @RequestParam String password,
	                           HttpSession session,
	                           Model model) {
		// Validate input
		if (username == null || username.trim().isEmpty()) {
			throw new InvalidInputException("username", username);
		}
		if (password == null || password.trim().isEmpty()) {
			throw new InvalidInputException("password", "empty");
		}

		// Find admin by username - throws AdminNotFoundException if not found
		Admin admin = adminRepository.findByAdminUserName(username)
				.orElseThrow(() -> new AdminNotFoundException("username", username));

		// Validate password - throws InvalidCredentialsException if incorrect
		if (!admin.getAdminPassword().equals(password)) {
			throw new InvalidCredentialsException();
		}

		// Check if admin is active
		if (!"ACTIVE".equalsIgnoreCase(admin.getAdminStatus())) {
			throw new InvalidInputException("adminStatus", admin.getAdminStatus() + " - Admin account is not active");
		}

		// Generate JWT token
		String token = jwtTokenProvider.generateToken(admin.getAdminId(), "ADMIN", admin.getAdminUserName());

		// Set session (for JSP pages to work)
		session.setAttribute("loggedInAdmin", admin);
		session.setAttribute("jwtToken", token);

		// Redirect to dashboard
		return "redirect:/admin/dashboard";
	}

	/**
	 * Handle admin logout
	 */
	@GetMapping("/admin/logout")
	public String handleLogout(HttpSession session) {
		session.invalidate();
		return "redirect:/admin/login";
	}

	/**
	 * Show admin dashboard
	 */
	@GetMapping("/admin/dashboard")
	public String showDashboard(HttpSession session, Model model) {
		Admin admin = (Admin) session.getAttribute("loggedInAdmin");
		if (admin == null) {
			return "redirect:/admin/login";
		}
		model.addAttribute("admin", admin);
		return "admin/dashboard";
	}
}

