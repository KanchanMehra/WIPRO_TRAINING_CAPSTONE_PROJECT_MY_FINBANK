package com.bank.customer_service.repository;

import com.bank.customer_service.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository for Transaction CRUD operations
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	Optional<Transaction> findByTransactionId(String transactionId);

	List<Transaction> findByCustomerIdOrderByTransactionDateDesc(Long customerId);

	List<Transaction> findByCustomerIdAndTypeOrderByTransactionDateDesc(Long customerId, String type);

	List<Transaction> findByCustomerIdAndTransactionDateBetweenOrderByTransactionDateDesc(
			Long customerId, LocalDateTime fromDate, LocalDateTime toDate);

	List<Transaction> findByCustomerIdAndTypeAndTransactionDateBetweenOrderByTransactionDateDesc(
			Long customerId, String type, LocalDateTime fromDate, LocalDateTime toDate);
}

