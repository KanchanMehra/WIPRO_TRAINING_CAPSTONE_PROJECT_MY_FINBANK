package com.bank.customer_service.service;

import com.bank.customer_service.entity.Loan;
import com.bank.customer_service.exception.*;
import com.bank.customer_service.repository.LoanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service implementation for Loan operations
 * Validates all inputs and throws custom exceptions for invalid data
 */
@Service
public class LoanServiceImpl implements LoanService {

	private final LoanRepository loanRepository;

	public LoanServiceImpl(LoanRepository loanRepository) {
		this.loanRepository = loanRepository;
	}

	@Override
	public Map<String, Object> calculateEMI(BigDecimal amount, BigDecimal interestRate, Integer months) {
		// Validate amount
		if (amount == null) {
			throw new InvalidInputException("amount", "cannot be null");
		}
		if (amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new InvalidInputException("amount", amount + " - must be greater than zero");
		}

		// Validate months
		if (months == null || months <= 0) {
			throw new InvalidInputException("months", months + " - must be greater than zero");
		}

		// Validate interest rate
		if (interestRate == null) {
			throw new InvalidInputException("interestRate", "cannot be null");
		}
		if (interestRate.compareTo(BigDecimal.ZERO) < 0) {
			throw new InvalidInputException("interestRate", interestRate + " - must be non-negative");
		}

		try {
			// Convert annual interest rate to monthly rate with proper scale and rounding
			BigDecimal monthlyRate = interestRate
					.divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP)
					.divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);

			// Calculate (1 + r)^n
			BigDecimal rPlusOne = monthlyRate.add(BigDecimal.ONE);
			BigDecimal rPlusOneToN = rPlusOne.pow(months);

			// EMI = P × [r(1+r)^n] / [(1+r)^n - 1]
			BigDecimal numerator = monthlyRate.multiply(rPlusOneToN);
			BigDecimal denominator = rPlusOneToN.subtract(BigDecimal.ONE);

			BigDecimal emi = amount.multiply(numerator)
					.divide(denominator, 2, RoundingMode.HALF_UP);

			BigDecimal totalAmount = emi.multiply(BigDecimal.valueOf(months))
					.setScale(2, RoundingMode.HALF_UP);
			BigDecimal totalInterest = totalAmount.subtract(amount);

			Map<String, Object> result = new HashMap<>();
			result.put("emi", emi);
			result.put("totalAmount", totalAmount);
			result.put("totalInterest", totalInterest);
			result.put("monthlyBreakdown", generateMonthlyBreakdown(amount, monthlyRate, emi, months));

