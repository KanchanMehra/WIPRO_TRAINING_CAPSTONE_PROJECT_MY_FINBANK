package com.bank.customer_service.controller;

import com.bank.customer_service.entity.Customer;
import com.bank.customer_service.entity.FixedDeposit;
import com.bank.customer_service.entity.Loan;
import com.bank.customer_service.entity.RecurringDeposit;
import com.bank.customer_service.service.InvestmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

/**
 * Controller for Investment operations (RD, FD, Loan EMI)
 */
@Controller
@RequestMapping("/customer/investment")
public class InvestmentController {

	private final InvestmentService investmentService;

	public InvestmentController(InvestmentService investmentService) {
		this.investmentService = investmentService;
	}

	// ==================== Recurring Deposit ====================

	@GetMapping("/rd/create")
	public String showCreateRD(HttpSession session, Model model) {
		Customer customer = (Customer) session.getAttribute("loggedInCustomer");
		if (customer == null) {
			return "redirect:/customer/login";
		}
		model.addAttribute("customer", customer);
		return "customer/create-rd";
	}

	@PostMapping("/rd/create")
	public String createRD(@RequestParam BigDecimal monthlyInstallment,
	                       @RequestParam Integer durationInMonths,
	                       HttpSession session,
	                       Model model) {
		Customer customer = (Customer) session.getAttribute("loggedInCustomer");
		if (customer == null) {
			return "redirect:/customer/login";
		}

		try {
			RecurringDeposit rd = investmentService.createRecurringDeposit(
					customer.getId(), monthlyInstallment, durationInMonths);

			model.addAttribute("rd", rd);
			model.addAttribute("customer", customer);
			model.addAttribute("type", "RD");
			return "customer/investment-success";

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			model.addAttribute("customer", customer);
			return "customer/create-rd";
		}
	}

	@GetMapping("/rd/my-rds")
	public String showMyRDs(HttpSession session, Model model) {
		Customer customer = (Customer) session.getAttribute("loggedInCustomer");
		if (customer == null) {
			return "redirect:/customer/login";
		}

		List<RecurringDeposit> rds = investmentService.getCustomerRDs(customer.getId());
		model.addAttribute("rds", rds);
		model.addAttribute("customer", customer);
		return "customer/my-rd";
	}

	// ==================== Fixed Deposit ====================

	@GetMapping("/fd/create")
	public String showCreateFD(HttpSession session, Model model) {
		Customer customer = (Customer) session.getAttribute("loggedInCustomer");
		if (customer == null) {
			return "redirect:/customer/login";
		}
		model.addAttribute("customer", customer);
		return "customer/create-fd";
	}

	@PostMapping("/fd/create")
	public String createFD(@RequestParam BigDecimal principalAmount,
	                       @RequestParam Integer durationInMonths,
	                       HttpSession session,
	                       Model model) {
		Customer customer = (Customer) session.getAttribute("loggedInCustomer");
		if (customer == null) {
			return "redirect:/customer/login";
		}

		try {
			FixedDeposit fd = investmentService.createFixedDeposit(
					customer.getId(), principalAmount, durationInMonths);

			model.addAttribute("fd", fd);
			model.addAttribute("customer", customer);
			model.addAttribute("type", "FD");
			return "customer/investment-success";

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			model.addAttribute("customer", customer);
			return "customer/create-fd";
		}
	}

	@GetMapping("/fd/my-fds")
	public String showMyFDs(HttpSession session, Model model) {
		Customer customer = (Customer) session.getAttribute("loggedInCustomer");
		if (customer == null) {
			return "redirect:/customer/login";
		}

		List<FixedDeposit> fds = investmentService.getCustomerFDs(customer.getId());
		model.addAttribute("fds", fds);
		model.addAttribute("customer", customer);
		return "customer/my-fd";
	}

	// ==================== Loan EMI Payment ====================

	@GetMapping("/emi/pay")
	public String showPayEMI(HttpSession session, Model model) {
		Customer customer = (Customer) session.getAttribute("loggedInCustomer");
		if (customer == null) {
			return "redirect:/customer/login";
		}

		List<Loan> loans = investmentService.getCustomerApprovedLoans(customer.getId());
		model.addAttribute("loans", loans);
		model.addAttribute("customer", customer);
		return "customer/pay-emi";
	}

	@PostMapping("/emi/pay/{loanId}")
	public String payEMI(@PathVariable Long loanId,
	                     HttpSession session,
	                     RedirectAttributes redirectAttributes) {
		Customer customer = (Customer) session.getAttribute("loggedInCustomer");
		if (customer == null) {
			return "redirect:/customer/login";
		}

		try {
			Loan loan = investmentService.payLoanEMI(customer.getId(), loanId);

			String message = "EMI of ₹" + loan.getEmiAmount() + " paid successfully!";
			if (loan.getStatus().equals("CLOSED")) {
				message += " Congratulations! Your loan is now fully paid and closed.";
			} else {
				message += " Remaining EMIs: " + loan.getRemainingEmis();
			}

			redirectAttributes.addFlashAttribute("success", message);

		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
		}

		return "redirect:/customer/investment/emi/pay";
	}

	// ==================== AJAX Endpoints for Calculations ====================

	@GetMapping("/rd/calculate")
	@ResponseBody
	public java.util.Map<String, Object> calculateRD(@RequestParam BigDecimal monthlyInstallment,
	                                                   @RequestParam Integer durationInMonths) {
		try {
			BigDecimal interestRate = investmentService.getRDInterestRate(durationInMonths);
			BigDecimal maturityAmount = investmentService.calculateRDMaturityAmount(
					monthlyInstallment, durationInMonths, interestRate);

			BigDecimal totalDeposited = monthlyInstallment.multiply(BigDecimal.valueOf(durationInMonths));
			BigDecimal totalInterest = maturityAmount.subtract(totalDeposited);

			java.util.Map<String, Object> result = new java.util.HashMap<>();
			result.put("interestRate", interestRate);
			result.put("maturityAmount", maturityAmount);
			result.put("totalDeposited", totalDeposited);
			result.put("totalInterest", totalInterest);

			return result;
		} catch (Exception e) {
			java.util.Map<String, Object> error = new java.util.HashMap<>();
			error.put("error", e.getMessage());
			return error;
		}
	}

	@GetMapping("/fd/calculate")
	@ResponseBody
	public java.util.Map<String, Object> calculateFD(@RequestParam BigDecimal principalAmount,
	                                                   @RequestParam Integer durationInMonths) {
		try {
			BigDecimal interestRate = investmentService.getFDInterestRate(durationInMonths);
			BigDecimal maturityAmount = investmentService.calculateFDMaturityAmount(
					principalAmount, durationInMonths, interestRate);

			BigDecimal totalInterest = maturityAmount.subtract(principalAmount);

			java.util.Map<String, Object> result = new java.util.HashMap<>();
			result.put("interestRate", interestRate);
			result.put("maturityAmount", maturityAmount);
			result.put("totalInterest", totalInterest);

			return result;
		} catch (Exception e) {
			java.util.Map<String, Object> error = new java.util.HashMap<>();
			error.put("error", e.getMessage());
			return error;
		}
	}
}

