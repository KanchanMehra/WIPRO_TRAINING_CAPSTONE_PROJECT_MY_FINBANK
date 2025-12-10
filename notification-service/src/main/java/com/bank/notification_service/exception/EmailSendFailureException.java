package com.bank.notification_service.exception;

/**
 * Custom exception thrown when email sending fails
 */
public class EmailSendFailureException extends RuntimeException {

	public EmailSendFailureException(String message) {
		super(message);
	}

	public EmailSendFailureException(String message, Throwable cause) {
		super(message, cause);
	}
}

