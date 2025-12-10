package com.bank.customer_service.exception;

/**
 * Custom exception thrown when a customer is not found
 */
public class CustomerNotFoundException extends RuntimeException {

	public CustomerNotFoundException(String username) {
		super("Customer not found with username: " + username);
	}

	public CustomerNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public CustomerNotFoundException(Long customerId) {
		super("Customer not found with ID: " + customerId);
	}
}

