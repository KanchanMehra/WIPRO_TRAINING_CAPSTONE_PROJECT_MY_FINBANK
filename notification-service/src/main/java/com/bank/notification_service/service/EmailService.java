package com.bank.notification_service.service;

import com.bank.notification_service.dto.CustomerBalanceAlertDTO;

/**
 * Service interface for Email operations
 */
public interface EmailService {

	/**
	 * Send simple text email
	 */
	void sendSimpleEmail(String to, String subject, String body);

	/**
	 * Send HTML email
	 */
	void sendHtmlEmail(String to, String subject, String htmlBody);

	/**
	 * Send balance zero alert email to admin
	 */
	void sendBalanceZeroAlert(CustomerBalanceAlertDTO alert);
}

