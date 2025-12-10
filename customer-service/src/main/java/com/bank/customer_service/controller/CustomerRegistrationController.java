package com.bank.customer_service.controller;

import com.bank.customer_service.entity.Customer;
import com.bank.customer_service.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customer")
public class CustomerRegistrationController {

	private final CustomerService customerService;

	public CustomerRegistrationController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("customer", new Customer());
		return "customer/register";
	}

	@PostMapping("/register")
	public String handleRegistration(@ModelAttribute("customer") Customer customer, Model model) {
		Customer savedCustomer = customerService.registerCustomer(customer);
		model.addAttribute("customer", savedCustomer);
		return "customer/register-success";
	}
}

