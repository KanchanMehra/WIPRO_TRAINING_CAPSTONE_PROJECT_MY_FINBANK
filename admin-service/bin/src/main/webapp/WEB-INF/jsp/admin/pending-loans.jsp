<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>MyFin Bank - Pending Loan Applications</title>
	<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
	<style>
		body {
			background: #f0f9ff;
			min-height: 100vh;
			font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
		}
		.navbar-custom {
			background: linear-gradient(135deg, #991b1b 0%, #7f1d1d 100%);
		}
		.navbar-custom .navbar-brand,
		.navbar-custom .nav-link,
		.navbar-custom .navbar-text {
			color: #f9fafb !important;
		}
		.container-loans {
			padding: 30px 20px;
			max-width: 1200px;
			margin: 0 auto;
		}
		.page-header {
			margin-bottom: 30px;
		}
		.page-header h2 {
			color: #1f2937;
			font-weight: 700;
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
			border-left: 4px solid #f59e0b;
		}
		.loan-header {
			display: flex;
			justify-content: space-between;
			align-items: start;
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
			color: #f59e0b;
			margin-bottom: 10px;
		}
		.customer-info {
			background: #f3f4f6;
			padding: 15px;
			border-radius: 6px;
			margin-bottom: 15px;
		}
		.customer-name {
			font-weight: 700;
			color: #1f2937;
			font-size: 16px;
		}
		.customer-email {
			color: #6b7280;
			font-size: 14px;
		}
		.balance-indicator {
			display: inline-block;
			padding: 6px 12px;
			border-radius: 20px;
			font-size: 12px;
			font-weight: 600;
		}
		.balance-sufficient {
			background: #d1fae5;
			color: #065f46;
		}
		.balance-insufficient {
			background: #fee2e2;
			color: #991b1b;
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
		.loan-footer {
			display: flex;
			gap: 10px;
			justify-content: flex-end;
			margin-top: 15px;
			padding-top: 15px;
			border-top: 1px solid #e5e7eb;
		}
		.btn-approve {
			background: #10b981;
			color: white;
			border: none;
			padding: 8px 16px;
			border-radius: 4px;
			font-size: 12px;
			font-weight: 600;
			text-decoration: none;
			cursor: pointer;
		}
		.btn-approve:hover {
			background: #059669;
			color: white;
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
			color: #991b1b;
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
		<a class="navbar-brand" href="${pageContext.request.contextPath}/admin/dashboard">
			🏦 MyFin Bank - Admin Panel
		</a>
		<div class="d-flex align-items-center">
			<span class="navbar-text me-3">
				Admin: ${admin.adminName}
			</span>
			<a href="${pageContext.request.contextPath}/admin/logout" class="btn btn-outline-light btn-sm">Logout</a>
		</div>
	</div>
</nav>

<div class="container-loans">
	<div class="page-header">
		<h2>📋 Pending Loan Applications</h2>
		<p class="text-muted">Review and approve/deny loan applications</p>
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
				<h4>No Pending Loan Applications</h4>
				<p>All loan applications have been processed.</p>
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

					<div class="customer-info">
						<div class="customer-name">${loan.customerFirstName} ${loan.customerLastName}</div>
						<div class="customer-email">${loan.customerEmail}</div>
						<div style="margin-top: 8px;">
							<span class="detail-label">Current Balance:</span>
							<strong>₹${String.format("%.2f", loan.customerBalance)}</strong>
							<c:choose>
								<c:when test="${loan.customerBalance >= 1000}">
									<span class="balance-indicator balance-sufficient">✓ Sufficient</span>
								</c:when>
								<c:otherwise>
									<span class="balance-indicator balance-insufficient">✗ Insufficient</span>
								</c:otherwise>
							</c:choose>
						</div>
					</div>

					<div class="loan-details">
						<div class="detail-item">
							<span class="detail-label">Duration</span>
							<span class="detail-value">${loan.durationInMonths} Months</span>
						</div>
						<div class="detail-item">
							<span class="detail-label">Interest Rate</span>
							<span class="detail-value">${String.format("%.2f", loan.interestRate)}%</span>
						</div>
						<div class="detail-item">
							<span class="detail-label">Monthly EMI</span>
							<span class="detail-value">₹${String.format("%.2f", loan.emiAmount)}</span>
						</div>
						<div class="detail-item">
							<span class="detail-label">Purpose</span>
							<span class="detail-value">${loan.purpose}</span>
						</div>
						<div class="detail-item">
							<span class="detail-label">Application Date</span>
							<span class="detail-value">${loan.applicationDate}</span>
						</div>
					</div>

					<div class="loan-footer">
						<a href="${pageContext.request.contextPath}/admin/loans/approve/${loan.id}" class="btn-approve">
							Review & Process
						</a>
					</div>
				</div>
			</c:forEach>
		</c:otherwise>
	</c:choose>

	<a href="${pageContext.request.contextPath}/admin/dashboard" class="back-button">
		← Back to Dashboard
	</a>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
</body>
</html>

