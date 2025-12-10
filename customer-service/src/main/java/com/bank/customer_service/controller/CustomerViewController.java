package com.bank.customer_service.controller;

import com.bank.customer_service.entity.Customer;
import com.bank.customer_service.entity.Transaction;
import com.bank.customer_service.repository.CustomerRepository;
import com.bank.customer_service.repository.TransactionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

/**
 * Controller for displaying customer transaction pages (JSP views)
 */
@Controller
@RequestMapping("/customer")
public class CustomerViewController {

	private final CustomerRepository customerRepository;
	private final TransactionRepository transactionRepository;

	public CustomerViewController(CustomerRepository customerRepository,
	                               TransactionRepository transactionRepository) {
		this.customerRepository = customerRepository;
		this.transactionRepository = transactionRepository;
	}

	/**
	 * Show customer transactions page
	 */
	@GetMapping("/transactions")
	public String showTransactions(HttpSession session, Model model) {
		Customer customer = (Customer) session.getAttribute("loggedInCustomer");
		if (customer == null) {
			return "redirect:/customer/login";
		}

		// Refresh customer data
		Optional<Customer> updatedCustomer = customerRepository.findById(customer.getId());
		if (updatedCustomer.isPresent()) {
			customer = updatedCustomer.get();
			session.setAttribute("loggedInCustomer", customer);
		}

		// Get transactions for this customer
		List<Transaction> transactions = transactionRepository.findByCustomerIdOrderByTransactionDateDesc(customer.getId());

		model.addAttribute("customer", customer);
		model.addAttribute("transactions", transactions);

		return "customer/transactions";
	}

	/**
	 * Show deposit page
	 */
	@GetMapping("/deposit")
	public String showDeposit(HttpSession session, Model model) {
		Customer customer = (Customer) session.getAttribute("loggedInCustomer");
		if (customer == null) {
			return "redirect:/customer/login";
		}

		Optional<Customer> updatedCustomer = customerRepository.findById(customer.getId());
		if (updatedCustomer.isPresent()) {
			model.addAttribute("customer", updatedCustomer.get());
		} else {
			model.addAttribute("customer", customer);
		}

		return "customer/deposit";
	}

	/**
	 * Show withdraw page
	 */
	@GetMapping("/withdraw")
	public String showWithdraw(HttpSession session, Model model) {
		Customer customer = (Customer) session.getAttribute("loggedInCustomer");
		if (customer == null) {
			return "redirect:/customer/login";
		}

		Optional<Customer> updatedCustomer = customerRepository.findById(customer.getId());
		if (updatedCustomer.isPresent()) {
			model.addAttribute("customer", updatedCustomer.get());
		} else {
			model.addAttribute("customer", customer);
		}

		return "customer/withdraw";
	}

	/**
	 * Show transfer page
	 */
	@GetMapping("/transfer")
	public String showTransfer(HttpSession session, Model model) {
		Customer customer = (Customer) session.getAttribute("loggedInCustomer");
		if (customer == null) {
			return "redirect:/customer/login";
		}

		Optional<Customer> updatedCustomer = customerRepository.findById(customer.getId());
		if (updatedCustomer.isPresent()) {
			model.addAttribute("customer", updatedCustomer.get());
		} else {
			model.addAttribute("customer", customer);
		}

		return "customer/transfer";
	}
}

