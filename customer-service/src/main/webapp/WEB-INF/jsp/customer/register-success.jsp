<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Registration Successful - MyFin Bank</title>
	<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
	<style>
		:root {
			--primary-color: #1e40af;
			--success-color: #10b981;
		}

		body {
			background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
			min-height: 100vh;
			display: flex;
			align-items: center;
			justify-content: center;
			font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
			padding: 20px;
		}

		.success-container {
			background: white;
			border-radius: 12px;
			box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
			max-width: 500px;
			padding: 50px 40px;
			text-align: center;
			animation: slideUp 0.5s ease-out;
		}

		@keyframes slideUp {
			from {
				opacity: 0;
				transform: translateY(30px);
			}
			to {
				opacity: 1;
				transform: translateY(0);
			}
		}

		.success-icon {
			font-size: 80px;
			margin-bottom: 20px;
			animation: bounceIn 0.6s ease-out;
		}

		@keyframes bounceIn {
			0% {
				transform: scale(0);
				opacity: 0;
			}
			50% {
				transform: scale(1.1);
			}
			100% {
				transform: scale(1);
				opacity: 1;
			}
		}

		.success-title {
			color: var(--success-color);
			font-size: 28px;
			font-weight: 700;
			margin-bottom: 15px;
		}

		.success-message {
			color: #6b7280;
			font-size: 16px;
			line-height: 1.6;
			margin-bottom: 30px;
		}

		.customer-info {
			background: #f9fafb;
			padding: 20px;
			border-radius: 8px;
			margin-bottom: 30px;
			text-align: left;
		}

		.info-item {
			display: flex;
			justify-content: space-between;
			padding: 10px 0;
			border-bottom: 1px solid #e5e7eb;
		}

		.info-item:last-child {
			border-bottom: none;
		}

		.info-label {
			color: var(--primary-color);
			font-weight: 600;
		}

		.info-value {
			color: #374151;
		}

		.status-badge {
			display: inline-block;
			background: #fef3c7;
			color: #92400e;
			padding: 6px 12px;
			border-radius: 20px;
			font-size: 13px;
			font-weight: 600;
			margin-top: 15px;
		}

		.action-buttons {
			display: flex;
			gap: 15px;
			margin-top: 30px;
		}

		.btn-custom {
			flex: 1;
			padding: 12px;
			border-radius: 6px;
			text-decoration: none;
			font-weight: 600;
			font-size: 14px;
			transition: all 0.3s ease;
			border: none;
			cursor: pointer;
		}

		.btn-primary-custom {
			background: linear-gradient(135deg, var(--primary-color), #3b82f6);
			color: white;
		}

		.btn-primary-custom:hover {
			transform: translateY(-2px);
			box-shadow: 0 10px 20px rgba(30, 64, 175, 0.3);
			color: white;
		}

		.note {
			background: #dbeafe;
			border-left: 4px solid var(--primary-color);
			padding: 15px;
			border-radius: 4px;
			color: #1e40af;
			font-size: 13px;
			margin-top: 20px;
			text-align: left;
		}

		@media (max-width: 600px) {
			.success-container {
				padding: 40px 25px;
			}

			.success-title {
				font-size: 24px;
			}

			.success-icon {
				font-size: 60px;
			}
		}
	</style>
</head>
<body>
<div class="success-container">
	<div class="success-icon">✓</div>
	<div class="success-title">Registration Successful!</div>

	<div class="success-message">
		Welcome to <strong>MyFin Bank</strong>, <c:out value="${customer.firstName}"/>!<br>
		Your account has been created successfully.
	</div>

	<div class="customer-info">
		<div class="info-item">
			<span class="info-label">Name:</span>
			<span class="info-value"><c:out value="${customer.firstName} ${customer.lastName}"/></span>
		</div>
		<div class="info-item">
			<span class="info-label">Username:</span>
			<span class="info-value"><c:out value="${customer.userName}"/></span>
		</div>
		<div class="info-item">
			<span class="info-label">Email:</span>
			<span class="info-value"><c:out value="${customer.email}"/></span>
		</div>
		<div class="info-item">
			<span class="info-label">Account Type:</span>
			<span class="info-value"><c:out value="${customer.accountType}"/></span>
		</div>
		<div class="info-item">
			<span class="info-label">Status:</span>
			<span class="info-value"><c:out value="${customer.status}"/></span>
		</div>
	</div>

	<div class="status-badge">⏳ Awaiting Admin Approval</div>

	<div class="note">
		<strong>Note:</strong> Your account is currently <strong>INACTIVE</strong>.
		An admin will review your registration and approve it shortly.
		Once approved, your account number will be generated and your account will be activated.
		We will notify you via email once your account is activated.
	</div>

	<div class="action-buttons">
		<a href="${pageContext.request.contextPath}/customer/register" class="btn-custom btn-primary-custom">
			Register Another Account
		</a>
	</div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
</body>
</html>

