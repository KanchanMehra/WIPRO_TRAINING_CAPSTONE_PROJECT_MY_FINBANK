package com.bank.admin_service.controller;

import com.bank.admin_service.entity.Admin;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller for Admin Loan Management
 * Handles loan approval and rejection
 */
@Controller
@RequestMapping("/admin/loans")
public class AdminLoanController {

	private final RestTemplate restTemplate;
	private static final String CUSTOMER_SERVICE_URL = "http://localhost:8081/api";

	public AdminLoanController(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	/**
	 * Show all pending loans
	 */
	@GetMapping("/pending")
	public String showPendingLoans(HttpSession session, Model model) {
		// Check if admin is logged in
		Admin admin = (Admin) session.getAttribute("loggedInAdmin");
		if (admin == null) {
			return "redirect:/admin/login";
		}

		try {
			String url = CUSTOMER_SERVICE_URL + "/loans/pending";
			ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
					url,
					HttpMethod.GET,
					null,
					new ParameterizedTypeReference<List<Map<String, Object>>>() {}
			);

			List<Map<String, Object>> loans = response.getBody();
			model.addAttribute("loans", loans);
			model.addAttribute("admin", admin);
			return "admin/pending-loans";

		} catch (Exception e) {
			model.addAttribute("error", "Error loading pending loans: " + e.getMessage());
			model.addAttribute("loans", List.of());
			return "admin/pending-loans";
		}
	}

	/**
	 * Show loan approval page
	 */
	@GetMapping("/approve/{loanId}")
	public String showLoanApprovalPage(@PathVariable Long loanId,
	                                   HttpSession session,
	                                   Model model) {
		Admin admin = (Admin) session.getAttribute("loggedInAdmin");
		if (admin == null) {
			return "redirect:/admin/login";
		}

		try {
			// Get all pending loans and find the specific one
			String url = CUSTOMER_SERVICE_URL + "/loans/pending";
			ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
					url,
					HttpMethod.GET,
					null,
					new ParameterizedTypeReference<List<Map<String, Object>>>() {}
			);

			List<Map<String, Object>> loans = response.getBody();
			Map<String, Object> loan = loans.stream()
					.filter(l -> l.get("id").toString().equals(loanId.toString()))
					.findFirst()
					.orElse(null);

			if (loan == null) {
				return "redirect:/admin/loans/pending";
			}

			model.addAttribute("loan", loan);
			model.addAttribute("admin", admin);
			return "admin/loan-approval";

		} catch (Exception e) {
			return "redirect:/admin/loans/pending";
		}
	}

	/**
	 * Approve loan
	 */
	@PostMapping("/approve/{loanId}")
	public String approveLoan(@PathVariable Long loanId,
	                          @RequestParam(required = false) String adminRemarks,
	                          HttpSession session,
	                          RedirectAttributes redirectAttributes) {
		Admin admin = (Admin) session.getAttribute("loggedInAdmin");
		if (admin == null) {
			return "redirect:/admin/login";
		}

		try {
			String url = CUSTOMER_SERVICE_URL + "/loans/" + loanId + "/approve";
			if (adminRemarks != null && !adminRemarks.trim().isEmpty()) {
				url += "?adminRemarks=" + adminRemarks;
			}

			ResponseEntity<Map> response = restTemplate.postForEntity(url, null, Map.class);
			Map<String, Object> result = response.getBody();

			if (result != null && Boolean.TRUE.equals(result.get("success"))) {
				redirectAttributes.addFlashAttribute("success", result.get("message"));
			} else {
				redirectAttributes.addFlashAttribute("error", result != null ? result.get("message") : "Failed to approve loan");
			}

		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Error approving loan: " + e.getMessage());
		}

		return "redirect:/admin/loans/pending";
	}

	/**
	 * Reject loan
	 */
	@PostMapping("/reject/{loanId}")
	public String rejectLoan(@PathVariable Long loanId,
	                         @RequestParam String adminRemarks,
	                         HttpSession session,
	                         RedirectAttributes redirectAttributes) {
		Admin admin = (Admin) session.getAttribute("loggedInAdmin");
		if (admin == null) {
			return "redirect:/admin/login";
		}

		if (adminRemarks == null || adminRemarks.trim().isEmpty()) {
			redirectAttributes.addFlashAttribute("error", "Please provide a reason for rejection");
			return "redirect:/admin/loans/approve/" + loanId;
		}

		try {
			String url = CUSTOMER_SERVICE_URL + "/loans/" + loanId + "/reject?adminRemarks=" + adminRemarks;
			ResponseEntity<Map> response = restTemplate.postForEntity(url, null, Map.class);
			Map<String, Object> result = response.getBody();

			if (result != null && Boolean.TRUE.equals(result.get("success"))) {
				redirectAttributes.addFlashAttribute("success", result.get("message"));
			} else {
				redirectAttributes.addFlashAttribute("error", result != null ? result.get("message") : "Failed to reject loan");
			}

		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Error rejecting loan: " + e.getMessage());
		}

		return "redirect:/admin/loans/pending";
	}
}

