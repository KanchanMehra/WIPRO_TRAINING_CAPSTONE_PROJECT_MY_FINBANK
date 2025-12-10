<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Admin Registration Successful - MyFin Bank</title>
	<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
	<style>
		:root {
			--primary-color: #dc2626;
			--success-color: #10b981;
		}

		body {
			background: linear-gradient(135deg, #991b1b 0%, #7f1d1d 100%);
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

		.admin-info {
			background: #fef2f2;
			border-left: 4px solid var(--primary-color);
			padding: 20px;
			border-radius: 8px;
			margin-bottom: 30px;
			text-align: left;
		}

		.info-item {
			display: flex;
			justify-content: space-between;
			padding: 10px 0;
			border-bottom: 1px solid #fecaca;
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
			background: #dcfce7;
			color: #166534;
			padding: 8px 16px;
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
			background: linear-gradient(135deg, var(--primary-color), #ef4444);
			color: white;
		}

		.btn-primary-custom:hover {
			transform: translateY(-2px);
			box-shadow: 0 10px 20px rgba(220, 38, 38, 0.3);
			color: white;
		}

		.note {
			background: #fee2e2;
			border-left: 4px solid var(--primary-color);
			padding: 15px;
			border-radius: 4px;
			color: #7f1d1d;
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
	<div class="success-title">Admin Registration Successful!</div>

	<div class="success-message">
		Welcome to <strong>MyFin Bank Admin Portal</strong>!<br>
		Your admin account has been created successfully.
	</div>

	<div class="admin-info">
		<div class="info-item">
			<span class="info-label">Admin ID:</span>
			<span class="info-value"><c:out value="${admin.adminId}"/></span>
		</div>
		<div class="info-item">
			<span class="info-label">Name:</span>
			<span class="info-value"><c:out value="${admin.adminName}"/></span>
		</div>
		<div class="info-item">
			<span class="info-label">Username:</span>
			<span class="info-value"><c:out value="${admin.adminUserName}"/></span>
		</div>
		<div class="info-item">
			<span class="info-label">Email:</span>
			<span class="info-value"><c:out value="${admin.adminEmail}"/></span>
		</div>
		<div class="info-item">
			<span class="info-label">Phone:</span>
			<span class="info-value"><c:out value="${admin.adminPhone}"/></span>
		</div>
		<div class="info-item">
			<span class="info-label">Status:</span>
			<span class="info-value"><c:out value="${admin.adminStatus}"/></span>
		</div>
	</div>

	<div class="status-badge">✓ Account Created Successfully</div>

	<div class="note">
		<strong>Note:</strong> Your admin account is now <strong>ACTIVE</strong>.
		You can login to the admin dashboard to manage customers, approve registrations,
		generate account numbers, and perform other administrative tasks.
	</div>

	<div class="action-buttons">
		<a href="${pageContext.request.contextPath}/admin/register" class="btn-custom btn-primary-custom">
			Register Another Admin
		</a>
	</div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
</body>
</html>

