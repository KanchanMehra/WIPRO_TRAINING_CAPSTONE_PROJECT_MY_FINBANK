package com.bank.admin_service.exception;

/**
 * Custom exception thrown when trying to register an admin that already exists
 */
public class AdminAlreadyExistsException extends RuntimeException {

	public AdminAlreadyExistsException(String username) {
		super("Admin already exists with username: " + username);
	}

	public AdminAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}
}

