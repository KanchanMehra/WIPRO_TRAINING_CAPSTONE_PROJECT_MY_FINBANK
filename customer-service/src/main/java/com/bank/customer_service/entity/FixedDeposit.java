package com.bank.customer_service.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Fixed Deposit Entity
 * Represents a customer's fixed deposit account
 */
@Entity
@Table(name = "FIXED_DEPOSIT")
public class FixedDeposit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "CUSTOMER_ID", nullable = false)
	private Long customerId;

	@Column(name = "FD_ACCOUNT_NO", nullable = false, unique = true)
	private String fdAccountNo;

	@Column(name = "PRINCIPAL_AMOUNT", nullable = false)
	private BigDecimal principalAmount;

	@Column(name = "DURATION_IN_MONTHS", nullable = false)
	private Integer durationInMonths;

	@Column(name = "INTEREST_RATE", nullable = false)
	private BigDecimal interestRate;

	@Column(name = "MATURITY_AMOUNT", nullable = false)
	private BigDecimal maturityAmount;

	@Column(name = "START_DATE", nullable = false)
	private LocalDate startDate;

	@Column(name = "MATURITY_DATE", nullable = false)
	private LocalDate maturityDate;

	@Column(name = "STATUS", nullable = false, length = 20)
	private String status; // ACTIVE, MATURED, CLOSED

	@Column(name = "CREATED_DATE", nullable = false)
	private LocalDateTime createdDate;

	// Constructors
	public FixedDeposit() {
	}

	public FixedDeposit(Long customerId, String fdAccountNo, BigDecimal principalAmount,
	                     Integer durationInMonths, BigDecimal interestRate, BigDecimal maturityAmount,
	                     LocalDate startDate, LocalDate maturityDate) {
		this.customerId = customerId;
		this.fdAccountNo = fdAccountNo;
		this.principalAmount = principalAmount;
		this.durationInMonths = durationInMonths;
		this.interestRate = interestRate;
		this.maturityAmount = maturityAmount;
		this.startDate = startDate;
		this.maturityDate = maturityDate;
		this.status = "ACTIVE";
		this.createdDate = LocalDateTime.now();
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getFdAccountNo() {
		return fdAccountNo;
	}

	public void setFdAccountNo(String fdAccountNo) {
		this.fdAccountNo = fdAccountNo;
	}

	public BigDecimal getPrincipalAmount() {
		return principalAmount;
	}

	public void setPrincipalAmount(BigDecimal principalAmount) {
		this.principalAmount = principalAmount;
	}

	public Integer getDurationInMonths() {
		return durationInMonths;
	}

	public void setDurationInMonths(Integer durationInMonths) {
		this.durationInMonths = durationInMonths;
	}

	public BigDecimal getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(BigDecimal interestRate) {
		this.interestRate = interestRate;
	}

	public BigDecimal getMaturityAmount() {
		return maturityAmount;
	}

	public void setMaturityAmount(BigDecimal maturityAmount) {
		this.maturityAmount = maturityAmount;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(LocalDate maturityDate) {
		this.maturityDate = maturityDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
}

