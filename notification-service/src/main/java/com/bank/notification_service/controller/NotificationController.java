package com.bank.notification_service.controller;

import com.bank.notification_service.dto.CustomerBalanceAlertDTO;
import com.bank.notification_service.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * REST Controller for Notification operations
 */
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

	private final EmailService emailService;

	public NotificationController(EmailService emailService) {
		this.emailService = emailService;
	}

	/**
	 * Send balance zero alert email to admin
	 */
	@PostMapping("/balance-zero-alert")
	public ResponseEntity<Map<String, Object>> sendBalanceZeroAlert(@RequestBody CustomerBalanceAlertDTO alert) {
		Map<String, Object> response = new HashMap<>();

		try {
			emailService.sendBalanceZeroAlert(alert);
			response.put("success", true);
			response.put("message", "Balance zero alert sent successfully");
			response.put("customerName", alert.getCustomerName());
			response.put("accountNo", alert.getAccountNo());
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "Failed to send alert: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	/**
	 * Test endpoint to check if notification service is running
	 */
	@GetMapping("/health")
	public ResponseEntity<Map<String, String>> healthCheck() {
		Map<String, String> response = new HashMap<>();
		response.put("status", "UP");
		response.put("service", "notification-service");
		response.put("port", "8083");
		return ResponseEntity.ok(response);
	}

	/**
	 * Test endpoint to send a simple test email
	 */
	@PostMapping("/test-email")
	public ResponseEntity<Map<String, String>> sendTestEmail(@RequestParam String to) {
		Map<String, String> response = new HashMap<>();

		try {
			emailService.sendSimpleEmail(to, "Test Email from MyFin Bank",
				"This is a test email from MyFin Bank Notification Service. If you received this, the email service is working correctly!");
			response.put("success", "true");
			response.put("message", "Test email sent to " + to);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.put("success", "false");
			response.put("message", "Failed to send test email: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
}

