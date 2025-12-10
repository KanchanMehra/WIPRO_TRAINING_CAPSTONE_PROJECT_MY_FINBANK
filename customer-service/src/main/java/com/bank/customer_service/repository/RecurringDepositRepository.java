package com.bank.customer_service.repository;

import com.bank.customer_service.entity.RecurringDeposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for RecurringDeposit CRUD operations
 */
@Repository
public interface RecurringDepositRepository extends JpaRepository<RecurringDeposit, Long> {

	/**
	 * Find all RDs for a customer
	 */
	List<RecurringDeposit> findByCustomerId(Long customerId);

	/**
	 * Find RDs by customer and status
	 */
	List<RecurringDeposit> findByCustomerIdAndStatus(Long customerId, String status);
}

