package com.bank.admin_service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.concurrent.atomic.AtomicLong;

@Entity
@Table(name = "admin")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Admin {
	private static final AtomicLong adminSequence = new AtomicLong(1000);

	@Id
	@Column(name = "admin_id", length = 50, nullable = false)
	private String adminId;

	@Column(name = "admin_name", length = 100, nullable = false)
	private String adminName;

	@Column(name = "admin_user_name", length = 50, nullable = false, unique = true)
	private String adminUserName;

	@Column(name = "admin_password", length = 255, nullable = false)
	private String adminPassword;

	@Column(name = "admin_email", length = 100, nullable = false, unique = true)
	private String adminEmail;

	@Column(name = "admin_phone", length = 20, nullable = false)
	private String adminPhone;

	@Column(name = "admin_status", length = 20, nullable = false)
	private String adminStatus;

	@PrePersist
	private void generateAdminId() {
		if (this.adminId == null || this.adminId.isEmpty()) {
			this.adminId = "ADM" + adminSequence.incrementAndGet();
		}
	}
}

