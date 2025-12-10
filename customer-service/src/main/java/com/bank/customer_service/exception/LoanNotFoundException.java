package com.bank.customer_service.exception;

/**
 * Custom exception thrown when a loan is not found
 */
public class LoanNotFoundException extends RuntimeException {

	public LoanNotFoundException(String message) {
		super(message);
	}

	public LoanNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public LoanNotFoundException(Long loanId) {
		super("Loan not found with ID: " + loanId);
	}
}

