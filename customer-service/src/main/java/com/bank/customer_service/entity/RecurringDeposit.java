package com.bank.customer_service.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Recurring Deposit Entity
 * Represents a customer's recurring deposit account
 */
@Entity
@Table(name = "RECURRING_DEPOSIT")
public class RecurringDeposit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "CUSTOMER_ID", nullable = false)
	private Long customerId;

	@Column(name = "RD_ACCOUNT_NO", nullable = false, unique = true)
	private String rdAccountNo;

	@Column(name = "MONTHLY_INSTALLMENT", nullable = false)
	private BigDecimal monthlyInstallment;

	@Column(name = "DURATION_IN_MONTHS", nullable = false)
	private Integer durationInMonths;

	@Column(name = "INTEREST_RATE", nullable = false)
	private BigDecimal interestRate;

	@Column(name = "MATURITY_AMOUNT", nullable = false)
	private BigDecimal maturityAmount;

	@Column(name = "TOTAL_DEPOSITED")
	private BigDecimal totalDeposited;

	@Column(name = "START_DATE", nullable = false)
	private LocalDate startDate;

	@Column(name = "MATURITY_DATE", nullable = false)
	private LocalDate maturityDate;

	@Column(name = "STATUS", nullable = false, length = 20)
	private String status; // ACTIVE, MATURED, CLOSED

	@Column(name = "CREATED_DATE", nullable = false)
	private LocalDateTime createdDate;

	// Constructors
	public RecurringDeposit() {
	}

	public RecurringDeposit(Long customerId, String rdAccountNo, BigDecimal monthlyInstallment,
	                         Integer durationInMonths, BigDecimal interestRate, BigDecimal maturityAmount,
	                         LocalDate startDate, LocalDate maturityDate) {
		this.customerId = customerId;
		this.rdAccountNo = rdAccountNo;
		this.monthlyInstallment = monthlyInstallment;
		this.durationInMonths = durationInMonths;
		this.interestRate = interestRate;
		this.maturityAmount = maturityAmount;
		this.totalDeposited = monthlyInstallment; // First installment
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

	public String getRdAccountNo() {
		return rdAccountNo;
	}

	public void setRdAccountNo(String rdAccountNo) {
		this.rdAccountNo = rdAccountNo;
	}

	public BigDecimal getMonthlyInstallment() {
		return monthlyInstallment;
	}

	public void setMonthlyInstallment(BigDecimal monthlyInstallment) {
		this.monthlyInstallment = monthlyInstallment;
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

	public BigDecimal getTotalDeposited() {
		return totalDeposited;
	}

	public void setTotalDeposited(BigDecimal totalDeposited) {
		this.totalDeposited = totalDeposited;
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

