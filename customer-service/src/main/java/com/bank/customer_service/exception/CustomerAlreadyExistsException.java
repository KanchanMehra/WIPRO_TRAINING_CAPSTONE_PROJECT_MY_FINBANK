package com.bank.customer_service.exception;

/**
 * Custom exception thrown when trying to register a customer that already exists
 */
public class CustomerAlreadyExistsException extends RuntimeException {

	public CustomerAlreadyExistsException(String username) {
		super("Customer already exists with username: " + username);
	}

	public CustomerAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}
}

