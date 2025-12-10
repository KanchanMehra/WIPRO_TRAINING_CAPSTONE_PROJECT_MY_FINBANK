<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>MyFin Bank - Admin Registration</title>
	<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
	<style>
		:root {
			--primary-color: #dc2626;
			--secondary-color: #1f2937;
			--accent-color: #ef4444;
			--success-color: #10b981;
		}

		* {
			margin: 0;
			padding: 0;
			box-sizing: border-box;
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

		.registration-container {
			background: white;
			border-radius: 12px;
			box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
			width: 100%;
			max-width: 600px;
			padding: 40px;
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

		.registration-header {
			text-align: center;
			margin-bottom: 30px;
			border-bottom: 3px solid var(--primary-color);
			padding-bottom: 20px;
		}

		.registration-header h1 {
			color: var(--primary-color);
			font-size: 28px;
			font-weight: 700;
			margin-bottom: 10px;
		}

		.registration-header .badge-admin {
			display: inline-block;
			background: var(--primary-color);
			color: white;
			padding: 8px 16px;
			border-radius: 20px;
			font-size: 12px;
			font-weight: 600;
			margin-top: 10px;
		}

		.registration-header p {
			color: #6b7280;
			font-size: 14px;
		}

		.form-group {
			margin-bottom: 20px;
		}

		.form-group label {
			display: block;
			margin-bottom: 8px;
			color: var(--secondary-color);
			font-weight: 500;
			font-size: 14px;
		}

		.form-group input[type="text"],
		.form-group input[type="email"],
		.form-group input[type="password"],
		.form-group input[type="tel"] {
			width: 100%;
			padding: 12px 15px;
			border: 2px solid #e5e7eb;
			border-radius: 6px;
			font-size: 14px;
			transition: all 0.3s ease;
			background-color: #f9fafb;
		}

		.form-group input[type="text"]:focus,
		.form-group input[type="email"]:focus,
		.form-group input[type="password"]:focus,
		.form-group input[type="tel"]:focus {
			outline: none;
			border-color: var(--primary-color);
			background-color: white;
			box-shadow: 0 0 0 3px rgba(220, 38, 38, 0.1);
		}

		.form-row {
			display: grid;
			grid-template-columns: 1fr 1fr;
			gap: 20px;
		}

		.form-row .form-group {
			margin-bottom: 0;
		}

		.form-group-full {
			grid-column: 1 / -1;
		}

		.form-divider {
			margin: 25px 0;
			border-top: 2px solid #fecaca;
			position: relative;
		}

		.form-divider::after {
			content: "Admin Credentials";
			position: absolute;
			top: -12px;
			left: 50%;
			transform: translateX(-50%);
			background: white;
			padding: 0 10px;
			color: var(--primary-color);
			font-size: 12px;
			font-weight: 600;
		}

		.submit-btn {
			width: 100%;
			padding: 12px;
			background: linear-gradient(135deg, var(--primary-color), var(--accent-color));
			color: white;
			border: none;
			border-radius: 6px;
			font-size: 16px;
			font-weight: 600;
			cursor: pointer;
			transition: all 0.3s ease;
			margin-top: 20px;
		}

		.submit-btn:hover {
			transform: translateY(-2px);
			box-shadow: 0 10px 20px rgba(220, 38, 38, 0.3);
		}

		.submit-btn:active {
			transform: translateY(0);
		}

		@media (max-width: 600px) {
			.registration-container {
				padding: 30px 20px;
			}

			.form-row {
				grid-template-columns: 1fr;
			}

			.form-row .form-group {
				margin-bottom: 20px;
			}

			.registration-header h1 {
				font-size: 24px;
			}
		}
	</style>
</head>
<body>
<div class="registration-container">
	<div class="registration-header">
		<h1>🔐 MyFin Bank</h1>
		<div class="badge-admin">ADMIN PORTAL</div>
		<p>Register as an Administrator</p>
	</div>

	<form:form method="post" modelAttribute="admin" action="${pageContext.request.contextPath}/admin/register" class="registration-form">

	<!-- Personal Information -->
	<div class="form-divider"></div>
	<div class="form-group">
		<label for="adminName">Admin Name *</label>
		<form:input path="adminName" id="adminName" required="true"/>
	</div>

		<!-- Contact Information -->
		<div class="form-group">
			<label for="adminEmail">Admin Email *</label>
			<form:input path="adminEmail" id="adminEmail" type="email" required="true"/>
		</div>

		<div class="form-group">
			<label for="adminPhone">Admin Phone *</label>
			<form:input path="adminPhone" id="adminPhone" type="tel" required="true"/>
		</div>

		<!-- Login Credentials -->
		<div class="form-divider"></div>
		<div class="form-group">
			<label for="adminUserName">Username *</label>
			<form:input path="adminUserName" id="adminUserName" required="true"/>
		</div>

		<div class="form-group">
			<label for="adminPassword">Password *</label>
			<form:password path="adminPassword" id="adminPassword" required="true"/>
		</div>

		<button type="submit" class="submit-btn">Register Admin Account</button>
	</form:form>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
</body>
</html>

