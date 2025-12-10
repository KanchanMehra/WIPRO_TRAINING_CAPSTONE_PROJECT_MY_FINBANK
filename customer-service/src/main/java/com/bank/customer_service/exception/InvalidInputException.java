package com.bank.customer_service.exception;

/**
 * Custom exception thrown when invalid input is provided
 */
public class InvalidInputException extends RuntimeException {

	public InvalidInputException(String message) {
		super(message);
	}

	public InvalidInputException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidInputException(String field, Object value) {
		super(String.format("Invalid %s: %s", field, value));
	}
}

