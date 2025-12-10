package com.bank.customer_service.exception;

/**
 * Custom exception thrown when a dependent service is unavailable
 */
public class ServiceUnavailableException extends RuntimeException {

	public ServiceUnavailableException(String serviceName) {
		super(serviceName + " service is currently unavailable. Please try again later.");
	}

	public ServiceUnavailableException(String message, Throwable cause) {
		super(message, cause);
	}
}

