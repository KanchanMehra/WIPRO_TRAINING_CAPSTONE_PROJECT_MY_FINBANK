package com.bank.customer_service.controller;

import com.bank.customer_service.entity.Customer;
import com.bank.customer_service.repository.CustomerRepository;
import com.bank.customer_service.security.JwtTokenProvider;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * Authentication controller for customer service
 * Handles login and logout
 */
@Controller
public class CustomerAuthController {

	private final CustomerRepository customerRepository;
	private final JwtTokenProvider jwtTokenProvider;

	public CustomerAuthController(CustomerRepository customerRepository, JwtTokenProvider jwtTokenProvider) {
		this.customerRepository = customerRepository;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	/**
	 * Show customer login form (GET request)
	 */
	@GetMapping("/customer/login")
	public String showLoginForm(Model model) {
		return "customer/login";
	}

	/**
	 * Handle customer login (POST request)
	 */
	@PostMapping("/customer/login")
	public String handleLogin(@RequestParam String username,
	                           @RequestParam String password,
	                           HttpSession session,
	                           Model model) {
		try {
			// Find customer by username
			Optional<Customer> customerOpt = customerRepository.findByUserName(username);

			if (customerOpt.isEmpty()) {
				model.addAttribute("error", "Customer not found");
				return "customer/login";
			}

			Customer customer = customerOpt.get();

			// Validate password
			if (!customer.getPassword().equals(password)) {
				model.addAttribute("error", "Invalid password");
				return "customer/login";
			}

			// Check if customer is active
			if (!"ACTIVE".equalsIgnoreCase(customer.getStatus())) {
				model.addAttribute("error", "Customer account is not active");
				return "customer/login";
			}

			// Generate JWT token
			String token = jwtTokenProvider.generateToken(customer.getId(), "CUSTOMER", customer.getUserName());

			// Set session (for JSP pages to work)
			session.setAttribute("loggedInCustomer", customer);
			session.setAttribute("jwtToken", token);

			// Redirect to dashboard
			return "redirect:/customer/dashboard";

		} catch (Exception e) {
			model.addAttribute("error", "Login failed: " + e.getMessage());
			return "customer/login";
		}
	}

	/**
	 * Handle customer logout
	 */
	@GetMapping("/customer/logout")
	public String handleLogout(HttpSession session) {
		session.invalidate();
		return "redirect:/customer/login";
	}

	/**
	 * Show customer dashboard
	 */
	@GetMapping("/customer/dashboard")
	public String showDashboard(HttpSession session, Model model) {
		Customer customer = (Customer) session.getAttribute("loggedInCustomer");
		if (customer == null) {
			return "redirect:/customer/login";
		}
		// Refresh customer data from database
		Optional<Customer> updatedCustomer = customerRepository.findById(customer.getId());
		if (updatedCustomer.isPresent()) {
			model.addAttribute("customer", updatedCustomer.get());
			session.setAttribute("loggedInCustomer", updatedCustomer.get());
		} else {
			model.addAttribute("customer", customer);
		}
		return "customer/dashboard";
	}
}

