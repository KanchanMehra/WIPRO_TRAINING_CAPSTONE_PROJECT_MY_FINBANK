package com.bank.customer_service.controller;

import com.bank.customer_service.dto.CustomerDTO;
import com.bank.customer_service.dto.EntityDtoMapper;
import com.bank.customer_service.entity.Customer;
import com.bank.customer_service.entity.Loan;
import com.bank.customer_service.entity.Transaction;
import com.bank.customer_service.repository.CustomerRepository;
import com.bank.customer_service.repository.LoanRepository;
import com.bank.customer_service.repository.TransactionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * REST API Controller for Customer Management
 * Used for inter-service communication with admin-service
 * Returns DTOs to avoid exposing sensitive data like passwords
 */
@RestController
@RequestMapping("/api")
public class CustomerRestController {

	private final CustomerRepository customerRepository;
	private final LoanRepository loanRepository;
	private final TransactionRepository transactionRepository;

	public CustomerRestController(CustomerRepository customerRepository,
	                               LoanRepository loanRepository,
	                               TransactionRepository transactionRepository) {
		this.customerRepository = customerRepository;
		this.loanRepository = loanRepository;
		this.transactionRepository = transactionRepository;
	}

	/**
	 * Get all customers with optional status filter
	 * Returns CustomerDTO to exclude password field
	 */
	@GetMapping("/customers")
	public ResponseEntity<List<CustomerDTO>> getAllCustomers(@RequestParam(required = false) String status) {
		List<Customer> customers;

		if (status != null && !status.isEmpty()) {
			customers = customerRepository.findByStatus(status);
		} else {
			customers = customerRepository.findAll();
		}

		// Convert to DTOs (excludes password)
		List<CustomerDTO> customerDTOs = EntityDtoMapper.toCustomerDTOList(customers);
		return ResponseEntity.ok(customerDTOs);
	}

	/**
	 * Get all inactive customers (pending approval)
	 * Returns CustomerDTO to exclude password field
	 */
	@GetMapping("/customers/inactive")
	public ResponseEntity<List<CustomerDTO>> getInactiveCustomers() {
		List<Customer> inactiveCustomers = customerRepository.findByStatus("INACTIVE");

		// Convert to DTOs (excludes password)
		List<CustomerDTO> customerDTOs = EntityDtoMapper.toCustomerDTOList(inactiveCustomers);
		return ResponseEntity.ok(customerDTOs);
	}

	/**
	 * Search customers by keyword - MUST come before /{customerId} to avoid route conflicts
	 * Returns CustomerDTO to exclude password field
	 */
	@GetMapping("/customers/search")
	public ResponseEntity<List<CustomerDTO>> searchCustomers(@RequestParam String keyword) {
		List<Customer> allCustomers = customerRepository.findAll();

		if (keyword == null || keyword.isEmpty()) {
			List<CustomerDTO> customerDTOs = EntityDtoMapper.toCustomerDTOList(allCustomers);
			return ResponseEntity.ok(customerDTOs);
		}

		String lowerKeyword = keyword.toLowerCase();
		List<Customer> filtered = allCustomers.stream()
				.filter(c ->
						c.getFirstName().toLowerCase().contains(lowerKeyword) ||
						c.getLastName().toLowerCase().contains(lowerKeyword) ||
						c.getEmail().toLowerCase().contains(lowerKeyword) ||
						(c.getAccountNo() != null && c.getAccountNo().toLowerCase().contains(lowerKeyword))
				)
				.collect(Collectors.toList());

		// Convert to DTOs (excludes password)
		List<CustomerDTO> customerDTOs = EntityDtoMapper.toCustomerDTOList(filtered);
		return ResponseEntity.ok(customerDTOs);
	}

