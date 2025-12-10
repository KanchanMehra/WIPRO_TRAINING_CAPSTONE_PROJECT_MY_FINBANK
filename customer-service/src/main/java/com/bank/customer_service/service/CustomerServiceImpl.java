package com.bank.customer_service.service;

import com.bank.customer_service.entity.Customer;
import com.bank.customer_service.entity.Transaction;
import com.bank.customer_service.exception.*;
import com.bank.customer_service.repository.CustomerRepository;
import com.bank.customer_service.repository.TransactionRepository;
import com.bank.customer_service.util.NotificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Service implementation for Customer operations
 * Validates all inputs and throws custom exceptions for invalid data
 */
@Service
public class CustomerServiceImpl implements CustomerService {

	// ...existing code...

	private final CustomerRepository customerRepository;
	private final TransactionRepository transactionRepository;
	private final NotificationService notificationService;
	private static final AtomicLong accountNumberSequence = new AtomicLong(1000000);
	private static final AtomicLong transactionSequence = new AtomicLong(0);

	public CustomerServiceImpl(CustomerRepository customerRepository,
	                           TransactionRepository transactionRepository,
	                           NotificationService notificationService) {
		this.customerRepository = customerRepository;
		this.transactionRepository = transactionRepository;
		this.notificationService = notificationService;
	}

	@Override
	@Transactional
	public Customer registerCustomer(Customer customer) {
		// Ensure technical fields are controlled by the system
		customer.setId(null);
		customer.setAccountNo(null);
		customer.setStatus("INACTIVE");
		customer.setDateOfOpen(LocalDate.now());

		return customerRepository.save(customer);
	}

	@Override
	public List<Customer> getInactiveCustomers() {
		return customerRepository.findByStatus("INACTIVE");
	}

	@Override
	@Transactional
	public Customer activateCustomer(Long customerId) {
		// Validate customerId
		if (customerId == null || customerId <= 0) {
			throw new InvalidInputException("customerId", customerId);
		}

		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new CustomerNotFoundException(customerId));

		if ("ACTIVE".equalsIgnoreCase(customer.getStatus())) {
			throw new InvalidInputException("Customer status", "already active");
		}

		// Generate unique account number
		String accountNo = generateAccountNumber();
		customer.setAccountNo(accountNo);
		customer.setStatus("ACTIVE");

