package com.bank.customer_service.controller;

import com.bank.customer_service.entity.Customer;
import com.bank.customer_service.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Map;

/**
 * Controller for Customer Fund Transfer Operations
 */
@Controller
@RequestMapping("/customer/transfer")
public class TransferController {

	private final CustomerService customerService;

	public TransferController(CustomerService customerService) {
		this.customerService = customerService;
	}

	/**
	 * Handle transfer request (POST only)
	 * GET request handled by CustomerViewController
	 */
	@PostMapping
	public String handleTransfer(@RequestParam("targetAccountNo") String targetAccountNo,
	                             @RequestParam("amount") String amountStr,
	                             HttpSession session,
	                             Model model) {
		Customer customer = (Customer) session.getAttribute("loggedInCustomer");
		if (customer == null) {
			return "redirect:/customer/login";
		}

		try {
			// Parse amount
			BigDecimal amount;
			try {
				amount = new BigDecimal(amountStr);
			} catch (NumberFormatException e) {
				model.addAttribute("error", "Invalid amount. Please enter a valid number.");
				Customer updatedCustomer = customerService.getCustomerById(customer.getId());
				model.addAttribute("customer", updatedCustomer);
				return "customer/transfer";
			}

			// Process transfer (validates everything)
			Map<String, Object> result = customerService.transferMoney(
					customer.getId(),
					targetAccountNo.trim().toUpperCase(),
					amount
			);

			// Get updated sender
			Customer updatedCustomer = customerService.getCustomerById(customer.getId());

			// Update session with new balance
			session.setAttribute("loggedInCustomer", updatedCustomer);

			// Pass all details to success page
			model.addAttribute("result", result);
			model.addAttribute("customer", updatedCustomer);

			return "customer/transfer-success";

		} catch (RuntimeException e) {
			// Handle validation errors
			model.addAttribute("error", e.getMessage());
			Customer updatedCustomer = customerService.getCustomerById(customer.getId());
			model.addAttribute("customer", updatedCustomer);
			return "customer/transfer";
		}
	}
}

