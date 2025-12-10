package com.bank.customer_service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for Transaction
 * Used for transferring transaction data via REST APIs
 */
public class TransactionDTO {

	private Long id;
	private String transactionId;
	private Long customerId;
	private String type;
	private BigDecimal amount;
	private BigDecimal balanceBefore;
	private BigDecimal balanceAfter;
	private LocalDateTime transactionDate;
	private String status;
	private String remarks;

	// Constructors
	public TransactionDTO() {
	}

	public TransactionDTO(Long id, String transactionId, Long customerId, String type, BigDecimal amount,
	                      BigDecimal balanceBefore, BigDecimal balanceAfter, LocalDateTime transactionDate,
	                      String status, String remarks) {
		this.id = id;
		this.transactionId = transactionId;
		this.customerId = customerId;
		this.type = type;
		this.amount = amount;
		this.balanceBefore = balanceBefore;
		this.balanceAfter = balanceAfter;
		this.transactionDate = transactionDate;
		this.status = status;
		this.remarks = remarks;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getBalanceBefore() {
		return balanceBefore;
	}

	public void setBalanceBefore(BigDecimal balanceBefore) {
		this.balanceBefore = balanceBefore;
	}

	public BigDecimal getBalanceAfter() {
		return balanceAfter;
	}

	public void setBalanceAfter(BigDecimal balanceAfter) {
		this.balanceAfter = balanceAfter;
	}

	public LocalDateTime getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDateTime transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}

