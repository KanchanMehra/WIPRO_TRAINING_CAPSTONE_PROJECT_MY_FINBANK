package com.bank.customer_service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Data Transfer Object for Customer
 * Used for transferring customer data between services
 * Password field is excluded for security
 */
public class CustomerDTO {

	private Long id;
	private String accountNo;
	private String firstName;
	private String lastName;
	private LocalDate dob;
	private LocalDate dateOfOpen;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String zipCode;
	private String country;
	private BigDecimal amount;
	private Boolean cheqFacil;  // Changed from String to Boolean
	private String accountType;
	private String userName;
	private String mobileNo;
	private String email;
	private String status;

	// Constructors
	public CustomerDTO() {
	}

	public CustomerDTO(Long id, String accountNo, String firstName, String lastName, LocalDate dob,
	                   LocalDate dateOfOpen, String address1, String address2, String city, String state, String zipCode,
	                   String country, BigDecimal amount, Boolean cheqFacil, String accountType,
	                   String userName, String mobileNo, String email, String status) {
		this.id = id;
		this.accountNo = accountNo;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
		this.dateOfOpen = dateOfOpen;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.country = country;
		this.amount = amount;
		this.cheqFacil = cheqFacil;
		this.accountType = accountType;
		this.userName = userName;
		this.mobileNo = mobileNo;
		this.email = email;
		this.status = status;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public LocalDate getDateOfOpen() {
		return dateOfOpen;
	}

	public void setDateOfOpen(LocalDate dateOfOpen) {
		this.dateOfOpen = dateOfOpen;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Boolean getCheqFacil() {
		return cheqFacil;
	}

	public void setCheqFacil(Boolean cheqFacil) {
		this.cheqFacil = cheqFacil;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}

