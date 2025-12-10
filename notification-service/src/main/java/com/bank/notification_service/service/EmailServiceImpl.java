package com.bank.notification_service.service;

import com.bank.notification_service.dto.CustomerBalanceAlertDTO;
import com.bank.notification_service.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.format.DateTimeFormatter;

/**
 * Service implementation for Email operations
 * Validates all inputs and throws custom exceptions for invalid data
 */
@Service
public class EmailServiceImpl implements EmailService {

	private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

	private final JavaMailSender mailSender;

	@Value("${notification.admin.email}")
	private String adminEmail;

	@Value("${notification.from.email}")
	private String fromEmail;

	@Value("${notification.from.name}")
	private String fromName;

	public EmailServiceImpl(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Override
	public void sendSimpleEmail(String to, String subject, String body) {
		// Validate input
		validateEmail(to);
		validateSubject(subject);
		validateBody(body);

		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(fromEmail);
			message.setTo(to);
			message.setSubject(subject);
			message.setText(body);
			mailSender.send(message);

			logger.info("✅ Simple email sent successfully to: {}", to);
		} catch (Exception e) {
			logger.error("❌ Error sending simple email to {}: {}", to, e.getMessage());
			throw new EmailSendFailureException("Failed to send email to " + to, e);
		}
	}

