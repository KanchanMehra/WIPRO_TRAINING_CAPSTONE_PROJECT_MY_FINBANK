package com.bank.customer_service.controller;

import com.bank.customer_service.entity.Customer;
import com.bank.customer_service.entity.Transaction;
import com.bank.customer_service.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

/**
 * Controller for Customer Withdrawal Operations
 */
@Controller
@RequestMapping("/customer/withdraw")
public class WithdrawController {

	private final CustomerService customerService;

	public WithdrawController(CustomerService customerService) {
		this.customerService = customerService;
	}

	/**
	 * Handle withdrawal request (POST only)
	 * GET request handled by CustomerViewController
	 */
	@PostMapping
	public String handleWithdraw(@RequestParam("amount") String amountStr,
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
				return "customer/withdraw";
			}

			// Process withdrawal (validates amount, customer status, balance, etc.)
			Transaction transaction = customerService.withdrawMoney(customer.getId(), amount);

			// Get updated customer with new balance
			Customer updatedCustomer = customerService.getCustomerById(customer.getId());

			// Update session with new balance
			session.setAttribute("loggedInCustomer", updatedCustomer);

			// Pass transaction and customer details to success page
			model.addAttribute("transaction", transaction);
			model.addAttribute("customer", updatedCustomer);

			return "customer/withdraw-success";

		} catch (RuntimeException e) {
			// Handle validation errors (insufficient balance, account not active, etc.)
			model.addAttribute("error", e.getMessage());
			Customer updatedCustomer = customerService.getCustomerById(customer.getId());
			model.addAttribute("customer", updatedCustomer);
			return "customer/withdraw";
		}
	}
}

