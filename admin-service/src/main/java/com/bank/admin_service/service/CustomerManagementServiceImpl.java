package com.bank.admin_service.service;

import com.bank.admin_service.dto.CustomerDTO;
import com.bank.admin_service.exception.*;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CustomerManagementServiceImpl implements CustomerManagementService {

	private final RestTemplate restTemplate;
	private final DiscoveryClient discoveryClient;

	public CustomerManagementServiceImpl(RestTemplate restTemplate, DiscoveryClient discoveryClient) {
		this.restTemplate = restTemplate;
		this.discoveryClient = discoveryClient;
	}

	@Override
	public List<CustomerDTO> getInactiveCustomers() {
		try {
			String customerServiceUrl = getCustomerServiceUrl();
			String url = customerServiceUrl + "/api/customers/inactive";

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
	public CustomerDTO activateCustomer(Long customerId) {
		// Validate input
		if (customerId == null || customerId <= 0) {
			throw new InvalidInputException("customerId", customerId);
		}

		try {
			String customerServiceUrl = getCustomerServiceUrl();
			String url = customerServiceUrl + "/api/customers/" + customerId + "/activate";

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
	public CustomerDTO getCustomerById(Long customerId) {
		// Validate input
		if (customerId == null || customerId <= 0) {
			throw new InvalidInputException("customerId", customerId);
		}

		try {
			String customerServiceUrl = getCustomerServiceUrl();
			String url = customerServiceUrl + "/api/customers/" + customerId;

			ResponseEntity<CustomerDTO> response = restTemplate.exchange(
					url,
					HttpMethod.GET,
					null,
					CustomerDTO.class
			);

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

	/**
	 * Get customer-service URL from Eureka
	 * @throws ServiceUnavailableException if customer-service is not registered in Eureka
	 */
	private String getCustomerServiceUrl() {
		List<ServiceInstance> instances = discoveryClient.getInstances("customer-service");
		if (instances == null || instances.isEmpty()) {
			throw new ServiceUnavailableException("customer-service");
		}
		ServiceInstance instance = instances.get(0);
		return instance.getUri().toString();
	}
}

