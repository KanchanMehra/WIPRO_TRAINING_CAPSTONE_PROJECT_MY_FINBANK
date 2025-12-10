package com.bank.notification_service.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

/**
 * Global Exception Handler for Notification Service
 * Catches all exceptions and returns standardized error responses
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/**
	 * Handle EmailSendFailureException
	 */
	@ExceptionHandler(EmailSendFailureException.class)
	public ResponseEntity<ErrorResponse> handleEmailSendFailureException(
			EmailSendFailureException ex, WebRequest request) {

		logger.error("EmailSendFailureException: {}", ex.getMessage());

		ErrorResponse errorResponse = new ErrorResponse(
				HttpStatus.INTERNAL_SERVER_ERROR.value(),
				"Email Send Failure",
				ex.getMessage(),
				request.getDescription(false).replace("uri=", "")
		);
		errorResponse.addDetail("Failed to send email. Please check mail server configuration.");

		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Handle InvalidEmailException
	 */
	@ExceptionHandler(InvalidEmailException.class)
	public ResponseEntity<ErrorResponse> handleInvalidEmailException(
			InvalidEmailException ex, WebRequest request) {

		logger.error("InvalidEmailException: {}", ex.getMessage());

		ErrorResponse errorResponse = new ErrorResponse(
				HttpStatus.BAD_REQUEST.value(),
				"Bad Request",
				ex.getMessage(),
				request.getDescription(false).replace("uri=", "")
		);
		errorResponse.addDetail("Please provide a valid email address.");

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handle InvalidInputException
	 */
	@ExceptionHandler(InvalidInputException.class)
	public ResponseEntity<ErrorResponse> handleInvalidInputException(
			InvalidInputException ex, WebRequest request) {

		logger.error("InvalidInputException: {}", ex.getMessage());

		ErrorResponse errorResponse = new ErrorResponse(
				HttpStatus.BAD_REQUEST.value(),
				"Bad Request",
				ex.getMessage(),
				request.getDescription(false).replace("uri=", "")
		);
		errorResponse.addDetail("Please provide valid input and try again.");

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handle ServiceUnavailableException
	 */
	@ExceptionHandler(ServiceUnavailableException.class)
	public ResponseEntity<ErrorResponse> handleServiceUnavailableException(
			ServiceUnavailableException ex, WebRequest request) {

		logger.error("ServiceUnavailableException: {}", ex.getMessage());

		ErrorResponse errorResponse = new ErrorResponse(
				HttpStatus.SERVICE_UNAVAILABLE.value(),
				"Service Unavailable",
				ex.getMessage(),
				request.getDescription(false).replace("uri=", "")
		);
		errorResponse.addDetail("The mail service is temporarily unavailable. Please try again later.");

		return new ResponseEntity<>(errorResponse, HttpStatus.SERVICE_UNAVAILABLE);
	}

	/**
	 * Handle all other exceptions (catch-all)
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGlobalException(
			Exception ex, WebRequest request) {

		logger.error("Unexpected error: {}", ex.getMessage(), ex);

		ErrorResponse errorResponse = new ErrorResponse(
				HttpStatus.INTERNAL_SERVER_ERROR.value(),
				"Internal Server Error",
				"An unexpected error occurred",
				request.getDescription(false).replace("uri=", "")
		);
		errorResponse.addDetail("Error: " + ex.getMessage());
		errorResponse.addDetail("Please contact support if the issue persists.");

		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

