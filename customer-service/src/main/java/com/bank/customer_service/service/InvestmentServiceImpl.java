package com.bank.customer_service.service;

import com.bank.customer_service.entity.*;
import com.bank.customer_service.exception.*;
import com.bank.customer_service.repository.*;
import com.bank.customer_service.util.NotificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Service implementation for Investment operations
 * Validates all inputs and throws custom exceptions for invalid data
 */
@Service
public class InvestmentServiceImpl implements InvestmentService {

	private final RecurringDepositRepository rdRepository;
	private final FixedDepositRepository fdRepository;
	private final LoanRepository loanRepository;
	private final CustomerRepository customerRepository;
	private final TransactionRepository transactionRepository;
	private final NotificationService notificationService;

	public InvestmentServiceImpl(RecurringDepositRepository rdRepository,
	                              FixedDepositRepository fdRepository,
	                              LoanRepository loanRepository,
	                              CustomerRepository customerRepository,
	                              TransactionRepository transactionRepository,
	                              NotificationService notificationService) {
		this.rdRepository = rdRepository;
		this.fdRepository = fdRepository;
		this.loanRepository = loanRepository;
		this.customerRepository = customerRepository;
		this.transactionRepository = transactionRepository;
		this.notificationService = notificationService;
	}

	// ==================== Recurring Deposit ====================

	@Override
	@Transactional
	public RecurringDeposit createRecurringDeposit(Long customerId, BigDecimal monthlyInstallment, Integer durationInMonths) {
		// Validate customerId
		if (customerId == null || customerId <= 0) {
			throw new InvalidInputException("customerId", customerId);
		}

		// Validate monthly installment
		if (monthlyInstallment == null) {
			throw new InvalidInputException("monthlyInstallment", "cannot be null");
		}
		if (monthlyInstallment.compareTo(BigDecimal.valueOf(500)) < 0 ||
		    monthlyInstallment.compareTo(BigDecimal.valueOf(50000)) > 0) {
			throw new InvalidInputException("monthlyInstallment", monthlyInstallment + " - must be between ₹500 and ₹50,000");
		}

		// Validate duration
		if (durationInMonths == null || durationInMonths < 6 || durationInMonths > 120) {
			throw new InvalidInputException("durationInMonths", durationInMonths + " - must be between 6 and 120 months");
		}

		// Get customer
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new CustomerNotFoundException(customerId));

		// Check balance
		if (customer.getAmount().compareTo(monthlyInstallment) < 0) {
			throw new InsufficientBalanceException(customer.getAmount(), monthlyInstallment);
		}

		// Calculate interest rate and maturity amount
		BigDecimal interestRate = getRDInterestRate(durationInMonths);
		BigDecimal maturityAmount = calculateRDMaturityAmount(monthlyInstallment, durationInMonths, interestRate);

		// Generate RD account number
		String rdAccountNo = generateRDAccountNo();

		// Calculate dates
		LocalDate startDate = LocalDate.now();
		LocalDate maturityDate = startDate.plusMonths(durationInMonths);

		// Create RD
		RecurringDeposit rd = new RecurringDeposit(customerId, rdAccountNo, monthlyInstallment,
				durationInMonths, interestRate, maturityAmount, startDate, maturityDate);

		// Deduct first installment from customer balance
		BigDecimal balanceBefore = customer.getAmount();
		customer.setAmount(customer.getAmount().subtract(monthlyInstallment));
		customerRepository.save(customer);

		// Create transaction
		createTransaction(customerId, "RD_OPENING", monthlyInstallment,
				balanceBefore, customer.getAmount());

		// Send notification if balance reaches zero
		notificationService.sendBalanceZeroAlert(customer, "RD_CREATION", balanceBefore);

