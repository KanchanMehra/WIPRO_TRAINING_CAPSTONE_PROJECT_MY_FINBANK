package com.bank.customer_service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for Loan
 * Used for transferring loan data via REST APIs
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
	private Integer totalEmis;
	private Integer paidEmis;
	private Integer remainingEmis;

	// Constructors
	public LoanDTO() {
	}

	public LoanDTO(Long id, Long customerId, String loanApplicationNo, BigDecimal loanAmount,
	               Integer durationInMonths, BigDecimal interestRate, BigDecimal emiAmount,
	               BigDecimal totalAmountPayable, BigDecimal totalInterest, String purpose,
	               LocalDate requiredByDate, String remarks, String status, LocalDateTime applicationDate,
	               LocalDateTime approvalDate, Integer totalEmis, Integer paidEmis, Integer remainingEmis) {
		this.id = id;
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
		this.status = status;
		this.applicationDate = applicationDate;
		this.approvalDate = approvalDate;
		this.totalEmis = totalEmis;
		this.paidEmis = paidEmis;
		this.remainingEmis = remainingEmis;
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

	public Integer getTotalEmis() {
		return totalEmis;
	}

	public void setTotalEmis(Integer totalEmis) {
		this.totalEmis = totalEmis;
	}

	public Integer getPaidEmis() {
		return paidEmis;
	}

	public void setPaidEmis(Integer paidEmis) {
		this.paidEmis = paidEmis;
	}

	public Integer getRemainingEmis() {
		return remainingEmis;
	}

	public void setRemainingEmis(Integer remainingEmis) {
		this.remainingEmis = remainingEmis;
	}
}

