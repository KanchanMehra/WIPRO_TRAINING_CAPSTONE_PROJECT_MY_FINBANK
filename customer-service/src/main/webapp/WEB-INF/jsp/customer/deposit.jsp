<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>MyFin Bank - Deposit Money</title>
	<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
	<style>
		body {
			background: #f0f9ff;
			min-height: 100vh;
			font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
		}
		.navbar-custom {
			background: linear-gradient(135deg, #0891b2 0%, #06b6d4 100%);
		}
		.navbar-custom .navbar-brand,
		.navbar-custom .nav-link,
		.navbar-custom .navbar-text {
			color: #f0f9ff !important;
		}
		.container-deposit {
			padding: 30px 20px;
			max-width: 600px;
			margin: 0 auto;
		}
		.card {
			border-radius: 12px;
			box-shadow: 0 10px 30px rgba(0, 0, 0, 0.08);
			border: none;
		}
		.card-header-custom {
			background: linear-gradient(135deg, #0ea5e9 0%, #0284c7 100%);
			color: white;
			font-weight: 600;
			border-radius: 12px 12px 0 0 !important;
			padding: 20px;
		}
		.current-balance {
			background: #ecfdf5;
			border-left: 4px solid #10b981;
			padding: 15px;
			border-radius: 6px;
			margin-bottom: 20px;
		}
		.current-balance-label {
			font-size: 12px;
			color: #059669;
			text-transform: uppercase;
			font-weight: 600;
			margin-bottom: 5px;
		}
		.current-balance-amount {
			font-size: 32px;
			font-weight: 700;
			color: #10b981;
		}
		.form-group {
			margin-bottom: 20px;
		}
		.form-group label {
			display: block;
			margin-bottom: 8px;
			color: #1f2937;
			font-weight: 600;
			font-size: 14px;
		}
		.form-group input[type="number"],
		.form-group input[type="text"] {
			width: 100%;
			padding: 12px 15px;
			border: 2px solid #e5e7eb;
			border-radius: 6px;
			font-size: 16px;
			transition: all 0.3s ease;
			background-color: #f9fafb;
		}
		.form-group input[type="number"]:focus,
		.form-group input[type="text"]:focus {
			outline: none;
			border-color: #0891b2;
			background-color: white;
			box-shadow: 0 0 0 3px rgba(8, 145, 178, 0.1);
		}
		.submit-btn {
			width: 100%;
			padding: 13px;
			background: linear-gradient(135deg, #10b981, #059669);
			color: white;
			border: none;
			border-radius: 6px;
			font-size: 16px;
			font-weight: 600;
			cursor: pointer;
			transition: all 0.3s ease;
			margin-top: 10px;
		}
		.submit-btn:hover {
			transform: translateY(-2px);
			box-shadow: 0 10px 20px rgba(16, 185, 129, 0.3);
		}
		.error-message {
			margin-bottom: 20px;
			color: #b91c1c;
			background-color: #fee2e2;
			border: 1px solid #fecaca;
			border-radius: 6px;
			padding: 12px 15px;
			font-size: 14px;
			display: flex;
			align-items: center;
		}
		.error-message::before {
			content: "⚠️";
			margin-right: 10px;
			font-size: 18px;
		}
		.back-button {
			display: inline-block;
			margin-top: 20px;
			color: #0891b2;
			text-decoration: none;
			font-weight: 600;
		}
		.back-button:hover {
			text-decoration: underline;
		}
		.helper-text {
			font-size: 13px;
			color: #6b7280;
			margin-top: 5px;
		}
	</style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-custom">
	<div class="container-fluid">
		<a class="navbar-brand" href="${pageContext.request.contextPath}/customer/dashboard">
			🏦 MyFin Bank
		</a>
		<div class="d-flex align-items-center">
			<span class="navbar-text me-3">
				${sessionScope.loggedInCustomer.firstName} ${sessionScope.loggedInCustomer.lastName}
			</span>
			<a href="${pageContext.request.contextPath}/customer/logout" class="btn btn-outline-light btn-sm">Logout</a>
		</div>
	</div>
</nav>

<div class="container-deposit">
	<div class="card">
		<div class="card-header-custom">
			<h4 class="mb-0">💰 Deposit Money</h4>
		</div>
		<div class="card-body p-4">
			<!-- Current Balance Display -->
			<div class="current-balance">
				<div class="current-balance-label">Current Account Balance</div>
				<div class="current-balance-amount">₹${customer.amount}</div>
			</div>

			<!-- Error Message -->
			<c:if test="${not empty error}">
				<div class="error-message">${error}</div>
			</c:if>

			<!-- Deposit Form -->
			<form method="post" action="${pageContext.request.contextPath}/customer/deposit">
				<div class="form-group">
					<label for="amount">Deposit Amount</label>
					<input type="number"
					       id="amount"
					       name="amount"
					       step="0.01"
					       placeholder="Enter amount to deposit"
					       required
					       autofocus
					       min="0.01"/>
					<div class="helper-text">
						✓ Enter the amount you want to deposit (e.g., 5000, 2500.50)
					</div>
				</div>

				<button type="submit" class="submit-btn">💸 Deposit Now</button>
			</form>

			<!-- Back to Dashboard -->
			<a href="${pageContext.request.contextPath}/customer/dashboard" class="back-button">
				← Back to Dashboard
			</a>
		</div>
	</div>

	<!-- Information Card -->
	<div class="card" style="margin-top: 20px;">
		<div class="card-body p-4" style="background: linear-gradient(135deg, #f3f4f6 0%, #e5e7eb 100%); border-radius: 0 0 12px 12px;">
			<h6 style="color: #1f2937; font-weight: 600; margin-bottom: 10px;">📋 About Deposits</h6>
			<ul style="font-size: 13px; color: #4b5563; margin-bottom: 0; padding-left: 20px;">
				<li>Deposit amount should be greater than ₹0</li>
				<li>You will receive a Transaction ID for this deposit</li>
				<li>Your new balance will be updated immediately</li>
				<li>Deposits are credited instantly to your account</li>
			</ul>
		</div>
	</div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
</body>
</html>

