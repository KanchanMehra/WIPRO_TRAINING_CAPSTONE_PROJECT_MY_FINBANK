package com.bank.customer_service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Transaction Entity - Records all financial transactions (deposits, withdrawals, transfers)
 */
@Entity
@Table(name = "transaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "transaction_id", unique = true, nullable = false, length = 20)
	private String transactionId;

	@Column(name = "customer_id", nullable = false)
	private Long customerId;

	@Column(name = "type", nullable = false, length = 20)
	private String type; // DEPOSIT, WITHDRAW, TRANSFER

	@Column(name = "amount", precision = 15, scale = 2, nullable = false)
	private BigDecimal amount;

	@Column(name = "balance_before", precision = 15, scale = 2)
	private BigDecimal balanceBefore;

	@Column(name = "balance_after", precision = 15, scale = 2, nullable = false)
	private BigDecimal balanceAfter;

	@Column(name = "transaction_date", nullable = false)
	private LocalDateTime transactionDate;

	@Column(name = "status", nullable = false, length = 20)
	private String status; // SUCCESS, FAILED

	@Column(name = "remarks", length = 255)
	private String remarks;
}

