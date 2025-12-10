package com.bank.notification_service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for Customer Balance Zero Alert
 */
public class CustomerBalanceAlertDTO {

	private Long customerId;
	private String accountNo;
	private String customerName;
	private String email;
	private String mobileNo;
	private BigDecimal previousBalance;
	private String transactionType;
	private LocalDateTime timestamp;

	// Constructors
	public CustomerBalanceAlertDTO() {
	}

	public CustomerBalanceAlertDTO(Long customerId, String accountNo, String customerName,
	                                String email, String mobileNo, BigDecimal previousBalance,
	                                String transactionType, LocalDateTime timestamp) {
		this.customerId = customerId;
		this.accountNo = accountNo;
		this.customerName = customerName;
		this.email = email;
		this.mobileNo = mobileNo;
		this.previousBalance = previousBalance;
		this.transactionType = transactionType;
		this.timestamp = timestamp;
	}

	// Getters and Setters
	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public BigDecimal getPreviousBalance() {
		return previousBalance;
	}

	public void setPreviousBalance(BigDecimal previousBalance) {
		this.previousBalance = previousBalance;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
}

