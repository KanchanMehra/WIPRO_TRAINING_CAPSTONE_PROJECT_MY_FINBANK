package com.bank.customer_service.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Loan Entity
 * Represents a customer loan application
 */
@Entity
@Table(name = "LOAN")
public class Loan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "CUSTOMER_ID", nullable = false)
	private Long customerId;

	@Column(name = "LOAN_APPLICATION_NO", nullable = false, unique = true)
	private String loanApplicationNo;

	@Column(name = "LOAN_AMOUNT", nullable = false)
	private BigDecimal loanAmount;

	@Column(name = "DURATION_IN_MONTHS", nullable = false)
	private Integer durationInMonths;

	@Column(name = "INTEREST_RATE", nullable = false)
	private BigDecimal interestRate;

	@Column(name = "EMI_AMOUNT", nullable = false)
	private BigDecimal emiAmount;

	@Column(name = "TOTAL_AMOUNT_PAYABLE", nullable = false)
	private BigDecimal totalAmountPayable;

	@Column(name = "TOTAL_INTEREST", nullable = false)
	private BigDecimal totalInterest;

	@Column(name = "PURPOSE")
	private String purpose;

	@Column(name = "REQUIRED_BY_DATE")
	private LocalDate requiredByDate;

	@Column(name = "REMARKS", length = 500)
	private String remarks;

	@Column(name = "STATUS", nullable = false, length = 20)
	private String status; // PENDING, APPROVED, REJECTED, DISBURSED, CLOSED

	@Column(name = "APPLICATION_DATE", nullable = false)
	private LocalDateTime applicationDate;

	@Column(name = "APPROVAL_DATE")
	private LocalDateTime approvalDate;

	@Column(name = "ADMIN_REMARKS", length = 500)
	private String adminRemarks;

	// EMI Tracking Fields
	@Column(name = "TOTAL_EMIS")
	private Integer totalEmis;

	@Column(name = "REMAINING_EMIS")
	private Integer remainingEmis;

	@Column(name = "PAID_EMIS")
	private Integer paidEmis;

	// Constructors
	public Loan() {
	}

	public Loan(Long customerId, String loanApplicationNo, BigDecimal loanAmount,
	            Integer durationInMonths, BigDecimal interestRate, BigDecimal emiAmount,
	            BigDecimal totalAmountPayable, BigDecimal totalInterest, String purpose,
	            LocalDate requiredByDate, String remarks) {
		this.customerId = customerId;
		this.loanApplicationNo = loanApplicationNo;
		this.loanAmount = loanAmount;
		this.durationInMonths = durationInMonths;
		this.interestRate = interestRate;
		this.emiAmount = emiAmount;
		this.totalAmountPayable = totalAmountPayable;
		this.totalInterest = totalInterest;
		this.purpose = purpose;
		this.requiredByDate = requiredByDate;
		this.remarks = remarks;
		this.status = "PENDING";
		this.applicationDate = LocalDateTime.now();
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

	public String getLoanApplicationNo() {
		return loanApplicationNo;
	}

	public void setLoanApplicationNo(String loanApplicationNo) {
		this.loanApplicationNo = loanApplicationNo;
	}

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
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

	public BigDecimal getEmiAmount() {
		return emiAmount;
	}

	public void setEmiAmount(BigDecimal emiAmount) {
		this.emiAmount = emiAmount;
	}

	public BigDecimal getTotalAmountPayable() {
		return totalAmountPayable;
	}

	public void setTotalAmountPayable(BigDecimal totalAmountPayable) {
		this.totalAmountPayable = totalAmountPayable;
	}

	public BigDecimal getTotalInterest() {
		return totalInterest;
	}

	public void setTotalInterest(BigDecimal totalInterest) {
		this.totalInterest = totalInterest;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public LocalDate getRequiredByDate() {
		return requiredByDate;
	}

	public void setRequiredByDate(LocalDate requiredByDate) {
		this.requiredByDate = requiredByDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(LocalDateTime applicationDate) {
		this.applicationDate = applicationDate;
	}

	public LocalDateTime getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(LocalDateTime approvalDate) {
		this.approvalDate = approvalDate;
	}

	public String getAdminRemarks() {
		return adminRemarks;
	}

	public void setAdminRemarks(String adminRemarks) {
		this.adminRemarks = adminRemarks;
	}

	public Integer getTotalEmis() {
		return totalEmis;
	}

	public void setTotalEmis(Integer totalEmis) {
		this.totalEmis = totalEmis;
	}

	public Integer getRemainingEmis() {
		return remainingEmis;
	}

	public void setRemainingEmis(Integer remainingEmis) {
		this.remainingEmis = remainingEmis;
	}

	public Integer getPaidEmis() {
		return paidEmis;
	}

	public void setPaidEmis(Integer paidEmis) {
		this.paidEmis = paidEmis;
	}
}

