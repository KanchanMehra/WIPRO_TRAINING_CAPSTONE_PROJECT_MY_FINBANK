<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>MyFin Bank - Customer Registration</title>
	<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
	<style>
		:root {
			--primary-color: #1e40af;
			--secondary-color: #1f2937;
			--accent-color: #3b82f6;
			--success-color: #10b981;
		}

		* {
			margin: 0;
			padding: 0;
			box-sizing: border-box;
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
		}

		.registration-header h1 {
			color: var(--primary-color);
			font-size: 28px;
			font-weight: 700;
			margin-bottom: 10px;
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
		.form-group input[type="number"],
		.form-group input[type="date"],
		.form-group select {
			width: 100%;
			padding: 12px 15px;
			border: 1px solid #d1d5db;
			border-radius: 6px;
			font-size: 14px;
			transition: all 0.3s ease;
			background-color: #f9fafb;
		}

		.form-group input[type="text"]:focus,
		.form-group input[type="email"]:focus,
		.form-group input[type="password"]:focus,
		.form-group input[type="number"]:focus,
		.form-group input[type="date"]:focus,
		.form-group select:focus {
			outline: none;
			border-color: var(--accent-color);
			background-color: white;
			box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
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

		.checkbox-group {
			display: flex;
			align-items: center;
			gap: 10px;
			margin-top: 8px;
		}

		.form-group input[type="checkbox"] {
			width: 18px;
			height: 18px;
			cursor: pointer;
		}

		.checkbox-group label {
			margin-bottom: 0;
			cursor: pointer;
			color: var(--secondary-color);
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
			box-shadow: 0 10px 20px rgba(30, 64, 175, 0.3);
		}

		.submit-btn:active {
			transform: translateY(0);
		}

		.form-divider {
			margin: 30px 0;
			border-top: 1px solid #e5e7eb;
			position: relative;
		}

		.form-divider::after {
			content: "Account Information";
			position: absolute;
			top: -12px;
			left: 50%;
			transform: translateX(-50%);
			background: white;
			padding: 0 10px;
			color: #9ca3af;
			font-size: 12px;
			font-weight: 600;
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
		<h1>🏦 MyFin Bank</h1>
		<p>Create your account and start banking with us today</p>
	</div>

	<form:form method="post" modelAttribute="customer" action="${pageContext.request.contextPath}/customer/register" class="registration-form">

		<!-- Personal Information -->
		<div class="form-divider"></div>
		<div class="form-row">
			<div class="form-group">
				<label for="firstName">First Name *</label>
				<form:input path="firstName" id="firstName" required="true"/>
			</div>
			<div class="form-group">
				<label for="lastName">Last Name *</label>
				<form:input path="lastName" id="lastName" required="true"/>
			</div>
		</div>

		<div class="form-group">
			<label for="dob">Date of Birth *</label>
			<form:input path="dob" id="dob" type="date" required="true"/>
		</div>

		<!-- Address Information -->
		<div class="form-divider"></div>
		<div class="form-group">
			<label for="address1">Address Line 1 *</label>
			<form:input path="address1" id="address1" required="true"/>
		</div>

		<div class="form-group">
			<label for="address2">Address Line 2</label>
			<form:input path="address2" id="address2"/>
		</div>

		<div class="form-row">
			<div class="form-group">
				<label for="city">City *</label>
				<form:input path="city" id="city" required="true"/>
			</div>
			<div class="form-group">
				<label for="state">State *</label>
				<form:input path="state" id="state" required="true"/>
			</div>
		</div>

		<div class="form-row">
			<div class="form-group">
				<label for="zipCode">Zip Code *</label>
				<form:input path="zipCode" id="zipCode" required="true"/>
			</div>
			<div class="form-group">
				<label for="country">Country *</label>
				<form:input path="country" id="country" required="true"/>
			</div>
		</div>

		<!-- Account Information -->
		<div class="form-divider"></div>
		<div class="form-row">
			<div class="form-group">
				<label for="amount">Initial Amount *</label>
				<form:input path="amount" id="amount" type="number" step="0.01" required="true"/>
			</div>
			<div class="form-group">
				<label for="accountType">Account Type *</label>
				<form:select path="accountType" id="accountType" required="true">
					<form:option value="" label="--Select--"/>
					<form:option value="SAVINGS" label="Savings"/>
					<form:option value="CURRENT" label="Current"/>
				</form:select>
			</div>
		</div>

		<div class="form-group">
			<div class="checkbox-group">
				<form:checkbox path="cheqFacil" id="cheqFacil"/>
				<label for="cheqFacil">Enable Cheque Facility</label>
			</div>
		</div>

		<!-- Login Information -->
		<div class="form-divider"></div>
		<div class="form-group">
			<label for="userName">Username *</label>
			<form:input path="userName" id="userName" required="true"/>
		</div>

		<div class="form-group">
			<label for="password">Password *</label>
			<form:password path="password" id="password" required="true"/>
		</div>

		<!-- Contact Information -->
		<div class="form-divider"></div>
		<div class="form-row">
			<div class="form-group">
				<label for="mobileNo">Mobile Number *</label>
				<form:input path="mobileNo" id="mobileNo" required="true"/>
			</div>
			<div class="form-group">
				<label for="email">Email *</label>
				<form:input path="email" id="email" type="email" required="true"/>
			</div>
		</div>

		<button type="submit" class="submit-btn">Create Account</button>
	</form:form>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
</body>
</html>

