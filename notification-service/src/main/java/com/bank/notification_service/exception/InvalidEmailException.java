package com.bank.notification_service.exception;

/**
 * Custom exception thrown when email address is invalid
 */
public class InvalidEmailException extends RuntimeException {

	public InvalidEmailException(String message) {
		super(message);
	}

	public InvalidEmailException(String message, Throwable cause) {
		super(message, cause);
	}
}

