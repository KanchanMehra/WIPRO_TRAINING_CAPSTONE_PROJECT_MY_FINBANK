package com.bank.admin_service.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Global Exception Handler for Admin Service
 * Catches all exceptions and returns standardized error responses
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/**
	 * Handle AdminNotFoundException
	 */
	@ExceptionHandler(AdminNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleAdminNotFoundException(
			AdminNotFoundException ex, WebRequest request) {

		logger.error("AdminNotFoundException: {}", ex.getMessage());

		ErrorResponse errorResponse = new ErrorResponse(
				HttpStatus.NOT_FOUND.value(),
				"Not Found",
				ex.getMessage(),
				request.getDescription(false).replace("uri=", "")
		);
		errorResponse.addDetail("Please verify the admin ID or username and try again.");

		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	/**
	 * Handle AdminAlreadyExistsException
	 */
	@ExceptionHandler(AdminAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleAdminAlreadyExistsException(
			AdminAlreadyExistsException ex, WebRequest request) {

		logger.error("AdminAlreadyExistsException: {}", ex.getMessage());

		ErrorResponse errorResponse = new ErrorResponse(
				HttpStatus.CONFLICT.value(),
				"Conflict",
				ex.getMessage(),
				request.getDescription(false).replace("uri=", "")
		);
		errorResponse.addDetail("Please use a different username.");

		return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
	}

	/**
	 * Handle InvalidCredentialsException
	 */
	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<ErrorResponse> handleInvalidCredentialsException(
			InvalidCredentialsException ex, WebRequest request) {

		logger.error("InvalidCredentialsException: {}", ex.getMessage());

		ErrorResponse errorResponse = new ErrorResponse(
				HttpStatus.UNAUTHORIZED.value(),
				"Unauthorized",
				ex.getMessage(),
				request.getDescription(false).replace("uri=", "")
		);
		errorResponse.addDetail("Please check your credentials and try again.");

		return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
	}

	/**
	 * Handle CustomerNotFoundException
	 */
	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleCustomerNotFoundException(
			CustomerNotFoundException ex, WebRequest request) {

		logger.error("CustomerNotFoundException: {}", ex.getMessage());

		ErrorResponse errorResponse = new ErrorResponse(
				HttpStatus.NOT_FOUND.value(),
				"Not Found",
				ex.getMessage(),
				request.getDescription(false).replace("uri=", "")
		);
		errorResponse.addDetail("The requested customer does not exist.");

		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
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
		errorResponse.addDetail("The service is temporarily unavailable. Please try again later.");

		return new ResponseEntity<>(errorResponse, HttpStatus.SERVICE_UNAVAILABLE);
	}

	/**
	 * Handle validation errors (from @Valid annotation)
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationExceptions(
			MethodArgumentNotValidException ex, WebRequest request) {

		logger.error("Validation error: {}", ex.getMessage());

		List<String> details = new ArrayList<>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			details.add(error.getField() + ": " + error.getDefaultMessage());
		}

		ErrorResponse errorResponse = new ErrorResponse(
				HttpStatus.BAD_REQUEST.value(),
				"Validation Failed",
				"Input validation failed",
				request.getDescription(false).replace("uri=", ""),
				details
		);

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handle NullPointerException
	 */
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<ErrorResponse> handleNullPointerException(
			NullPointerException ex, WebRequest request) {

		logger.error("NullPointerException: {}", ex.getMessage(), ex);

		ErrorResponse errorResponse = new ErrorResponse(
				HttpStatus.INTERNAL_SERVER_ERROR.value(),
				"Internal Server Error",
				"A null value was encountered unexpectedly",
				request.getDescription(false).replace("uri=", "")
		);
		errorResponse.addDetail("Please contact support if the issue persists.");

		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Handle IllegalArgumentException
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
			IllegalArgumentException ex, WebRequest request) {

		logger.error("IllegalArgumentException: {}", ex.getMessage());

		ErrorResponse errorResponse = new ErrorResponse(
				HttpStatus.BAD_REQUEST.value(),
				"Bad Request",
				ex.getMessage(),
				request.getDescription(false).replace("uri=", "")
		);
		errorResponse.addDetail("Please provide valid input values.");

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
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

