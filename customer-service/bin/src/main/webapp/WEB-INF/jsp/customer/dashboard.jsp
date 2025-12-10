<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>MyFin Bank - Customer Dashboard</title>
	<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
	<style>
		body {
			background: #f0f9ff;
			min-height: 100vh;
			font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
		}
		.navbar-custom {
			background: linear-gradient(135deg, #0891b2 0%, #06b6d4 100%);
			box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
		}
		.navbar-custom .navbar-brand,
		.navbar-custom .nav-link,
		.navbar-custom .navbar-text {
			color: #f0f9ff !important;
		}
		.dashboard-container {
			padding: 30px 20px;
		}
		.welcome-banner {
			background: linear-gradient(135deg, #0891b2 0%, #06b6d4 100%);
			color: white;
			padding: 30px;
			border-radius: 12px;
			margin-bottom: 30px;
			box-shadow: 0 10px 30px rgba(8, 145, 178, 0.2);
		}
		.welcome-banner h2 {
			font-size: 32px;
			font-weight: 700;
			margin-bottom: 5px;
		}
		.welcome-banner p {
			font-size: 16px;
			opacity: 0.9;
			margin-bottom: 0;
		}
		.card {
			border-radius: 12px;
			box-shadow: 0 5px 20px rgba(0, 0, 0, 0.08);
			border: none;
			margin-bottom: 20px;
			transition: transform 0.2s;
		}
		.card:hover {
			transform: translateY(-5px);
		}
		.card-header-custom {
			background: linear-gradient(135deg, #0ea5e9 0%, #0284c7 100%);
			color: white;
			font-weight: 600;
			border-radius: 12px 12px 0 0 !important;
			padding: 15px 20px;
		}
		.account-number {
			font-size: 24px;
			font-weight: 700;
			color: #0891b2;
			letter-spacing: 2px;
		}
		.balance-amount {
			font-size: 36px;
			font-weight: 700;
			color: #10b981;
		}
		.info-label {
			font-size: 12px;
			color: #6b7280;
			text-transform: uppercase;
			font-weight: 600;
			margin-bottom: 5px;
		}
		.info-value {
			font-size: 16px;
			color: #1f2937;
			font-weight: 500;
			margin-bottom: 15px;
		}
		.badge-account-type {
			background: #3b82f6;
			color: white;
			padding: 8px 15px;
			border-radius: 20px;
			font-size: 14px;
			font-weight: 600;
		}
		.badge-status {
			background: #10b981;
			color: white;
			padding: 8px 15px;
			border-radius: 20px;
			font-size: 14px;
			font-weight: 600;
		}
		.quick-action-btn {
			width: 100%;
			padding: 15px;
			border-radius: 8px;
			font-weight: 600;
			margin-bottom: 10px;
			transition: all 0.3s;
		}
		.quick-action-btn.deposit {
			background: #10b981;
			color: white;
			border: none;
		}
		.quick-action-btn.withdraw {
			background: #f59e0b;
			color: white;
			border: none;
		}
		.quick-action-btn.transfer {
			background: #3b82f6;
			color: white;
			border: none;
		}
		.quick-action-btn:hover:not(:disabled) {
			transform: translateY(-2px);
			box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
		}
		.quick-action-btn:disabled {
			opacity: 0.6;
			cursor: not-allowed;
		}
	</style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-custom">
	<div class="container-fluid">
		<a class="navbar-brand" href="${pageContext.request.contextPath}/customer/dashboard">
			🏦 MyFin Bank - Customer Portal
		</a>
		<div class="d-flex align-items-center">
			<span class="navbar-text me-3">
				Welcome,
				<c:choose>
					<c:when test="${not empty sessionScope.loggedInCustomer}">
						<strong>${sessionScope.loggedInCustomer.firstName} ${sessionScope.loggedInCustomer.lastName}</strong>
					</c:when>
					<c:otherwise>Customer</c:otherwise>
				</c:choose>
			</span>
			<a href="${pageContext.request.contextPath}/customer/logout" class="btn btn-outline-light btn-sm">Logout</a>
		</div>
	</div>
</nav>

<div class="container dashboard-container">
	<!-- Welcome Banner -->
	<div class="welcome-banner">
		<h2>Welcome Back, ${sessionScope.loggedInCustomer.firstName}! 👋</h2>
		<p>Manage your account, transfer funds, and track your transactions</p>
	</div>

	<div class="row g-4">
		<!-- Left Column: Account Summary & Personal Info -->
		<div class="col-lg-6">
			<!-- Account Summary Card -->
			<div class="card">
				<div class="card-header-custom">
					<h5 class="mb-0">💳 Account Summary</h5>
				</div>
				<div class="card-body p-4">
					<div class="row">
						<div class="col-md-6 mb-3">
							<div class="info-label">Account Number</div>
							<div class="account-number">${sessionScope.loggedInCustomer.accountNo}</div>
						</div>
						<div class="col-md-6 mb-3">
							<div class="info-label">Account Type</div>
							<span class="badge-account-type">${sessionScope.loggedInCustomer.accountType}</span>
						</div>
						<div class="col-12 mb-3">
							<div class="info-label">Current Balance</div>
							<div class="balance-amount">₹${sessionScope.loggedInCustomer.amount}</div>
						</div>
						<div class="col-md-6">
							<div class="info-label">Account Status</div>
							<span class="badge-status">ACTIVE</span>
						</div>
						<div class="col-md-6">
							<div class="info-label">Account Opened</div>
							<div class="info-value">${sessionScope.loggedInCustomer.dateOfOpen}</div>
						</div>
					</div>
				</div>
			</div>

			<!-- Personal Information Card -->
			<div class="card">
				<div class="card-header-custom">
					<h5 class="mb-0">👤 Personal Information</h5>
				</div>
				<div class="card-body p-4">
					<div class="row">
						<div class="col-md-6 mb-3">
							<div class="info-label">Full Name</div>
							<div class="info-value">${sessionScope.loggedInCustomer.firstName} ${sessionScope.loggedInCustomer.lastName}</div>
						</div>
						<div class="col-md-6 mb-3">
							<div class="info-label">Username</div>
							<div class="info-value">${sessionScope.loggedInCustomer.userName}</div>
						</div>
						<div class="col-md-6 mb-3">
							<div class="info-label">Email Address</div>
							<div class="info-value">${sessionScope.loggedInCustomer.email}</div>
						</div>
						<div class="col-md-6 mb-3">
							<div class="info-label">Mobile Number</div>
							<div class="info-value">${sessionScope.loggedInCustomer.mobileNo}</div>
						</div>
						<div class="col-12 mb-3">
							<div class="info-label">Address</div>
							<div class="info-value">
								${sessionScope.loggedInCustomer.address1}<br>
								<c:if test="${not empty sessionScope.loggedInCustomer.address2}">
									${sessionScope.loggedInCustomer.address2}<br>
								</c:if>
								${sessionScope.loggedInCustomer.city}, ${sessionScope.loggedInCustomer.state} - ${sessionScope.loggedInCustomer.zipCode}<br>
								${sessionScope.loggedInCustomer.country}
							</div>
						</div>
						<div class="col-md-6">
							<div class="info-label">Date of Birth</div>
							<div class="info-value">${sessionScope.loggedInCustomer.dob}</div>
						</div>
						<div class="col-md-6">
							<div class="info-label">Cheque Facility</div>
							<div class="info-value">
								<c:choose>
									<c:when test="${sessionScope.loggedInCustomer.cheqFacil}">
										✅ Enabled
									</c:when>
									<c:otherwise>
										❌ Not Enabled
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- Right Column: Quick Actions Grid -->
		<div class="col-lg-6">
			<!-- Banking Operations Card -->
			<div class="card">
				<div class="card-header-custom">
					<h5 class="mb-0">💰 Banking Operations</h5>
				</div>
				<div class="card-body p-3">
					<div class="row g-2">
						<div class="col-md-6">
							<button class="quick-action-btn deposit" onclick="location.href='${pageContext.request.contextPath}/customer/deposit'">
								💰 Deposit Money
							</button>
						</div>
						<div class="col-md-6">
							<button class="quick-action-btn withdraw" onclick="location.href='${pageContext.request.contextPath}/customer/withdraw'">
								💸 Withdraw Money
							</button>
						</div>
						<div class="col-md-6">
							<button class="quick-action-btn transfer" onclick="location.href='${pageContext.request.contextPath}/customer/transfer'">
								🔄 Fund Transfer
							</button>
						</div>
						<div class="col-md-6">
							<button class="quick-action-btn" style="background: #6366f1; color: white;" onclick="location.href='${pageContext.request.contextPath}/customer/transactions'">
								📊 Transactions
							</button>
						</div>
					</div>
				</div>
			</div>

			<!-- Loan Services Card -->
			<div class="card">
				<div class="card-header-custom">
					<h5 class="mb-0">🏦 Loan Services</h5>
				</div>
				<div class="card-body p-3">
					<div class="row g-2">
						<div class="col-md-6">
							<button class="quick-action-btn" style="background: #8b5cf6; color: white;" onclick="location.href='${pageContext.request.contextPath}/customer/loan/calculator'">
								🧮 EMI Calculator
							</button>
						</div>
						<div class="col-md-6">
							<button class="quick-action-btn" style="background: #d946ef; color: white;" onclick="location.href='${pageContext.request.contextPath}/customer/loan/apply'">
								📋 Apply for Loan
							</button>
						</div>
						<div class="col-md-6">
							<button class="quick-action-btn" style="background: #ec4899; color: white;" onclick="location.href='${pageContext.request.contextPath}/customer/loan/my-loans'">
								📊 My Loans
							</button>
						</div>
						<div class="col-md-6">
							<button class="quick-action-btn" style="background: #6366f1; color: white;" onclick="location.href='${pageContext.request.contextPath}/customer/investment/emi/pay'">
								💳 Pay EMI
							</button>
						</div>
					</div>
				</div>
			</div>

			<!-- Investment Services Card -->
			<div class="card">
				<div class="card-header-custom">
					<h5 class="mb-0">📈 Investment Services</h5>
				</div>
				<div class="card-body p-3">
					<div class="row g-2">
						<div class="col-md-6">
							<button class="quick-action-btn" style="background: #10b981; color: white;" onclick="location.href='${pageContext.request.contextPath}/customer/investment/rd/create'">
								🏦 Create RD
							</button>
						</div>
						<div class="col-md-6">
							<button class="quick-action-btn" style="background: #059669; color: white;" onclick="location.href='${pageContext.request.contextPath}/customer/investment/rd/my-rds'">
								📊 View RDs
							</button>
						</div>
						<div class="col-md-6">
							<button class="quick-action-btn" style="background: #0891b2; color: white;" onclick="location.href='${pageContext.request.contextPath}/customer/investment/fd/create'">
								💎 Create FD
							</button>
						</div>
						<div class="col-md-6">
							<button class="quick-action-btn" style="background: #0284c7; color: white;" onclick="location.href='${pageContext.request.contextPath}/customer/investment/fd/my-fds'">
								📊 View FDs
							</button>
						</div>
					</div>
				</div>
			</div>

			<!-- Support Card -->
			<div class="card">
				<div class="card-header-custom">
					<h5 class="mb-0">💬 Customer Support</h5>
				</div>
				<div class="card-body p-3">
					<button class="quick-action-btn" style="background: #8b5cf6; color: white;" onclick="location.href='${pageContext.request.contextPath}/customer/chat'">
						💬 Chat with Support
					</button>
				</div>
			</div>
		</div>
	</div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
</body>
</html>

