package com.bank.customer_service.service;

import com.bank.customer_service.entity.Loan;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Service interface for Loan operations
 */
public interface LoanService {

	/**
	 * Calculate EMI based on loan amount, interest rate, and duration
	 * @param amount Loan amount
	 * @param interestRate Annual interest rate
	 * @param months Duration in months
	 * @return Map with EMI, totalAmount, totalInterest, monthlyBreakdown
	 */
	Map<String, Object> calculateEMI(BigDecimal amount, BigDecimal interestRate, Integer months);

	/**
	 * Get interest rate based on loan amount (tier-based)
	 * @param loanAmount Loan amount
	 * @return Interest rate percentage
	 */
	BigDecimal getInterestRate(BigDecimal loanAmount);

	/**
	 * Apply for a loan
	 * @param customerId Customer ID
	 * @param amount Loan amount
	 * @param months Duration in months
	 * @param purpose Purpose of loan
	 * @param requiredByDate Required by date
	 * @param remarks Additional remarks
	 * @return Created loan
	 */
	Loan applyForLoan(Long customerId, BigDecimal amount, Integer months,
	                   String purpose, LocalDate requiredByDate, String remarks);

	/**
	 * Get all loans for a customer
	 * @param customerId Customer ID
	 * @return List of loans
	 */
	List<Loan> getCustomerLoans(Long customerId);

	/**
	 * Get loan by ID
	 * @param loanId Loan ID
	 * @return Loan object
	 */
	Loan getLoanById(Long loanId);

	/**
	 * Get all pending loans (for admin)
	 * @return List of pending loans
	 */
	List<Loan> getPendingLoans();

	/**
	 * Get loan by application number
	 * @param loanApplicationNo Application number
	 * @return Loan object
	 */
	Loan getLoanByApplicationNo(String loanApplicationNo);
}

