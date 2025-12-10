package com.bank.customer_service.repository;

import com.bank.customer_service.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	Optional<Customer> findByUserName(String userName);

	Optional<Customer> findByEmail(String email);

	List<Customer> findByStatus(String status);

	Optional<Customer> findById(Long id);

	Optional<Customer> findByAccountNo(String accountNo);
}

