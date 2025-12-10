package com.bank.admin_service.controller;

import com.bank.admin_service.dto.CustomerDTO;
import com.bank.admin_service.entity.Admin;
import com.bank.admin_service.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Controller for Admin Customer Management
 * Handles viewing all customers, deactivating, and activating customers
 */
@Controller
@RequestMapping("/admin/customers")
public class AdminCustomerController {

	private final AdminService adminService;

	public AdminCustomerController(AdminService adminService) {
		this.adminService = adminService;
	}

	/**
	 * Show all customers with optional filters
	 */
	@GetMapping("/all")
	public String showAllCustomers(
			@RequestParam(required = false) String status,
			@RequestParam(required = false) String search,
			HttpSession session,
			Model model) {

		// Check if admin is logged in
		Admin admin = (Admin) session.getAttribute("loggedInAdmin");
		if (admin == null) {
			return "redirect:/admin/login";
		}

		List<CustomerDTO> customers;

		// Apply search or filter
		if (search != null && !search.trim().isEmpty()) {
			customers = adminService.searchCustomers(search.trim());
			model.addAttribute("searchTerm", search);
		} else {
			customers = adminService.getAllCustomers(status);
		}

		model.addAttribute("customers", customers);
		model.addAttribute("currentStatus", status);
		model.addAttribute("admin", admin);

		return "admin/all-customers";
	}

	/**
	 * Deactivate a customer
	 */
	@PostMapping("/deactivate/{customerId}")
	public String deactivateCustomer(@PathVariable Long customerId,
	                                 HttpSession session,
	                                 RedirectAttributes redirectAttributes) {
		Admin admin = (Admin) session.getAttribute("loggedInAdmin");
		if (admin == null) {
			return "redirect:/admin/login";
		}

		try {
			CustomerDTO customer = adminService.deactivateCustomer(customerId);
			redirectAttributes.addFlashAttribute("success",
					"Customer " + customer.getFirstName() + " " + customer.getLastName() + " has been deactivated successfully.");
		} catch (RuntimeException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
		}

		return "redirect:/admin/customers/all";
	}

	/**
	 * Activate a customer
	 */
	@PostMapping("/activate/{customerId}")
	public String activateCustomer(@PathVariable Long customerId,
	                               HttpSession session,
	                               RedirectAttributes redirectAttributes) {
		Admin admin = (Admin) session.getAttribute("loggedInAdmin");
		if (admin == null) {
			return "redirect:/admin/login";
		}

		try {
			CustomerDTO customer = adminService.activateCustomer(customerId);
			redirectAttributes.addFlashAttribute("success",
					"Customer " + customer.getFirstName() + " " + customer.getLastName() + " has been activated successfully.");
		} catch (RuntimeException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
		}

		return "redirect:/admin/customers/all";
	}

	/**
	 * Show customer detail page
	 */
	@GetMapping("/detail/{customerId}")
	public String showCustomerDetail(@PathVariable Long customerId,
	                                 HttpSession session,
	                                 Model model) {
		Admin admin = (Admin) session.getAttribute("loggedInAdmin");
		if (admin == null) {
			return "redirect:/admin/login";
		}

		CustomerDTO customer = adminService.getCustomerById(customerId);
		if (customer == null) {
			return "redirect:/admin/customers/all";
		}

		model.addAttribute("customer", customer);
		model.addAttribute("admin", admin);

		return "admin/customer-detail";
	}
}

