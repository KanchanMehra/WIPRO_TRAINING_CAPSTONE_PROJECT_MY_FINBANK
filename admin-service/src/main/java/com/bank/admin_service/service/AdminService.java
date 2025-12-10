package com.bank.admin_service.service;

import com.bank.admin_service.dto.CustomerDTO;
import com.bank.admin_service.entity.Admin;
import com.bank.admin_service.exception.*;

import java.util.List;

public interface AdminService {

	/**
	 * Register a new admin
	 * @throws InvalidInputException if input is invalid
	 * @throws AdminAlreadyExistsException if username already exists
	 */
	Admin registerAdmin(Admin admin) throws InvalidInputException, AdminAlreadyExistsException;

	/**
	 * Get admin by ID
	 * @throws InvalidInputException if adminId is invalid
	 * @throws AdminNotFoundException if admin not found
	 */
	Admin getAdminById(String adminId) throws InvalidInputException, AdminNotFoundException;

	/**
	 * Get admin by username
	 * @throws InvalidInputException if username is invalid
	 * @throws AdminNotFoundException if admin not found
	 */
	Admin getAdminByUserName(String adminUserName) throws InvalidInputException, AdminNotFoundException;

	/**
	 * Get all customers with optional status filter
	 * @param status Optional: "ACTIVE", "INACTIVE", or null for all
	 * @return List of all customers
	 * @throws ServiceUnavailableException if customer-service is unavailable
	 */
	List<CustomerDTO> getAllCustomers(String status) throws ServiceUnavailableException;

	/**
	 * Get customer by ID
	 * @param customerId Customer ID
	 * @return Customer DTO object
	 * @throws InvalidInputException if customerId is invalid
	 * @throws CustomerNotFoundException if customer not found
	 * @throws ServiceUnavailableException if customer-service is unavailable
	 */
	CustomerDTO getCustomerById(Long customerId) throws InvalidInputException, CustomerNotFoundException, ServiceUnavailableException;

	/**
	 * Deactivate a customer (change status to INACTIVE)
	 * @throws InvalidInputException if customerId is invalid
	 * @throws CustomerNotFoundException if customer not found
	 * @throws ServiceUnavailableException if customer-service is unavailable
	 */
	CustomerDTO deactivateCustomer(Long customerId) throws InvalidInputException, CustomerNotFoundException, ServiceUnavailableException;

	/**
	 * Activate a customer (change status to ACTIVE and generate account number)
	 * @throws InvalidInputException if customerId is invalid
	 * @throws CustomerNotFoundException if customer not found
	 * @throws ServiceUnavailableException if customer-service is unavailable
	 */
	CustomerDTO activateCustomer(Long customerId) throws InvalidInputException, CustomerNotFoundException, ServiceUnavailableException;

    List<CustomerDTO> searchCustomers(String keyword);
}

