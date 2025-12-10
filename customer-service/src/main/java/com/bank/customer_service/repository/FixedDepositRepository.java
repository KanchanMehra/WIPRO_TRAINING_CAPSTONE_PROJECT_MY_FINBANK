package com.bank.customer_service.repository;

import com.bank.customer_service.entity.FixedDeposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for FixedDeposit CRUD operations
 */
@Repository
public interface FixedDepositRepository extends JpaRepository<FixedDeposit, Long> {

	/**
	 * Find all FDs for a customer
	 */
	List<FixedDeposit> findByCustomerId(Long customerId);

	/**
	 * Find FDs by customer and status
	 */
	List<FixedDeposit> findByCustomerIdAndStatus(Long customerId, String status);
}

