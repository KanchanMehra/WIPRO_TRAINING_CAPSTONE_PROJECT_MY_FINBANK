<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>MyFin Bank - Investment Created Successfully</title>
	<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
	<style>
		body {
			background: #f0f9ff;
			min-height: 100vh;
			font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
		}
		.navbar-custom {
			background: linear-gradient(135deg, #10b981 0%, #059669 100%);
		}
		.navbar-custom .navbar-brand, .navbar-custom .nav-link, .navbar-custom .navbar-text {
			color: #f0f9ff !important;
		}
		.container-success {
			padding: 40px 20px;
			max-width: 800px;
			margin: 0 auto;
		}
		.success-card {
			background: white;
			border-radius: 12px;
			box-shadow: 0 5px 20px rgba(0, 0, 0, 0.08);
			padding: 40px;
			text-align: center;
		}
		.success-icon {
			font-size: 64px;
			margin-bottom: 20px;
			animation: scaleIn 0.5s ease-in;
		}
		@keyframes scaleIn {
			from { transform: scale(0); opacity: 0; }
			to { transform: scale(1); opacity: 1; }
		}
		.success-title {
			color: #10b981;
			font-size: 28px;
			font-weight: 700;
			margin-bottom: 10px;
		}
		.details-card {
			background: #d1fae5;
			border-left: 4px solid #10b981;
			padding: 25px;
			border-radius: 8px;
			margin: 30px 0;
			text-align: left;
		}
		.detail-row {
			display: flex;
			justify-content: space-between;
			padding: 12px 0;
			border-bottom: 1px solid #a7f3d0;
		}
		.detail-row:last-child { border-bottom: none; }
		.detail-label {
			font-weight: 600;
			color: #065f46;
		}
		.detail-value {
			color: #10b981;
			font-weight: 700;
		}
		.action-buttons {
			display: flex;
			gap: 10px;
			justify-content: center;
			margin-top: 30px;
			flex-wrap: wrap;
		}
		.btn-custom {
			padding: 12px 24px;
			border-radius: 6px;
			font-weight: 600;
			text-decoration: none;
			border: none;
			cursor: pointer;
		}
		.btn-primary-custom {
			background: #10b981;
			color: white;
		}
		.btn-primary-custom:hover {
			background: #059669;
		}
		.btn-secondary-custom {
			background: #e5e7eb;
			color: #1f2937;
		}
		.btn-secondary-custom:hover {
			background: #d1d5db;
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
				${customer.firstName} ${customer.lastName}
			</span>
			<a href="${pageContext.request.contextPath}/customer/logout" class="btn btn-outline-light btn-sm">Logout</a>
		</div>
	</div>
</nav>

<div class="container-success">
	<div class="success-card">
		<div class="success-icon">✅</div>
		<h2 class="success-title">
			<c:choose>
				<c:when test="${type == 'RD'}">Recurring Deposit Created Successfully!</c:when>
				<c:when test="${type == 'FD'}">Fixed Deposit Created Successfully!</c:when>
				<c:otherwise>Investment Created Successfully!</c:otherwise>
			</c:choose>
		</h2>
		<p class="text-muted">Your investment has been created and money has been deducted from your account</p>

		<div class="details-card">
			<c:choose>
				<c:when test="${type == 'RD'}">
					<h4 style="margin-bottom: 15px; color: #065f46;">Recurring Deposit Details</h4>
					<div class="detail-row">
						<span class="detail-label">RD Account Number</span>
						<span class="detail-value">${rd.rdAccountNo}</span>
					</div>
					<div class="detail-row">
						<span class="detail-label">Monthly Installment</span>
						<span class="detail-value">₹${String.format("%.2f", rd.monthlyInstallment)}</span>
					</div>
					<div class="detail-row">
						<span class="detail-label">Duration</span>
						<span class="detail-value">${rd.durationInMonths} Months</span>
					</div>
					<div class="detail-row">
						<span class="detail-label">Interest Rate</span>
						<span class="detail-value">${String.format("%.2f", rd.interestRate)}% p.a.</span>
					</div>
					<div class="detail-row">
						<span class="detail-label">Maturity Amount</span>
						<span class="detail-value">₹${String.format("%.2f", rd.maturityAmount)}</span>
					</div>
					<div class="detail-row">
						<span class="detail-label">Start Date</span>
						<span class="detail-value">${rd.startDate}</span>
					</div>
					<div class="detail-row">
						<span class="detail-label">Maturity Date</span>
						<span class="detail-value">${rd.maturityDate}</span>
					</div>
					<div class="detail-row">
						<span class="detail-label">Status</span>
						<span class="detail-value">ACTIVE</span>
					</div>
				</c:when>
				<c:when test="${type == 'FD'}">
					<h4 style="margin-bottom: 15px; color: #065f46;">Fixed Deposit Details</h4>
					<div class="detail-row">
						<span class="detail-label">FD Account Number</span>
						<span class="detail-value">${fd.fdAccountNo}</span>
					</div>
					<div class="detail-row">
						<span class="detail-label">Principal Amount</span>
						<span class="detail-value">₹${String.format("%.2f", fd.principalAmount)}</span>
					</div>
					<div class="detail-row">
						<span class="detail-label">Duration</span>
						<span class="detail-value">${fd.durationInMonths} Months</span>
					</div>
					<div class="detail-row">
						<span class="detail-label">Interest Rate</span>
						<span class="detail-value">${String.format("%.2f", fd.interestRate)}% p.a.</span>
					</div>
					<div class="detail-row">
						<span class="detail-label">Maturity Amount</span>
						<span class="detail-value">₹${String.format("%.2f", fd.maturityAmount)}</span>
					</div>
					<div class="detail-row">
						<span class="detail-label">Start Date</span>
						<span class="detail-value">${fd.startDate}</span>
					</div>
					<div class="detail-row">
						<span class="detail-label">Maturity Date</span>
						<span class="detail-value">${fd.maturityDate}</span>
					</div>
					<div class="detail-row">
						<span class="detail-label">Status</span>
						<span class="detail-value">ACTIVE</span>
					</div>
				</c:when>
			</c:choose>
		</div>

		<div class="action-buttons">
			<c:choose>
				<c:when test="${type == 'RD'}">
					<a href="${pageContext.request.contextPath}/customer/investment/rd/my-rds" class="btn-custom btn-primary-custom">
						View My RDs
					</a>
				</c:when>
				<c:when test="${type == 'FD'}">
					<a href="${pageContext.request.contextPath}/customer/investment/fd/my-fds" class="btn-custom btn-primary-custom">
						View My FDs
					</a>
				</c:when>
			</c:choose>
			<a href="${pageContext.request.contextPath}/customer/dashboard" class="btn-custom btn-secondary-custom">
				Back to Dashboard
			</a>
		</div>
	</div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
</body>
</html>

