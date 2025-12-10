package com.bank.admin_service.repository;

import com.bank.admin_service.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, String> {

	Optional<Admin> findByAdminUserName(String adminUserName);

	Optional<Admin> findByAdminEmail(String adminEmail);

	Optional<Admin> findByAdminId(String adminId);
}

