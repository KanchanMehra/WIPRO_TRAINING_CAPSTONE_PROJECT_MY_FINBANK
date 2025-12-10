package com.bank.customer_service.controller;

import com.bank.customer_service.entity.Customer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * Controller for customer chat UI
 */
@Controller
@RequestMapping("/customer/chat")
public class CustomerChatViewController {

	@GetMapping
	public String showCustomerChat(HttpSession session, Model model) {
		Customer customer = (Customer) session.getAttribute("loggedInCustomer");
		if (customer == null) {
			return "redirect:/customer/login";
		}

		model.addAttribute("customer", customer);
		model.addAttribute("roomId", "room-" + customer.getId());
		return "customer/customer-chat";
	}
}

