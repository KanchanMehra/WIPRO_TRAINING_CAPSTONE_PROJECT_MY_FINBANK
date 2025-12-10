package com.bank.customer_service.controller;

import com.bank.customer_service.dto.EntityDtoMapper;
import com.bank.customer_service.dto.TransactionDTO;
import com.bank.customer_service.entity.Transaction;
import com.bank.customer_service.exception.InvalidInputException;
import com.bank.customer_service.repository.TransactionRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * REST API Controller for Transaction queries
 * Used for inter-service communication and customer transaction history
 * Returns DTOs instead of entities for better security and decoupling
 * Throws custom exceptions for invalid inputs - handled by GlobalExceptionHandler
 */
@RestController
@RequestMapping("/api/transactions")
public class TransactionRestController {

	private final TransactionRepository transactionRepository;

	public TransactionRestController(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	/**
	 * Get all transactions for a customer with optional filters
	 * Returns TransactionDTO to decouple entity from API
	 * Throws InvalidInputException if customerId is invalid
	 */
	@GetMapping("/customer/{customerId}")
	public ResponseEntity<List<TransactionDTO>> getCustomerTransactions(
			@PathVariable Long customerId,
			@RequestParam(required = false) String type,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {

		// Validate customer ID
		if (customerId == null || customerId <= 0) {
			throw new InvalidInputException("customerId", customerId);
		}

		List<Transaction> transactions;

		// Apply filters based on parameters
		if (type != null && !type.isEmpty()) {
			// Filter by type
			if (fromDate != null && toDate != null) {
				// Filter by type and date range
				LocalDateTime fromDateTime = fromDate.atStartOfDay();
				LocalDateTime toDateTime = toDate.atTime(LocalTime.MAX);
				transactions = transactionRepository
						.findByCustomerIdAndTypeAndTransactionDateBetweenOrderByTransactionDateDesc(
								customerId, type, fromDateTime, toDateTime);
			} else {
				// Filter by type only
				transactions = transactionRepository
						.findByCustomerIdAndTypeOrderByTransactionDateDesc(customerId, type);
			}
		} else {
			// No type filter
			if (fromDate != null && toDate != null) {
				// Filter by date range only
				LocalDateTime fromDateTime = fromDate.atStartOfDay();
				LocalDateTime toDateTime = toDate.atTime(LocalTime.MAX);
				transactions = transactionRepository
						.findByCustomerIdAndTransactionDateBetweenOrderByTransactionDateDesc(
								customerId, fromDateTime, toDateTime);
			} else {
				// Get all transactions
				transactions = transactionRepository.findByCustomerIdOrderByTransactionDateDesc(customerId);
			}
		}

		// Convert to DTOs
		List<TransactionDTO> transactionDTOs = EntityDtoMapper.toTransactionDTOList(transactions);
		return ResponseEntity.ok(transactionDTOs);
	}

	/**
	 * Get single transaction by ID
	 * Returns TransactionDTO to decouple entity from API
	 */
	@GetMapping("/{transactionId}")
	public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable String transactionId) {
		Optional<Transaction> transaction = transactionRepository.findByTransactionId(transactionId);
		if (transaction.isPresent()) {
			// Convert to DTO
			TransactionDTO transactionDTO = EntityDtoMapper.toTransactionDTO(transaction.get());
			return ResponseEntity.ok(transactionDTO);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	/**
	 * Get transaction summary for a customer
	 * Returns: total count, deposits, withdrawals, transfers, net change
	 * Throws InvalidInputException if customerId is invalid
	 */
	@GetMapping("/customer/{customerId}/summary")
	public ResponseEntity<Map<String, Object>> getTransactionSummary(@PathVariable Long customerId) {
		// Validate customer ID
		if (customerId == null || customerId <= 0) {
			throw new InvalidInputException("customerId", customerId);
		}

		List<Transaction> allTransactions = transactionRepository.findByCustomerIdOrderByTransactionDateDesc(customerId);

		Map<String, Object> summary = new HashMap<>();

		// Count total transactions
		summary.put("totalTransactions", allTransactions.size());

		// Calculate totals by type
		java.math.BigDecimal totalDeposits = allTransactions.stream()
				.filter(t -> "DEPOSIT".equals(t.getType()))
				.map(Transaction::getAmount)
				.reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);

		java.math.BigDecimal totalWithdrawals = allTransactions.stream()
				.filter(t -> "WITHDRAW".equals(t.getType()))
				.map(Transaction::getAmount)
				.reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);

		java.math.BigDecimal totalTransfersOut = allTransactions.stream()
				.filter(t -> "TRANSFER_OUT".equals(t.getType()))
				.map(Transaction::getAmount)
				.reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);

		java.math.BigDecimal totalTransfersIn = allTransactions.stream()
				.filter(t -> "TRANSFER_IN".equals(t.getType()))
				.map(Transaction::getAmount)
				.reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);

		summary.put("totalDeposits", totalDeposits);
		summary.put("totalWithdrawals", totalWithdrawals);
		summary.put("totalTransfersOut", totalTransfersOut);
		summary.put("totalTransfersIn", totalTransfersIn);

		// Calculate net change: deposits - withdrawals - transfers_out + transfers_in
		java.math.BigDecimal netChange = totalDeposits
				.subtract(totalWithdrawals)
				.subtract(totalTransfersOut)
				.add(totalTransfersIn);

		summary.put("netChange", netChange);

		return ResponseEntity.ok(summary);
	}

	/**
	 * Get transactions count by type
	 */
	@GetMapping("/customer/{customerId}/count-by-type")
	public ResponseEntity<Map<String, Long>> getTransactionCountByType(@PathVariable Long customerId) {
		List<Transaction> allTransactions = transactionRepository.findByCustomerIdOrderByTransactionDateDesc(customerId);

		Map<String, Long> counts = new HashMap<>();
		counts.put("DEPOSIT", allTransactions.stream().filter(t -> "DEPOSIT".equals(t.getType())).count());
		counts.put("WITHDRAW", allTransactions.stream().filter(t -> "WITHDRAW".equals(t.getType())).count());
		counts.put("TRANSFER_OUT", allTransactions.stream().filter(t -> "TRANSFER_OUT".equals(t.getType())).count());
		counts.put("TRANSFER_IN", allTransactions.stream().filter(t -> "TRANSFER_IN".equals(t.getType())).count());

		return ResponseEntity.ok(counts);
	}
}

