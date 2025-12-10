package com.bank.customer_service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "account_no", length = 20, unique = true)
	private String accountNo;

	@Column(name = "first_name", length = 50, nullable = false)
	private String firstName;

	@Column(name = "last_name", length = 50, nullable = false)
	private String lastName;

	@Column(name = "dob", nullable = false)
	private LocalDate dob;

	@Column(name = "date_of_open", nullable = false)
	private LocalDate dateOfOpen;

	@Column(name = "address1", length = 100, nullable = false)
	private String address1;

	@Column(name = "address2", length = 100)
	private String address2;

	@Column(name = "city", length = 50, nullable = false)
	private String city;

	@Column(name = "state", length = 50, nullable = false)
	private String state;

	@Column(name = "zip_code", length = 10, nullable = false)
	private String zipCode;

	@Column(name = "country", length = 50, nullable = false)
	private String country;

	@Column(name = "amount", precision = 15, scale = 2, nullable = false)
	private BigDecimal amount;

	@Column(name = "cheq_facil", nullable = false)
	private Boolean cheqFacil;

	@Column(name = "account_type", length = 20, nullable = false)
	private String accountType;

	@Column(name = "user_name", length = 50, nullable = false, unique = true)
	private String userName;

	@Column(name = "password", length = 255, nullable = false)
	private String password;

	@Column(name = "mobile_no", length = 20, nullable = false)
	private String mobileNo;

	@Column(name = "email", length = 100, nullable = false, unique = true)
	private String email;

	@Column(name = "status", length = 20, nullable = false)
	private String status;
}
