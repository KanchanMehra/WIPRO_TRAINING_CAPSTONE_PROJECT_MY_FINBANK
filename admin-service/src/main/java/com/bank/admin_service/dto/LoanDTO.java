package com.bank.admin_service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for Loan
 * Used for inter-service communication between admin-service and customer-service
 */
public class LoanDTO {

	private Long id;
	private Long customerId;
	private String loanApplicationNo;
	private BigDecimal loanAmount;
	private Integer durationInMonths;
	private BigDecimal interestRate;
	private BigDecimal emiAmount;
	private BigDecimal totalAmountPayable;
	private BigDecimal totalInterest;
	private String purpose;
	private LocalDate requiredByDate;
	private String remarks;
	private String status;
	private LocalDateTime applicationDate;
	private LocalDateTime approvalDate;
	private String adminRemarks;

	// Customer details (for display)
	private String customerFirstName;
	private String customerLastName;
	private String customerEmail;
	private BigDecimal customerBalance;

	// Constructors
	public LoanDTO() {}

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

	public String getCustomerFirstName() {
		return customerFirstName;
	}

	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	public String getCustomerLastName() {
		return customerLastName;
	}

	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public BigDecimal getCustomerBalance() {
		return customerBalance;
	}

	public void setCustomerBalance(BigDecimal customerBalance) {
		this.customerBalance = customerBalance;
	}
}

