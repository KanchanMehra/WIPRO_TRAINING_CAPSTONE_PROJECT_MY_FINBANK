package com.bank.customer_service.controller;

import com.bank.customer_service.entity.Customer;
import com.bank.customer_service.entity.Loan;
import com.bank.customer_service.service.LoanService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Controller for Loan operations
 * Handles EMI calculator and loan applications
 */
@Controller
@RequestMapping("/customer/loan")
public class LoanController {

	private final LoanService loanService;

	public LoanController(LoanService loanService) {
		this.loanService = loanService;
	}

	/**
	 * Show EMI calculator page
	 */
	@GetMapping("/calculator")
	public String showCalculator(HttpSession session) {
		// Check if customer is logged in
		Customer loggedInCustomer = (Customer) session.getAttribute("loggedInCustomer");
		if (loggedInCustomer == null) {
			return "redirect:/customer/login";
		}
		return "customer/calculator";
	}

	/**
	 * Calculate EMI
	 */
	@PostMapping("/calculate")
	@ResponseBody
	public Map<String, Object> calculateEMI(@RequestParam BigDecimal amount,
	                                         @RequestParam BigDecimal rate,
	                                         @RequestParam Integer months,
	                                         HttpSession session) {
		// Check if customer is logged in
		Customer loggedInCustomer = (Customer) session.getAttribute("loggedInCustomer");
		if (loggedInCustomer == null) {
			throw new RuntimeException("Customer not logged in");
		}

		try {
			return loanService.calculateEMI(amount, rate, months);
		} catch (Exception e) {
			throw new RuntimeException("Error calculating EMI: " + e.getMessage());
		}
	}

	/**
	 * Show loan application form
	 */
	@GetMapping("/apply")
	public String showLoanApplication(HttpSession session, Model model) {
		// Check if customer is logged in
		Customer loggedInCustomer = (Customer) session.getAttribute("loggedInCustomer");
		if (loggedInCustomer == null) {
			return "redirect:/customer/login";
		}

		model.addAttribute("customer", loggedInCustomer);
		return "customer/apply-loan";
	}

	/**
	 * Submit loan application
	 */
	@PostMapping("/apply")
	public String submitLoanApplication(@RequestParam BigDecimal amount,
	                                    @RequestParam Integer months,
	                                    @RequestParam String purpose,
	                                    @RequestParam(required = false) LocalDate requiredByDate,
	                                    @RequestParam(required = false) String remarks,
	                                    HttpSession session,
	                                    Model model) {
		// Check if customer is logged in
		Customer loggedInCustomer = (Customer) session.getAttribute("loggedInCustomer");
		if (loggedInCustomer == null) {
			return "redirect:/customer/login";
		}

		try {
			// Validate inputs
			if (amount.compareTo(BigDecimal.ZERO) <= 0) {
				model.addAttribute("error", "Loan amount must be greater than zero");
				return "customer/apply-loan";
			}

			if (months <= 0) {
				model.addAttribute("error", "Duration must be greater than zero");
				return "customer/apply-loan";
			}

			// Apply for loan
			Loan loan = loanService.applyForLoan(loggedInCustomer.getId(), amount, months,
					purpose, requiredByDate, remarks);

			model.addAttribute("loan", loan);
			model.addAttribute("customer", loggedInCustomer);
			return "customer/loan-success";

		} catch (Exception e) {
			model.addAttribute("error", "Error applying for loan: " + e.getMessage());
			return "customer/apply-loan";
		}
	}

	/**
	 * Show customer's loan applications
	 */
	@GetMapping("/my-loans")
	public String showMyLoans(HttpSession session, Model model) {
		// Check if customer is logged in
		Customer loggedInCustomer = (Customer) session.getAttribute("loggedInCustomer");
		if (loggedInCustomer == null) {
			return "redirect:/customer/login";
		}

		try {
			List<Loan> loans = loanService.getCustomerLoans(loggedInCustomer.getId());
			model.addAttribute("loans", loans);
			model.addAttribute("customer", loggedInCustomer);
			return "customer/my-loans";
		} catch (Exception e) {
			model.addAttribute("error", "Error loading loans: " + e.getMessage());
			model.addAttribute("loans", List.of());
			return "customer/my-loans";
		}
	}

	/**
	 * Show loan details
	 */
	@GetMapping("/view/{loanId}")
	public String viewLoanDetails(@PathVariable Long loanId,
	                              HttpSession session,
	                              Model model) {
		// Check if customer is logged in
		Customer loggedInCustomer = (Customer) session.getAttribute("loggedInCustomer");
		if (loggedInCustomer == null) {
			return "redirect:/customer/login";
		}

		try {
			Loan loan = loanService.getLoanById(loanId);
			if (loan == null || !loan.getCustomerId().equals(loggedInCustomer.getId())) {
				return "redirect:/customer/loan/my-loans";
			}

			model.addAttribute("loan", loan);
			model.addAttribute("customer", loggedInCustomer);
			return "customer/loan-detail";
		} catch (Exception e) {
			return "redirect:/customer/loan/my-loans";
		}
	}
}

