package com.bank.customer_service.exception;

/**
 * Custom exception thrown when customer has insufficient balance for a transaction
 */
public class InsufficientBalanceException extends RuntimeException {

	public InsufficientBalanceException(String message) {
		super(message);
	}

	public InsufficientBalanceException(String message, Throwable cause) {
		super(message, cause);
	}

	public InsufficientBalanceException(java.math.BigDecimal currentBalance, java.math.BigDecimal requiredAmount) {
		super("Insufficient balance. Current balance: " + currentBalance + ", Required amount: " + requiredAmount);
	}
}

