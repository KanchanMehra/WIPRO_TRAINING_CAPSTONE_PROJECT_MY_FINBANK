package com.bank.admin_service.exception;

/**
 * Custom exception thrown when a customer is not found
 */
public class CustomerNotFoundException extends RuntimeException {

	public CustomerNotFoundException(String message) {
		super(message);
	}

	public CustomerNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public CustomerNotFoundException(Long customerId) {
		super("Customer not found with ID: " + customerId);
	}

	public CustomerNotFoundException(String field, String value) {
		super(String.format("Customer not found with %s: %s", field, value));
	}
}

