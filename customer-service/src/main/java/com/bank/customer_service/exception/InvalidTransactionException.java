package com.bank.customer_service.exception;

/**
 * Custom exception thrown when a transaction is invalid
 */
public class InvalidTransactionException extends RuntimeException {

	public InvalidTransactionException(String message) {
		super(message);
	}

	public InvalidTransactionException(String message, Throwable cause) {
		super(message, cause);
	}
}

