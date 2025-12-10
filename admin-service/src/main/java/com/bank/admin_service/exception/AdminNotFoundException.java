package com.bank.admin_service.exception;

/**
 * Custom exception thrown when an admin is not found
 */
public class AdminNotFoundException extends RuntimeException {

	public AdminNotFoundException(String message) {
		super(message);
	}

	public AdminNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public AdminNotFoundException(Long adminId) {
		super("Admin not found with ID: " + adminId);
	}

	public AdminNotFoundException(String field, String value) {
		super(String.format("Admin not found with %s: %s", field, value));
	}
}