		return customerRepository.save(customer);
	}

	@Override
	public Customer getCustomerById(Long customerId) {
		// Validate customerId
		if (customerId == null || customerId <= 0) {
			throw new InvalidInputException("customerId", customerId);
		}

		return customerRepository.findById(customerId)
				.orElseThrow(() -> new CustomerNotFoundException(customerId));
	}

	/**
	 * Generate a unique account number in format: ACC + 7-digit number
	 * Example: ACC1000001, ACC1000002, etc.
	 */
	private String generateAccountNumber() {
		long sequenceNumber = accountNumberSequence.incrementAndGet();
		return String.format("ACC%07d", sequenceNumber);
	}

	@Override
	public Customer validateLogin(String userName, String password) {
		// Validate input
		if (userName == null || userName.trim().isEmpty()) {
			throw new InvalidInputException("userName", userName);
		}
		if (password == null || password.trim().isEmpty()) {
			throw new InvalidInputException("password", "cannot be empty");
		}

		// Find customer by username
		Optional<Customer> customerOpt = customerRepository.findByUserName(userName);

		if (customerOpt.isEmpty()) {
			throw new CustomerNotFoundException(userName);
		}

		Customer customer = customerOpt.get();

		// Check password
		if (!customer.getPassword().equals(password)) {
			throw new InvalidCredentialsException();
		}

		// Check if customer is ACTIVE
		if (!"ACTIVE".equalsIgnoreCase(customer.getStatus())) {
			throw new InvalidTransactionException("Your account is not active. Please wait for admin approval.");
		}

		// Check if account number exists (customer must be activated by admin)
		if (customer.getAccountNo() == null || customer.getAccountNo().isEmpty()) {
			throw new InvalidTransactionException("Your account has not been activated yet by admin.");
		}

		return customer; // Valid login
	}

	@Override
	@Transactional
	public Transaction depositMoney(Long customerId, BigDecimal amount) {
		// Validate customerId
		if (customerId == null || customerId <= 0) {
			throw new InvalidInputException("customerId", customerId);
		}

		// Validate amount
		if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new InvalidInputException("amount", amount + " - must be greater than zero");
		}

		// Get customer
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new CustomerNotFoundException(customerId));

		// Check customer is active
		if (!"ACTIVE".equalsIgnoreCase(customer.getStatus())) {
			throw new InvalidTransactionException("Your account is not active. Cannot perform deposit.");
		}

		// Check customer has account number (has been activated by admin)
		if (customer.getAccountNo() == null || customer.getAccountNo().isEmpty()) {
			throw new InvalidTransactionException("Your account has not been activated yet. Cannot perform deposit.");
		}

		// Get balance before deposit
		BigDecimal balanceBefore = customer.getAmount();

		// Calculate new balance
		BigDecimal newBalance = balanceBefore.add(amount);

		// Update customer balance
		customer.setAmount(newBalance);
		customerRepository.save(customer);

		// Create transaction record
		String transactionId = generateTransactionId();
		Transaction transaction = Transaction.builder()
				.transactionId(transactionId)
				.customerId(customerId)
				.type("DEPOSIT")
				.amount(amount)
				.balanceBefore(balanceBefore)
				.balanceAfter(newBalance)
				.transactionDate(LocalDateTime.now())
				.status("SUCCESS")
				.remarks("Deposit to account")
				.build();

		return transactionRepository.save(transaction);
	}

	/**
	 * Generate a unique transaction ID in format: TXN + 10-digit number
	 * Example: TXN0000000001, TXN0000000002, etc.
	 */
	private String generateTransactionId() {
		long sequence = transactionSequence.incrementAndGet();
		return String.format("TXN%010d", sequence);
	}

	@Override
	@Transactional
	public Transaction withdrawMoney(Long customerId, BigDecimal amount) {
		// Validate customerId
		if (customerId == null || customerId <= 0) {
			throw new InvalidInputException("customerId", customerId);
		}

		// Validate amount
		if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new InvalidInputException("amount", amount + " - must be greater than zero");
		}

		// Get customer
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new CustomerNotFoundException(customerId));

		// Check customer is active
		if (!"ACTIVE".equalsIgnoreCase(customer.getStatus())) {
			throw new InvalidTransactionException("Your account is not active. Cannot perform withdrawal.");
		}

		// Check customer has account number (has been activated by admin)
		if (customer.getAccountNo() == null || customer.getAccountNo().isEmpty()) {
			throw new InvalidTransactionException("Your account has not been activated yet. Cannot perform withdrawal.");
		}

		// Get balance before withdrawal
		BigDecimal balanceBefore = customer.getAmount();

		// CRITICAL: Check sufficient balance
		if (balanceBefore.compareTo(amount) < 0) {
			throw new InsufficientBalanceException(balanceBefore, amount);
		}

		// Calculate new balance
		BigDecimal newBalance = balanceBefore.subtract(amount);

		// Update customer balance
		customer.setAmount(newBalance);
		customerRepository.save(customer);

		// Create transaction record
		String transactionId = generateTransactionId();
		Transaction transaction = Transaction.builder()
				.transactionId(transactionId)
				.customerId(customerId)
				.type("WITHDRAW")
				.amount(amount)
				.balanceBefore(balanceBefore)
				.balanceAfter(newBalance)
				.transactionDate(LocalDateTime.now())
				.status("SUCCESS")
				.remarks("Withdrawal from account")
				.build();

		transactionRepository.save(transaction);

		// Send notification if balance reaches zero
		notificationService.sendBalanceZeroAlert(customer, "WITHDRAW", balanceBefore);

		return transaction;
	}

	@Override
	@Transactional
	public Map<String, Object> transferMoney(Long senderCustomerId, String targetAccountNo, BigDecimal amount) {
		// Validate customerId
		if (senderCustomerId == null || senderCustomerId <= 0) {
			throw new InvalidInputException("senderCustomerId", senderCustomerId);
		}

		// 1. Validate amount
		if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new InvalidInputException("amount", amount + " - must be greater than zero");
		}

		// Validate target account
		if (targetAccountNo == null || targetAccountNo.trim().isEmpty()) {
			throw new InvalidInputException("targetAccountNo", targetAccountNo);
		}

		// 2. Get sender
		Customer sender = customerRepository.findById(senderCustomerId)
				.orElseThrow(() -> new CustomerNotFoundException(senderCustomerId));

		// 3. Validate sender status
		if (!"ACTIVE".equalsIgnoreCase(sender.getStatus())) {
			throw new InvalidTransactionException("Your account is not active. Cannot perform transfer.");
		}

		if (sender.getAccountNo() == null || sender.getAccountNo().isEmpty()) {
			throw new InvalidTransactionException("Your account has not been activated. Cannot perform transfer.");
		}

		// 4. Validate sender balance
		BigDecimal senderBalance = sender.getAmount();
		if (senderBalance.compareTo(amount) < 0) {
			throw new InsufficientBalanceException(senderBalance, amount);
		}

		// 5. Find target account (case-insensitive)
		Customer receiver = customerRepository.findByAccountNo(targetAccountNo.trim().toUpperCase())
				.orElseThrow(() -> new InvalidTransactionException("Account number " + targetAccountNo + " does not exist"));

		// 6. Validate target account
		if (!"ACTIVE".equalsIgnoreCase(receiver.getStatus())) {
			throw new InvalidTransactionException("Target account is not active. Cannot transfer to this account.");
		}

		if (receiver.getAccountNo() == null || receiver.getAccountNo().isEmpty()) {
			throw new InvalidTransactionException("Target account is not properly activated.");
		}

		// 7. Check not transferring to self
		if (sender.getId().equals(receiver.getId())) {
			throw new InvalidTransactionException("Cannot transfer money to your own account");
		}

		// 8. ATOMIC: Calculate new balances
		BigDecimal newSenderBalance = senderBalance.subtract(amount);
		BigDecimal oldReceiverBalance = receiver.getAmount();
		BigDecimal newReceiverBalance = oldReceiverBalance.add(amount);

		// 9. Update both balances in database
		sender.setAmount(newSenderBalance);
		receiver.setAmount(newReceiverBalance);

		customerRepository.save(sender);
		customerRepository.save(receiver);

		// 10. Create two transaction records (sender and receiver)
		String senderTransactionId = generateTransactionId();
		String receiverTransactionId = generateTransactionId();

		Transaction senderTransaction = Transaction.builder()
				.transactionId(senderTransactionId)
				.customerId(sender.getId())
				.type("TRANSFER_OUT")
				.amount(amount)
				.balanceBefore(senderBalance)
				.balanceAfter(newSenderBalance)
				.transactionDate(LocalDateTime.now())
				.status("SUCCESS")
				.remarks("Transfer to " + receiver.getAccountNo() + " (" + receiver.getFirstName() + " " + receiver.getLastName() + ")")
				.build();

		Transaction receiverTransaction = Transaction.builder()
				.transactionId(receiverTransactionId)
				.customerId(receiver.getId())
				.type("TRANSFER_IN")
				.amount(amount)
				.balanceBefore(oldReceiverBalance)
				.balanceAfter(newReceiverBalance)
				.transactionDate(LocalDateTime.now())
				.status("SUCCESS")
				.remarks("Transfer from " + sender.getAccountNo() + " (" + sender.getFirstName() + " " + sender.getLastName() + ")")
				.build();

		transactionRepository.save(senderTransaction);
		transactionRepository.save(receiverTransaction);

		// Send notification if sender balance reaches zero
		notificationService.sendBalanceZeroAlert(sender, "TRANSFER", senderBalance);

		// 12. Return success details
		Map<String, Object> result = new HashMap<>();
		result.put("senderTransactionId", senderTransactionId);
		result.put("receiverTransactionId", receiverTransactionId);
		result.put("sender", sender);
		result.put("receiver", receiver);
		result.put("amount", amount);
		result.put("timestamp", LocalDateTime.now());

		return result;
	}
}

