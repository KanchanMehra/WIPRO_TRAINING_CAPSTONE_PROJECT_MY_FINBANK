package com.bank.customer_service.service;

import com.bank.customer_service.entity.Customer;
import com.bank.customer_service.entity.Transaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface CustomerService {

	Customer registerCustomer(Customer customer);

	List<Customer> getInactiveCustomers();

	Customer activateCustomer(Long customerId);

	Customer getCustomerById(Long customerId);

	Customer validateLogin(String userName, String password);

	/**
	 * Deposit money into customer account
	 * @param customerId Customer ID
	 * @param amount Amount to deposit (must be > 0)
	 * @return Transaction object with transaction details
	 * @throws RuntimeException if amount is invalid or customer not found
	 */
	Transaction depositMoney(Long customerId, BigDecimal amount);

	/**
	 * Withdraw money from customer account
	 * @param customerId Customer ID
	 * @param amount Amount to withdraw (must be > 0 and <= balance)
	 * @return Transaction object with transaction details
	 * @throws RuntimeException if amount is invalid, insufficient balance, or customer not found
	 */
	Transaction withdrawMoney(Long customerId, BigDecimal amount);

	/**
	 * Transfer money from one customer to another
	 * @param senderCustomerId Customer sending the money
	 * @param targetAccountNo Target account number (recipient)
	 * @param amount Amount to transfer
	 * @return Map with transaction details for both sender and receiver
	 * @throws RuntimeException if:
	 *   - Amount is invalid (<=0)
	 *   - Insufficient balance
	 *   - Target account not found
	 *   - Target account not active
	 *   - Target account is same as sender
	 */
	Map<String, Object> transferMoney(Long senderCustomerId, String targetAccountNo, BigDecimal amount);
}

