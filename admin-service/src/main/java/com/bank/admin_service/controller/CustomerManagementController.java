package com.bank.admin_service.controller;

import com.bank.admin_service.dto.CustomerDTO;
import com.bank.admin_service.entity.Admin;
import com.bank.admin_service.service.CustomerManagementService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Controller for Admin to manage customers
 * - View inactive (pending) customers
 * - Activate customers (generate account number)
 */
@Controller
@RequestMapping("/admin/customers")
public class CustomerManagementController {

	private final CustomerManagementService customerManagementService;

	public CustomerManagementController(CustomerManagementService customerManagementService) {
		this.customerManagementService = customerManagementService;
	}

	/**
	 * Show all inactive customers waiting for approval
	 */
	@GetMapping("/pending")
	public String showPendingCustomers(HttpSession session, Model model) {
		// Check if admin is logged in
		Admin loggedInAdmin = (Admin) session.getAttribute("loggedInAdmin");
		if (loggedInAdmin == null) {
			return "redirect:/admin/login";
		}

		try {
			List<CustomerDTO> inactiveCustomers = customerManagementService.getInactiveCustomers();
			model.addAttribute("customers", inactiveCustomers);
			model.addAttribute("customerCount", inactiveCustomers.size());
			return "admin/pending-customers";
		} catch (Exception e) {
			model.addAttribute("error", "Unable to fetch customer data: " + e.getMessage());
			model.addAttribute("customers", List.of());
			model.addAttribute("customerCount", 0);
			return "admin/pending-customers";
		}
	}

	/**
	 * Activate a customer (generates account number and sets status to ACTIVE)
	 */
	@PostMapping("/activate")
	public String activateCustomer(@RequestParam("customerId") Long customerId,
	                                HttpSession session,
	                                RedirectAttributes redirectAttributes) {
		// Check if admin is logged in
		Admin loggedInAdmin = (Admin) session.getAttribute("loggedInAdmin");
		if (loggedInAdmin == null) {
			return "redirect:/admin/login";
		}

		try {
			CustomerDTO activatedCustomer = customerManagementService.activateCustomer(customerId);
			redirectAttributes.addFlashAttribute("successMessage",
					"Customer '" + activatedCustomer.getFirstName() + " " + activatedCustomer.getLastName() +
					"' has been activated successfully. Account Number: " + activatedCustomer.getAccountNo());
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMessage",
					"Failed to activate customer: " + e.getMessage());
		}

		return "redirect:/admin/customers/pending";
	}
}