		return rdRepository.save(rd);
	}

	@Override
	public List<RecurringDeposit> getCustomerRDs(Long customerId) {
		return rdRepository.findByCustomerId(customerId);
	}

	@Override
	public BigDecimal calculateRDMaturityAmount(BigDecimal monthlyInstallment, Integer durationInMonths, BigDecimal interestRate) {
		// Simple Interest Formula for RD
		// Total Deposited = P × n
		// Interest = P × n × (n + 1) / 2 × (rate/100) / 12
		// Maturity = Total Deposited + Interest

		BigDecimal totalDeposited = monthlyInstallment.multiply(BigDecimal.valueOf(durationInMonths));

		// Calculate interest
		BigDecimal n = BigDecimal.valueOf(durationInMonths);
		BigDecimal interest = monthlyInstallment
				.multiply(n)
				.multiply(n.add(BigDecimal.ONE))
				.divide(BigDecimal.valueOf(2), 10, RoundingMode.HALF_UP)
				.multiply(interestRate.divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP))
				.divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP);

		return totalDeposited.add(interest);
	}

	@Override
	public BigDecimal getRDInterestRate(Integer durationInMonths) {
		if (durationInMonths < 13) {
			return BigDecimal.valueOf(6.5); // 6-12 months: 6.5%
		} else if (durationInMonths <= 36) {
			return BigDecimal.valueOf(7.0); // 13-36 months: 7%
		} else {
			return BigDecimal.valueOf(7.5); // 37+ months: 7.5%
		}
	}

	// ==================== Fixed Deposit ====================

	@Override
	@Transactional
	public FixedDeposit createFixedDeposit(Long customerId, BigDecimal principalAmount, Integer durationInMonths) {
		// Validate customerId
		if (customerId == null || customerId <= 0) {
			throw new InvalidInputException("customerId", customerId);
		}

		// Validate principal amount
		if (principalAmount == null) {
			throw new InvalidInputException("principalAmount", "cannot be null");
		}
		if (principalAmount.compareTo(BigDecimal.valueOf(1000)) < 0 ||
		    principalAmount.compareTo(BigDecimal.valueOf(1000000)) > 0) {
			throw new InvalidInputException("principalAmount", principalAmount + " - must be between ₹1,000 and ₹10,00,000");
		}

		// Validate duration
		if (durationInMonths == null || durationInMonths < 1 || durationInMonths > 120) {
			throw new InvalidInputException("durationInMonths", durationInMonths + " - must be between 1 and 120 months");
		}

		// Get customer
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new CustomerNotFoundException(customerId));

		// Check balance
		if (customer.getAmount().compareTo(principalAmount) < 0) {
			throw new InsufficientBalanceException(customer.getAmount(), principalAmount);
		}

		// Calculate interest rate and maturity amount
		BigDecimal interestRate = getFDInterestRate(durationInMonths);
		BigDecimal maturityAmount = calculateFDMaturityAmount(principalAmount, durationInMonths, interestRate);

		// Generate FD account number
		String fdAccountNo = generateFDAccountNo();

		// Calculate dates
		LocalDate startDate = LocalDate.now();
		LocalDate maturityDate = startDate.plusMonths(durationInMonths);

		// Create FD
		FixedDeposit fd = new FixedDeposit(customerId, fdAccountNo, principalAmount,
				durationInMonths, interestRate, maturityAmount, startDate, maturityDate);

		// Deduct principal from customer balance
		BigDecimal balanceBefore = customer.getAmount();
		customer.setAmount(customer.getAmount().subtract(principalAmount));
		customerRepository.save(customer);

		// Create transaction
		createTransaction(customerId, "FD_OPENING", principalAmount,
				balanceBefore, customer.getAmount());

		// Send notification if balance reaches zero
		notificationService.sendBalanceZeroAlert(customer, "FD_CREATION", balanceBefore);

		return fdRepository.save(fd);
	}

	@Override
	public List<FixedDeposit> getCustomerFDs(Long customerId) {
		return fdRepository.findByCustomerId(customerId);
	}

	@Override
	public BigDecimal calculateFDMaturityAmount(BigDecimal principalAmount, Integer durationInMonths, BigDecimal interestRate) {
		// Simple Interest Formula: A = P + (P × r × t / 100)
		// Where: P = Principal, r = rate, t = time in years

		BigDecimal timeInYears = BigDecimal.valueOf(durationInMonths)
				.divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);

		BigDecimal interest = principalAmount
				.multiply(interestRate)
				.multiply(timeInYears)
				.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

		return principalAmount.add(interest);
	}

	@Override
	public BigDecimal getFDInterestRate(Integer durationInMonths) {
		if (durationInMonths < 6) {
			return BigDecimal.valueOf(5.0); // < 6 months: 5%
		} else if (durationInMonths < 13) {
			return BigDecimal.valueOf(6.0); // 6-12 months: 6%
		} else if (durationInMonths <= 36) {
			return BigDecimal.valueOf(7.0); // 13-36 months: 7%
		} else {
			return BigDecimal.valueOf(7.5); // 37+ months: 7.5%
		}
	}

	// ==================== Loan EMI Payment ====================

	@Override
	@Transactional
	public Loan payLoanEMI(Long customerId, Long loanId) {
		// Validate input
		if (customerId == null || customerId <= 0) {
			throw new InvalidInputException("customerId", customerId);
		}
		if (loanId == null || loanId <= 0) {
			throw new InvalidInputException("loanId", loanId);
		}

		// Get loan
		Loan loan = loanRepository.findById(loanId)
				.orElseThrow(() -> new LoanNotFoundException(loanId));

		// Validate customer owns this loan
		if (!loan.getCustomerId().equals(customerId)) {
			throw new InvalidTransactionException("Unauthorized: Loan does not belong to this customer");
		}

		// Validate loan status
		if (!"APPROVED".equals(loan.getStatus())) {
			throw new InvalidTransactionException("Loan is not approved. Current status: " + loan.getStatus());
		}

		// Check if already fully paid
		if (loan.getRemainingEmis() == null || loan.getRemainingEmis() <= 0) {
			throw new InvalidTransactionException("Loan is already fully paid");
		}

		// Get customer
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new CustomerNotFoundException(customerId));

		// Check balance
		if (customer.getAmount().compareTo(loan.getEmiAmount()) < 0) {
			throw new InsufficientBalanceException(customer.getAmount(), loan.getEmiAmount());
		}

		// Deduct EMI from balance
		BigDecimal balanceBefore = customer.getAmount();
		customer.setAmount(customer.getAmount().subtract(loan.getEmiAmount()));
		customerRepository.save(customer);

		// Update loan EMI tracking
		loan.setPaidEmis(loan.getPaidEmis() + 1);
		loan.setRemainingEmis(loan.getRemainingEmis() - 1);

		// If all EMIs paid, mark loan as CLOSED
		if (loan.getRemainingEmis() == 0) {
			loan.setStatus("CLOSED");
		}

		loanRepository.save(loan);

		// Create transaction
		createTransaction(customerId, "LOAN_EMI_PAYMENT", loan.getEmiAmount(),
				balanceBefore, customer.getAmount());

		// Send notification if balance reaches zero
		notificationService.sendBalanceZeroAlert(customer, "LOAN_EMI_PAYMENT", balanceBefore);

		return loan;
	}

	@Override
	public List<Loan> getCustomerApprovedLoans(Long customerId) {
		return loanRepository.findByCustomerIdAndStatus(customerId, "APPROVED");
	}

	@Override
	public Loan getLoanById(Long loanId) {
		return loanRepository.findById(loanId).orElse(null);
	}

	// ==================== Helper Methods ====================

	private String generateRDAccountNo() {
		String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		long random = (long) (Math.random() * 1000);
		return "RD" + dateTime + String.format("%03d", random);
	}

	private String generateFDAccountNo() {
		String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		long random = (long) (Math.random() * 1000);
		return "FD" + dateTime + String.format("%03d", random);
	}

	private void createTransaction(Long customerId, String type, BigDecimal amount,
	                                BigDecimal balanceBefore, BigDecimal balanceAfter) {
		Transaction transaction = new Transaction();
		transaction.setCustomerId(customerId);
		transaction.setType(type);
		transaction.setAmount(amount);
		transaction.setBalanceBefore(balanceBefore);
		transaction.setBalanceAfter(balanceAfter);
		transaction.setTransactionDate(LocalDateTime.now());
		transaction.setTransactionId(generateTransactionId());
		transaction.setStatus("SUCCESS");
		transactionRepository.save(transaction);
	}

	private String generateTransactionId() {
		long count = transactionRepository.count();
		return "TXN" + String.format("%010d", (count + 1));
	}
}

