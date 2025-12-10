package com.bank.admin_service.service;

import com.bank.admin_service.dto.CustomerDTO;

import java.util.List;

public interface CustomerManagementService {

	/**
	 * Get all inactive customers from customer-service
	 */
	List<CustomerDTO> getInactiveCustomers();

	/**
	 * Activate a customer (triggers account number generation in customer-service)
	 */
	CustomerDTO activateCustomer(Long customerId);

	/**
	 * Get customer details by ID
	 */
	CustomerDTO getCustomerById(Long customerId);
}

