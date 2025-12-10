<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>MyFin Bank - Pay Loan EMI</title>
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
		.container-emi {
			padding: 40px 20px;
			max-width: 1000px;
			margin: 0 auto;
		}
		.page-header {
			text-align: center;
			margin-bottom: 30px;
		}
		.page-header h2 {
			color: #1f2937;
			font-weight: 700;
			font-size: 32px;
		}
		.alert {
			border-radius: 8px;
			padding: 12px 15px;
			margin-bottom: 20px;
		}
		.loan-card {
			background: white;
			border-radius: 12px;
			box-shadow: 0 5px 20px rgba(0, 0, 0, 0.08);
			padding: 20px;
			margin-bottom: 20px;
			border-left: 4px solid #0891b2;
		}
		.loan-header {
			display: flex;
			justify-content: space-between;
			align-items: center;
			margin-bottom: 15px;
		}
		.loan-app-no {
			font-size: 14px;
			color: #6b7280;
			font-weight: 600;
			font-family: 'Courier New', monospace;
		}
		.loan-amount {
			font-size: 24px;
			font-weight: 700;
			color: #0891b2;
		}
		.loan-details {
			display: grid;
			grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
			gap: 15px;
			margin-bottom: 15px;
		}
		.detail-item {
			display: flex;
			flex-direction: column;
		}
		.detail-label {
			font-size: 12px;
			color: #6b7280;
			text-transform: uppercase;
			font-weight: 600;
			margin-bottom: 5px;
		}
		.detail-value {
			font-size: 16px;
			color: #1f2937;
			font-weight: 600;
		}
		.emi-highlight {
			background: #dbeafe;
			padding: 15px;
			border-radius: 8px;
			text-align: center;
			margin: 15px 0;
		}
		.emi-label {
			font-size: 12px;
			color: #1e40af;
			text-transform: uppercase;
			font-weight: 600;
		}
		.emi-value {
			font-size: 28px;
			font-weight: 700;
			color: #0284c7;
			margin: 5px 0;
		}
		.progress-bar-custom {
			height: 25px;
			background: #e5e7eb;
			border-radius: 12px;
			overflow: hidden;
			position: relative;
		}
		.progress-fill {
			height: 100%;
			background: linear-gradient(135deg, #10b981 0%, #059669 100%);
			transition: width 0.3s;
		}
		.progress-text {
			position: absolute;
			top: 50%;
			left: 50%;
			transform: translate(-50%, -50%);
			font-size: 12px;
			font-weight: 600;
			color: #1f2937;
		}
		.btn-pay {
			background: #10b981;
			color: white;
			border: none;
			padding: 10px 20px;
			border-radius: 6px;
			font-weight: 600;
			cursor: pointer;
		}
		.btn-pay:hover {
			background: #059669;
		}
		.btn-pay:disabled {
			background: #d1d5db;
			cursor: not-allowed;
		}
		.empty-state {
			text-align: center;
			padding: 60px 20px;
			background: white;
			border-radius: 12px;
			box-shadow: 0 5px 20px rgba(0, 0, 0, 0.08);
		}
		.empty-state-icon {
			font-size: 64px;
			color: #d1d5db;
			margin-bottom: 20px;
		}
		.back-button {
			display: inline-block;
			color: #0891b2;
			text-decoration: none;
			font-weight: 600;
			margin-top: 20px;
		}
		.back-button:hover {
			text-decoration: underline;
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

<div class="container-emi">
	<div class="page-header">
		<h2>💰 Pay Loan EMI</h2>
		<p class="text-muted">Manage your loan EMI payments</p>
	</div>

	<c:if test="${not empty success}">
		<div class="alert alert-success">${success}</div>
	</c:if>
	<c:if test="${not empty error}">
		<div class="alert alert-danger">${error}</div>
	</c:if>

	<c:choose>
		<c:when test="${empty loans}">
			<div class="empty-state">
				<div class="empty-state-icon">📭</div>
				<h4>No Approved Loans</h4>
				<p>You don't have any approved loans that require EMI payments.</p>
				<a href="${pageContext.request.contextPath}/customer/loan/apply" class="btn btn-primary">
					Apply for Loan
				</a>
			</div>
		</c:when>
		<c:otherwise>
			<c:forEach var="loan" items="${loans}">
				<div class="loan-card">
					<div class="loan-header">
						<div>
							<div class="loan-app-no">ID: ${loan.loanApplicationNo}</div>
							<div class="loan-amount">₹${String.format("%.2f", loan.loanAmount)}</div>
						</div>
					</div>

					<div class="emi-highlight">
						<div class="emi-label">Monthly EMI</div>
						<div class="emi-value">₹${String.format("%.2f", loan.emiAmount)}</div>
					</div>

					<div class="loan-details">
						<div class="detail-item">
							<span class="detail-label">Total EMIs</span>
							<span class="detail-value">${loan.totalEmis}</span>
						</div>
						<div class="detail-item">
							<span class="detail-label">Paid EMIs</span>
							<span class="detail-value">${loan.paidEmis}</span>
						</div>
						<div class="detail-item">
							<span class="detail-label">Remaining EMIs</span>
							<span class="detail-value">${loan.remainingEmis}</span>
						</div>
						<div class="detail-item">
							<span class="detail-label">Outstanding</span>
							<span class="detail-value">₹${String.format("%.2f", loan.emiAmount * loan.remainingEmis)}</span>
						</div>
					</div>

					<!-- Progress Bar -->
					<div style="margin: 20px 0;">
						<div class="detail-label" style="margin-bottom: 10px;">Payment Progress</div>
						<div class="progress-bar-custom">
							<div class="progress-fill" style="width: ${(loan.paidEmis * 100.0) / loan.totalEmis}%"></div>
							<div class="progress-text">${loan.paidEmis} / ${loan.totalEmis} EMIs Paid</div>
						</div>
					</div>

					<!-- Pay Button -->
					<form method="post" action="${pageContext.request.contextPath}/customer/investment/emi/pay/${loan.id}"
					      onsubmit="return confirm('Are you sure you want to pay this EMI?');">
						<button type="submit" class="btn-pay">
							💳 Pay EMI (₹${String.format("%.2f", loan.emiAmount)})
						</button>
					</form>
				</div>
			</c:forEach>
		</c:otherwise>
	</c:choose>

	<a href="${pageContext.request.contextPath}/customer/dashboard" class="back-button">
		← Back to Dashboard
	</a>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
</body>
</html>