	@Override
	public void sendHtmlEmail(String to, String subject, String htmlBody) {
		// Validate input
		validateEmail(to);
		validateSubject(subject);
		validateBody(htmlBody);

		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

			helper.setFrom(fromEmail, fromName);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(htmlBody, true);

			mailSender.send(message);

			logger.info("✅ HTML email sent successfully to: {}", to);
		} catch (MessagingException e) {
			logger.error("❌ Error sending HTML email to {}: {}", to, e.getMessage());
			throw new EmailSendFailureException("Failed to send HTML email to " + to, e);
		} catch (Exception e) {
			logger.error("❌ Unexpected error sending email to {}: {}", to, e.getMessage());
			throw new EmailSendFailureException("Failed to send email to " + to, e);
		}
	}

	@Override
	public void sendBalanceZeroAlert(CustomerBalanceAlertDTO alert) {
		// Validate alert
		if (alert == null) {
			throw new InvalidInputException("alert", "cannot be null");
		}
		if (alert.getCustomerName() == null || alert.getCustomerName().trim().isEmpty()) {
			throw new InvalidInputException("customerName", "cannot be empty");
		}
		if (alert.getEmail() == null || alert.getEmail().trim().isEmpty()) {
			throw new InvalidInputException("email", "cannot be empty");
		}
		if (alert.getAccountNo() == null || alert.getAccountNo().trim().isEmpty()) {
			throw new InvalidInputException("accountNo", "cannot be empty");
		}

		validateEmail(adminEmail);

		String subject = "🚨 ALERT: Customer Balance Zero - " + alert.getCustomerName();
		String htmlBody = createBalanceAlertHtml(alert);

		sendHtmlEmail(adminEmail, subject, htmlBody);

		logger.info("✅ Balance zero alert sent for customer: {} (ID: {})",
				alert.getCustomerName(), alert.getCustomerId());
	}

	/**
	 * Validate email address format
	 */
	private void validateEmail(String email) {
		if (email == null || email.trim().isEmpty()) {
			throw new InvalidEmailException("Email address cannot be null or empty");
		}
		if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
			throw new InvalidEmailException(email);
		}
	}

	/**
	 * Validate subject
	 */
	private void validateSubject(String subject) {
		if (subject == null || subject.trim().isEmpty()) {
			throw new InvalidInputException("subject", "cannot be empty");
		}
	}

	/**
	 * Validate body
	 */
	private void validateBody(String body) {
		if (body == null || body.trim().isEmpty()) {
			throw new InvalidInputException("body", "cannot be empty");
		}
	}

	private String createBalanceAlertHtml(CustomerBalanceAlertDTO alert) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");
		String formattedDate = alert.getTimestamp().format(formatter);

		return "<!DOCTYPE html>" +
				"<html>" +
				"<head>" +
				"<style>" +
				"body { font-family: Arial, sans-serif; margin: 0; padding: 20px; background-color: #f5f5f5; }" +
				".container { max-width: 600px; margin: 0 auto; background-color: #ffffff; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }" +
				".header { background: linear-gradient(135deg, #dc2626 0%, #991b1b 100%); color: white; padding: 30px; border-radius: 8px 8px 0 0; text-align: center; }" +
				".header h1 { margin: 0; font-size: 24px; }" +
				".alert-icon { font-size: 48px; margin-bottom: 10px; }" +
				".content { padding: 30px; }" +
				".alert-message { background-color: #fef2f2; border-left: 4px solid #dc2626; padding: 15px; margin-bottom: 20px; border-radius: 4px; }" +
				".detail-row { padding: 12px; border-bottom: 1px solid #e5e7eb; display: flex; justify-content: space-between; }" +
				".detail-row:last-child { border-bottom: none; }" +
				".label { font-weight: 600; color: #374151; }" +
				".value { color: #1f2937; }" +
				".highlight { color: #dc2626; font-weight: 700; }" +
				".footer { background-color: #f9fafb; padding: 20px; text-align: center; color: #6b7280; font-size: 14px; border-radius: 0 0 8px 8px; }" +
				".action-required { background-color: #fef3c7; padding: 15px; border-radius: 4px; margin-top: 20px; text-align: center; }" +
				".action-required strong { color: #92400e; }" +
				"</style>" +
				"</head>" +
				"<body>" +
				"<div class='container'>" +
				"<div class='header'>" +
				"<div class='alert-icon'>🚨</div>" +
				"<h1>Customer Balance Alert</h1>" +
				"</div>" +
				"<div class='content'>" +
				"<div class='alert-message'>" +
				"<strong>URGENT:</strong> A customer's account balance has reached zero!" +
				"</div>" +
				"<div class='detail-row'>" +
				"<span class='label'>Customer Name:</span>" +
				"<span class='value'>" + alert.getCustomerName() + "</span>" +
				"</div>" +
				"<div class='detail-row'>" +
				"<span class='label'>Account Number:</span>" +
				"<span class='value'>" + alert.getAccountNo() + "</span>" +
				"</div>" +
				"<div class='detail-row'>" +
				"<span class='label'>Customer ID:</span>" +
				"<span class='value'>" + alert.getCustomerId() + "</span>" +
				"</div>" +
				"<div class='detail-row'>" +
				"<span class='label'>Email:</span>" +
				"<span class='value'>" + alert.getEmail() + "</span>" +
				"</div>" +
				"<div class='detail-row'>" +
				"<span class='label'>Mobile:</span>" +
				"<span class='value'>" + alert.getMobileNo() + "</span>" +
				"</div>" +
				"<div class='detail-row'>" +
				"<span class='label'>Previous Balance:</span>" +
				"<span class='value highlight'>₹" + String.format("%.2f", alert.getPreviousBalance()) + "</span>" +
				"</div>" +
				"<div class='detail-row'>" +
				"<span class='label'>Current Balance:</span>" +
				"<span class='value highlight'>₹0.00</span>" +
				"</div>" +
				"<div class='detail-row'>" +
				"<span class='label'>Transaction Type:</span>" +
				"<span class='value'>" + alert.getTransactionType() + "</span>" +
				"</div>" +
				"<div class='detail-row'>" +
				"<span class='label'>Timestamp:</span>" +
				"<span class='value'>" + formattedDate + "</span>" +
				"</div>" +
				"<div class='action-required'>" +
				"<strong>⚠️ Action Required:</strong> Please contact the customer immediately." +
				"</div>" +
				"</div>" +
				"<div class='footer'>" +
				"<p>This is an automated notification from MyFin Bank System</p>" +
				"<p>&copy; 2025 MyFin Bank. All rights reserved.</p>" +
				"</div>" +
				"</div>" +
				"</body>" +
				"</html>";
	}
}