			return result;
		} catch (ArithmeticException e) {
			throw new InvalidInputException("calculation", "Error calculating EMI: " + e.getMessage());
		} catch (Exception e) {
			throw new InvalidInputException("calculation", "Unexpected error: " + e.getMessage());
		}
	}

	@Override
	public BigDecimal getInterestRate(BigDecimal loanAmount) {
		if (loanAmount.compareTo(BigDecimal.valueOf(100000)) < 0) {
			return BigDecimal.valueOf(8); // 8% for < 100,000
		} else if (loanAmount.compareTo(BigDecimal.valueOf(500000)) < 0) {
			return BigDecimal.valueOf(9); // 9% for 100,000 - 500,000
		} else if (loanAmount.compareTo(BigDecimal.valueOf(1000000)) < 0) {
			return BigDecimal.valueOf(10); // 10% for 500,000 - 1,000,000
		} else {
			return BigDecimal.valueOf(11); // 11% for > 1,000,000
		}
	}

	@Override
	@Transactional
	public Loan applyForLoan(Long customerId, BigDecimal amount, Integer months,
	                          String purpose, LocalDate requiredByDate, String remarks) {
		// Validate customerId
		if (customerId == null || customerId <= 0) {
			throw new InvalidInputException("customerId", customerId);
		}

		// Validate amount
		if (amount == null) {
			throw new InvalidInputException("amount", "cannot be null");
		}
		if (amount.compareTo(BigDecimal.ZERO) <= 0 || amount.compareTo(BigDecimal.valueOf(10000000)) > 0) {
			throw new InvalidInputException("amount", amount + " - must be between ₹1 and ₹1,00,00,000");
		}

		// Validate months
		if (months == null || months <= 0 || months > 600) {
			throw new InvalidInputException("months", months + " - must be between 1 and 600 months");
		}

		// Validate purpose
		if (purpose == null || purpose.trim().isEmpty()) {
			throw new InvalidInputException("purpose", "cannot be empty");
		}

		// Get interest rate based on amount
		BigDecimal interestRate = getInterestRate(amount);

		// Calculate EMI
		Map<String, Object> emiDetails = calculateEMI(amount, interestRate, months);
		BigDecimal emiAmount = (BigDecimal) emiDetails.get("emi");
		BigDecimal totalAmountPayable = (BigDecimal) emiDetails.get("totalAmount");
		BigDecimal totalInterest = (BigDecimal) emiDetails.get("totalInterest");

		// Generate loan application number
		String loanApplicationNo = generateLoanApplicationNo();

		// Create loan record
		Loan loan = new Loan(customerId, loanApplicationNo, amount, months, interestRate,
				emiAmount, totalAmountPayable, totalInterest, purpose, requiredByDate, remarks);

		// Initialize EMI tracking
		loan.setTotalEmis(months);
		loan.setRemainingEmis(0); // Will be set to totalEmis when approved
		loan.setPaidEmis(0);

		return loanRepository.save(loan);
	}

	@Override
	public List<Loan> getCustomerLoans(Long customerId) {
		return loanRepository.findByCustomerId(customerId);
	}

	@Override
	public Loan getLoanById(Long loanId) {
		return loanRepository.findById(loanId).orElse(null);
	}

	@Override
	public List<Loan> getPendingLoans() {
		return loanRepository.findByStatus("PENDING");
	}

	@Override
	public Loan getLoanByApplicationNo(String loanApplicationNo) {
		return loanRepository.findByLoanApplicationNo(loanApplicationNo).orElse(null);
	}

	/**
	 * Generate unique loan application number
	 * Format: LOAN + YYYYMMDD + 6-digit sequential
	 */
	private String generateLoanApplicationNo() {
		String datePrefix = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		long count = loanRepository.count();
		String sequentialNo = String.format("%06d", (count + 1) % 1000000);
		return "LOAN" + datePrefix + sequentialNo;
	}

	/**
	 * Generate monthly breakdown for EMI
	 */
	private List<Map<String, Object>> generateMonthlyBreakdown(BigDecimal principal,
	                                                             BigDecimal monthlyRate,
	                                                             BigDecimal emi,
	                                                             Integer months) {
		List<Map<String, Object>> breakdown = new ArrayList<>();
		BigDecimal remainingBalance = principal;

		for (int month = 1; month <= months; month++) {
			Map<String, Object> monthData = new HashMap<>();

			// Interest paid in this month
			BigDecimal interestPaid = remainingBalance.multiply(monthlyRate)
					.setScale(2, RoundingMode.HALF_UP);

			// Principal paid in this month
			BigDecimal principalPaid = emi.subtract(interestPaid)
					.setScale(2, RoundingMode.HALF_UP);

			// Remaining balance
			remainingBalance = remainingBalance.subtract(principalPaid);
			if (remainingBalance.compareTo(BigDecimal.ZERO) < 0) {
				remainingBalance = BigDecimal.ZERO;
			}

			monthData.put("month", month);
			monthData.put("principalPaid", principalPaid);
			monthData.put("interestPaid", interestPaid);
			monthData.put("emi", emi);
			monthData.put("balanceRemaining", remainingBalance);

			breakdown.add(monthData);
		}

		return breakdown;
	}
}