	/**
	 * Get customer by ID
	 * Returns CustomerDTO to exclude password field
	 */
	@GetMapping("/customers/{customerId}")
	public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long customerId) {
		return customerRepository.findById(customerId)
				.map(customer -> ResponseEntity.ok(EntityDtoMapper.toCustomerDTO(customer)))
				.orElse(ResponseEntity.notFound().build());
	}

	/**
	 * Deactivate a customer
	 * Returns CustomerDTO to exclude password field
	 */
	@PostMapping("/customers/{customerId}/deactivate")
	public ResponseEntity<CustomerDTO> deactivateCustomer(@PathVariable Long customerId) {
		return customerRepository.findById(customerId)
				.map(customer -> {
					if ("INACTIVE".equalsIgnoreCase(customer.getStatus())) {
						return ResponseEntity.badRequest().<CustomerDTO>build();
					}
					customer.setStatus("INACTIVE");
					Customer updated = customerRepository.save(customer);

					// Convert to DTO (excludes password)
					return ResponseEntity.ok(EntityDtoMapper.toCustomerDTO(updated));
				})
				.orElse(ResponseEntity.notFound().build());
	}

	/**
	 * Activate a customer
	 * Returns CustomerDTO to exclude password field
	 */
	@PostMapping("/customers/{customerId}/activate")
	public ResponseEntity<CustomerDTO> activateCustomer(@PathVariable Long customerId) {
		return customerRepository.findById(customerId)
				.map(customer -> {
					if ("ACTIVE".equalsIgnoreCase(customer.getStatus())) {
						return ResponseEntity.badRequest().<CustomerDTO>build();
					}

					// Generate account number if not already generated
					if (customer.getAccountNo() == null || customer.getAccountNo().isEmpty()) {
						String accountNo = generateAccountNumber(customerId);
						customer.setAccountNo(accountNo);
					}

					customer.setStatus("ACTIVE");
					Customer updated = customerRepository.save(customer);

					// Convert to DTO (excludes password)
					return ResponseEntity.ok(EntityDtoMapper.toCustomerDTO(updated));
				})
				.orElse(ResponseEntity.notFound().build());
	}

	/**
	 * Generate account number for customer
	 */
	private String generateAccountNumber(Long customerId) {
		return "ACC" + (1000000 + customerId);
	}

	// ==================== LOAN MANAGEMENT APIs ====================

	/**
	 * Get all pending loans
	 */
	@GetMapping("/loans/pending")
	public ResponseEntity<List<Map<String, Object>>> getPendingLoans() {
		List<Loan> pendingLoans = loanRepository.findByStatus("PENDING");

		// Enrich with customer details
		List<Map<String, Object>> enrichedLoans = pendingLoans.stream()
				.map(loan -> {
					Map<String, Object> loanMap = new HashMap<>();
					loanMap.put("id", loan.getId());
					loanMap.put("customerId", loan.getCustomerId());
					loanMap.put("loanApplicationNo", loan.getLoanApplicationNo());
					loanMap.put("loanAmount", loan.getLoanAmount());
					loanMap.put("durationInMonths", loan.getDurationInMonths());
					loanMap.put("interestRate", loan.getInterestRate());
					loanMap.put("emiAmount", loan.getEmiAmount());
					loanMap.put("totalAmountPayable", loan.getTotalAmountPayable());
					loanMap.put("totalInterest", loan.getTotalInterest());
					loanMap.put("purpose", loan.getPurpose());
					loanMap.put("requiredByDate", loan.getRequiredByDate());
					loanMap.put("remarks", loan.getRemarks());
					loanMap.put("status", loan.getStatus());
					loanMap.put("applicationDate", loan.getApplicationDate());

					// Add customer details
					customerRepository.findById(loan.getCustomerId()).ifPresent(customer -> {
						loanMap.put("customerFirstName", customer.getFirstName());
						loanMap.put("customerLastName", customer.getLastName());
						loanMap.put("customerEmail", customer.getEmail());
						loanMap.put("customerBalance", customer.getAmount());
					});

					return loanMap;
				})
				.collect(Collectors.toList());

		return ResponseEntity.ok(enrichedLoans);
	}

	/**
	 * Approve loan
	 */
	@PostMapping("/loans/{loanId}/approve")
	public ResponseEntity<Map<String, Object>> approveLoan(
			@PathVariable Long loanId,
			@RequestParam(required = false) String adminRemarks) {

		Map<String, Object> response = new HashMap<>();

		try {
			// Get loan
			Loan loan = loanRepository.findById(loanId)
					.orElse(null);

			if (loan == null) {
				response.put("success", false);
				response.put("message", "Loan not found");
				return ResponseEntity.badRequest().body(response);
			}

			// Check if already processed
			if (!"PENDING".equals(loan.getStatus())) {
				response.put("success", false);
				response.put("message", "Loan has already been processed");
				return ResponseEntity.badRequest().body(response);
			}

			// Get customer
			Customer customer = customerRepository.findById(loan.getCustomerId())
					.orElse(null);

			if (customer == null) {
				response.put("success", false);
				response.put("message", "Customer not found");
				return ResponseEntity.badRequest().body(response);
			}

			// Business rule: Check if customer has minimum balance (₹1000)
			if (customer.getAmount().compareTo(BigDecimal.valueOf(1000)) < 0) {
				response.put("success", false);
				response.put("message", "Customer balance is insufficient. Minimum ₹1,000 required.");
				return ResponseEntity.badRequest().body(response);
			}

			// Approve loan
			loan.setStatus("APPROVED");
			loan.setApprovalDate(LocalDateTime.now());
			loan.setAdminRemarks(adminRemarks != null ? adminRemarks : "Loan approved");
			loan.setRemainingEmis(loan.getTotalEmis()); // Set remaining EMIs
			loanRepository.save(loan);

			// Disburse loan amount to customer account
			customer.setAmount(customer.getAmount().add(loan.getLoanAmount()));
			customerRepository.save(customer);

			// Create transaction record
			Transaction transaction = new Transaction();
			transaction.setCustomerId(customer.getId());
			transaction.setType("LOAN_DISBURSEMENT");
			transaction.setAmount(loan.getLoanAmount());
			transaction.setBalanceBefore(customer.getAmount().subtract(loan.getLoanAmount()));
			transaction.setBalanceAfter(customer.getAmount());
			transaction.setTransactionDate(LocalDateTime.now());
			transaction.setTransactionId(generateTransactionId());
			transaction.setStatus("SUCCESS");
			transactionRepository.save(transaction);

			response.put("success", true);
			response.put("message", "Loan approved successfully. Amount ₹" + loan.getLoanAmount() + " disbursed to customer account.");
			response.put("loan", loan);

			return ResponseEntity.ok(response);

		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "Error approving loan: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	/**
	 * Reject loan
	 */
	@PostMapping("/loans/{loanId}/reject")
	public ResponseEntity<Map<String, Object>> rejectLoan(
			@PathVariable Long loanId,
			@RequestParam String adminRemarks) {

		Map<String, Object> response = new HashMap<>();

		try {
			// Get loan
			Loan loan = loanRepository.findById(loanId)
					.orElse(null);

			if (loan == null) {
				response.put("success", false);
				response.put("message", "Loan not found");
				return ResponseEntity.badRequest().body(response);
			}

			// Check if already processed
			if (!"PENDING".equals(loan.getStatus())) {
				response.put("success", false);
				response.put("message", "Loan has already been processed");
				return ResponseEntity.badRequest().body(response);
			}

			// Reject loan
			loan.setStatus("REJECTED");
			loan.setApprovalDate(LocalDateTime.now());
			loan.setAdminRemarks(adminRemarks);
			loanRepository.save(loan);

			response.put("success", true);
			response.put("message", "Loan rejected successfully.");
			response.put("loan", loan);

			return ResponseEntity.ok(response);

		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "Error rejecting loan: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	/**
	 * Generate transaction ID
	 */
	private String generateTransactionId() {
		long count = transactionRepository.count();
		return "TXN" + String.format("%010d", (count + 1));
	}
}
