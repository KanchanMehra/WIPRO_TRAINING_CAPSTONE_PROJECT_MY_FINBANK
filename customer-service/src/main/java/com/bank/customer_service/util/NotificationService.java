package com.bank.customer_service.util;

import com.bank.customer_service.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Service for sending notifications to notification-service
 * Uses custom exceptions but catches them gracefully to avoid breaking transactions
 */
@Service
public class NotificationService {

	private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

	private final RestTemplate restTemplate;

	@Value("${notification.service.url:http://localhost:8083}")
	private String notificationServiceUrl;

	public NotificationService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	/**
	 * Send balance zero alert when customer balance reaches zero
	 * Catches all exceptions to avoid breaking transactions
	 */
	public void sendBalanceZeroAlert(Customer customer, String transactionType, BigDecimal previousBalance) {
		// Validate customer
		if (customer == null) {
			logger.error("Cannot send balance zero alert: customer is null");
			return;
		}

		// Check if balance is exactly zero
		if (customer.getAmount() != null && customer.getAmount().compareTo(BigDecimal.ZERO) == 0) {
			try {
				// Validate required fields
				if (customer.getEmail() == null || customer.getEmail().trim().isEmpty()) {
					logger.warn("Cannot send balance zero alert: customer email is missing for customer ID: {}", customer.getId());
					return;
				}

				// Create alert DTO
				Map<String, Object> alert = new HashMap<>();
				alert.put("customerId", customer.getId());
				alert.put("accountNo", customer.getAccountNo());
				alert.put("customerName", customer.getFirstName() + " " + customer.getLastName());
				alert.put("email", customer.getEmail());
				alert.put("mobileNo", customer.getMobileNo());
				alert.put("previousBalance", previousBalance);
				alert.put("transactionType", transactionType);
				alert.put("timestamp", LocalDateTime.now().toString());

				// Call notification service
				String url = notificationServiceUrl + "/api/notifications/balance-zero-alert";
				restTemplate.postForObject(url, alert, Map.class);

				logger.info("✅ Balance zero alert sent for customer: {} {} (ID: {})",
						customer.getFirstName(), customer.getLastName(), customer.getId());

			} catch (RestClientException e) {
				// Log error but don't fail the transaction - notification is not critical
				logger.error("⚠️ Failed to send balance zero alert - notification service unavailable: {}", e.getMessage());
			} catch (Exception e) {
				// Log error but don't fail the transaction
				logger.error("⚠️ Failed to send balance zero alert - unexpected error: {}", e.getMessage(), e);
			}
		}
	}
}

