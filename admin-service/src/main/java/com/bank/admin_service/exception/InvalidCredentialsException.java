package com.bank.admin_service.exception;

/**
 * Custom exception thrown when invalid credentials are provided during login
 */
public class InvalidCredentialsException extends RuntimeException {

	public InvalidCredentialsException(String message) {
		super(message);
	}

	public InvalidCredentialsException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidCredentialsException() {
		super("Invalid username or password. Please try again.");
	}
}

