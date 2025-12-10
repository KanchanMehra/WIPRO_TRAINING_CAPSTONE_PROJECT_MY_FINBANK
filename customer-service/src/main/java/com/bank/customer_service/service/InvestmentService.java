package com.bank.customer_service.service;

import com.bank.customer_service.entity.FixedDeposit;
import com.bank.customer_service.entity.Loan;
import com.bank.customer_service.entity.RecurringDeposit;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service interface for Investment operations (RD, FD, Loan EMI)
 */
public interface InvestmentService {

	// ==================== Recurring Deposit ====================

	/**
	 * Create recurring deposit
	 */
	RecurringDeposit createRecurringDeposit(Long customerId, BigDecimal monthlyInstallment, Integer durationInMonths);

	/**
	 * Get customer's RDs
	 */
	List<RecurringDeposit> getCustomerRDs(Long customerId);

	/**
	 * Calculate RD maturity amount
	 */
	BigDecimal calculateRDMaturityAmount(BigDecimal monthlyInstallment, Integer durationInMonths, BigDecimal interestRate);

	/**
	 * Get RD interest rate based on duration
	 */
	BigDecimal getRDInterestRate(Integer durationInMonths);

	// ==================== Fixed Deposit ====================

	/**
	 * Create fixed deposit
	 */
	FixedDeposit createFixedDeposit(Long customerId, BigDecimal principalAmount, Integer durationInMonths);

	/**
	 * Get customer's FDs
	 */
	List<FixedDeposit> getCustomerFDs(Long customerId);

	/**
	 * Calculate FD maturity amount
	 */
	BigDecimal calculateFDMaturityAmount(BigDecimal principalAmount, Integer durationInMonths, BigDecimal interestRate);

	/**
	 * Get FD interest rate based on duration
	 */
	BigDecimal getFDInterestRate(Integer durationInMonths);

	// ==================== Loan EMI Payment ====================

	/**
	 * Pay loan EMI
	 */
	Loan payLoanEMI(Long customerId, Long loanId);

	/**
	 * Get customer's approved loans
	 */
	List<Loan> getCustomerApprovedLoans(Long customerId);

	/**
	 * Get loan by ID
	 */
	Loan getLoanById(Long loanId);
}

