package com.bank.admin_service.service;

import com.bank.admin_service.dto.CustomerDTO;
import com.bank.admin_service.entity.Admin;
import com.bank.admin_service.exception.*;
import com.bank.admin_service.repository.AdminRepository;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

	private final AdminRepository adminRepository;
	private final RestTemplate restTemplate;

	// Customer service URL (should be fetched from Eureka or config)
	private static final String CUSTOMER_SERVICE_URL = "http://localhost:8081/api/customers";

	public AdminServiceImpl(AdminRepository adminRepository, RestTemplate restTemplate) {
		this.adminRepository = adminRepository;
		this.restTemplate = restTemplate;
	}

	@Override
	@Transactional
	public Admin registerAdmin(Admin admin) {
		// Validate input
		if (admin == null) {
			throw new InvalidInputException("admin", "null");
		}
		if (admin.getAdminUserName() == null || admin.getAdminUserName().trim().isEmpty()) {
			throw new InvalidInputException("adminUserName", admin.getAdminUserName());
		}
		if (admin.getAdminPassword() == null || admin.getAdminPassword().trim().isEmpty()) {
			throw new InvalidInputException("adminPassword", "empty");
		}
		if (admin.getAdminEmail() == null || !admin.getAdminEmail().contains("@")) {
			throw new InvalidInputException("adminEmail", admin.getAdminEmail());
		}
		if (admin.getAdminPhone() == null || admin.getAdminPhone().trim().isEmpty()) {
			throw new InvalidInputException("adminPhone", admin.getAdminPhone());
		}

		// Check if admin already exists
		Optional<Admin> existing = adminRepository.findByAdminUserName(admin.getAdminUserName());
		if (existing.isPresent()) {
			throw new AdminAlreadyExistsException(admin.getAdminUserName());
		}

		// Set default status
		if (admin.getAdminStatus() == null) {
			admin.setAdminStatus("ACTIVE");
		}

		return adminRepository.save(admin);
	}

	@Override
	public Admin getAdminById(String adminId) {
		// Validate input
		if (adminId == null || adminId.trim().isEmpty()) {
			throw new InvalidInputException("adminId", adminId);
		}

		Optional<Admin> admin = adminRepository.findById(adminId);
		if (admin.isEmpty()) {
			throw new AdminNotFoundException(adminId);
		}

		return admin.get();
	}

	@Override
	public Admin getAdminByUserName(String adminUserName) {
		// Validate input
		if (adminUserName == null || adminUserName.trim().isEmpty()) {
			throw new InvalidInputException("adminUserName", adminUserName);
		}

		Optional<Admin> admin = adminRepository.findByAdminUserName(adminUserName);
		if (admin.isEmpty()) {
			throw new AdminNotFoundException("username", adminUserName);
		}

		return admin.get();
	}

	@Override
	public List<CustomerDTO> getAllCustomers(String status) {
		try {
			String url = CUSTOMER_SERVICE_URL;
			if (status != null && !status.isEmpty()) {
				url += "?status=" + status;
			}

			ResponseEntity<List<CustomerDTO>> response = restTemplate.exchange(
					url,
					HttpMethod.GET,
					null,
					new ParameterizedTypeReference<List<CustomerDTO>>() {}
			);

			return response.getBody();
		} catch (RestClientException e) {
			throw new ServiceUnavailableException("customer-service");
		} catch (Exception e) {
			throw new ServiceUnavailableException("customer-service: " + e.getMessage());
		}
	}

	@Override
	public CustomerDTO getCustomerById(Long customerId) {
		// Validate input
		if (customerId == null || customerId <= 0) {
			throw new InvalidInputException("customerId", customerId);
		}

		try {
			String url = CUSTOMER_SERVICE_URL + "/" + customerId;
			ResponseEntity<CustomerDTO> response = restTemplate.getForEntity(url, CustomerDTO.class);

			if (response.getBody() == null) {
				throw new CustomerNotFoundException(customerId);
			}

			return response.getBody();
		} catch (RestClientException e) {
			if (e.getMessage() != null && e.getMessage().contains("404")) {
				throw new CustomerNotFoundException(customerId);
			}
			throw new ServiceUnavailableException("customer-service");
		} catch (CustomerNotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceUnavailableException("customer-service: " + e.getMessage());
		}
	}

	@Override
	public CustomerDTO deactivateCustomer(Long customerId) {
		// Validate input
		if (customerId == null || customerId <= 0) {
			throw new InvalidInputException("customerId", customerId);
		}

		try {
			String url = CUSTOMER_SERVICE_URL + "/" + customerId + "/deactivate";
			ResponseEntity<CustomerDTO> response = restTemplate.postForEntity(url, null, CustomerDTO.class);

			if (response.getBody() == null) {
				throw new CustomerNotFoundException(customerId);
			}

			return response.getBody();
		} catch (RestClientException e) {
			if (e.getMessage() != null && e.getMessage().contains("404")) {
				throw new CustomerNotFoundException(customerId);
			}
			throw new ServiceUnavailableException("customer-service");
		} catch (CustomerNotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceUnavailableException("customer-service: " + e.getMessage());
		}
	}

	@Override
	public CustomerDTO activateCustomer(Long customerId) {
		// Validate input
		if (customerId == null || customerId <= 0) {
			throw new InvalidInputException("customerId", customerId);
		}

		try {
			String url = CUSTOMER_SERVICE_URL + "/" + customerId + "/activate";
			ResponseEntity<CustomerDTO> response = restTemplate.postForEntity(url, null, CustomerDTO.class);

			if (response.getBody() == null) {
				throw new CustomerNotFoundException(customerId);
			}

			return response.getBody();
		} catch (RestClientException e) {
			if (e.getMessage() != null && e.getMessage().contains("404")) {
				throw new CustomerNotFoundException(customerId);
			}
			throw new ServiceUnavailableException("customer-service");
		} catch (CustomerNotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceUnavailableException("customer-service: " + e.getMessage());
		}
	}

	@Override
    public List<CustomerDTO> searchCustomers(String keyword) {
		// Validate input
		if (keyword == null || keyword.trim().isEmpty()) {
			throw new InvalidInputException("keyword", keyword);
		}

		try {
			String url = CUSTOMER_SERVICE_URL + "/search?keyword=" + keyword;
			ResponseEntity<List<CustomerDTO>> response = restTemplate.exchange(
					url,
					HttpMethod.GET,
					null,
					new ParameterizedTypeReference<List<CustomerDTO>>() {}
			);

			return response.getBody();
		} catch (RestClientException e) {
			throw new ServiceUnavailableException("customer-service");
		} catch (Exception e) {
			throw new ServiceUnavailableException("customer-service: " + e.getMessage());
		}
	}
}



