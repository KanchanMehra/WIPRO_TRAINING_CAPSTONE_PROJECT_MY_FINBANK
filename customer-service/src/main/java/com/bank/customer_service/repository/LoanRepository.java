package com.bank.customer_service.repository;

import com.bank.customer_service.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Loan CRUD operations
 */
@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

	/**
	 * Find all loans for a customer
	 */
	List<Loan> findByCustomerId(Long customerId);

	/**
	 * Find loans by customer and status
	 */
	List<Loan> findByCustomerIdAndStatus(Long customerId, String status);

	/**
	 * Find loan by application number
	 */
	Optional<Loan> findByLoanApplicationNo(String loanApplicationNo);

	/**
	 * Find all pending loans (for admin)
	 */
	List<Loan> findByStatus(String status);
}

