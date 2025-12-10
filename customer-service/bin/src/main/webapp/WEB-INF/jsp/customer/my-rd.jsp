<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>MyFin Bank - My Recurring Deposits</title>
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
		.container-rds {
			padding: 30px 20px;
			max-width: 1000px;
			margin: 0 auto;
		}
		.page-header {
			display: flex;
			justify-content: space-between;
			align-items: center;
			margin-bottom: 30px;
		}
		.page-header h2 {
			color: #1f2937;
			font-weight: 700;
			margin: 0;
		}
		.btn-new {
			background: linear-gradient(135deg, #10b981 0%, #059669 100%);
			color: white;
			border: none;
			padding: 10px 20px;
			border-radius: 6px;
			font-weight: 600;
			text-decoration: none;
		}
		.rd-card {
			background: white;
			border-radius: 12px;
			box-shadow: 0 5px 20px rgba(0, 0, 0, 0.08);
			padding: 20px;
			margin-bottom: 20px;
			border-left: 4px solid #10b981;
		}
		.rd-header {
			display: flex;
			justify-content: space-between;
			align-items: start;
			margin-bottom: 15px;
		}
		.rd-account-no {
			font-size: 14px;
			color: #6b7280;
			font-weight: 600;
			font-family: 'Courier New', monospace;
		}
		.status-badge {
			padding: 6px 12px;
			border-radius: 20px;
			font-size: 12px;
			font-weight: 600;
		}
		.status-active { background: #d1fae5; color: #065f46; }
		.status-matured { background: #dbeafe; color: #1e40af; }
		.status-closed { background: #f3f4f6; color: #6b7280; }
		.installment-amount {
			font-size: 24px;
			font-weight: 700;
			color: #10b981;
			margin-bottom: 10px;
		}
		.rd-details {
			display: grid;
			grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
			gap: 15px;
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
			color: #10b981;
			text-decoration: none;
			font-weight: 600;
			margin-top: 20px;
		}
		.back-button:hover { text-decoration: underline; }
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

<div class="container-rds">
	<div class="page-header">
		<h2>🏦 My Recurring Deposits</h2>
		<a href="${pageContext.request.contextPath}/customer/investment/rd/create" class="btn-new">+ Create New RD</a>
	</div>

	<c:choose>
		<c:when test="${empty rds}">
			<div class="empty-state">
				<div class="empty-state-icon">📭</div>
				<h4>No Recurring Deposits</h4>
				<p>You haven't created any recurring deposits yet.</p>
				<a href="${pageContext.request.contextPath}/customer/investment/rd/create" class="btn-new" style="margin-top: 20px;">
					Create Your First RD
				</a>
			</div>
		</c:when>
		<c:otherwise>
			<c:forEach var="rd" items="${rds}">
				<div class="rd-card">
					<div class="rd-header">
						<div>
							<div class="rd-account-no">Account: ${rd.rdAccountNo}</div>
							<div class="installment-amount">₹${String.format("%.2f", rd.monthlyInstallment)}/month</div>
						</div>
						<span class="status-badge status-${fn:toLowerCase(rd.status)}">
							${rd.status}
						</span>
					</div>

					<div class="rd-details">
						<div class="detail-item">
							<span class="detail-label">Duration</span>
							<span class="detail-value">${rd.durationInMonths} Months</span>
						</div>
						<div class="detail-item">
							<span class="detail-label">Interest Rate</span>
							<span class="detail-value">${String.format("%.2f", rd.interestRate)}%</span>
						</div>
						<div class="detail-item">
							<span class="detail-label">Maturity Amount</span>
							<span class="detail-value">₹${String.format("%.2f", rd.maturityAmount)}</span>
						</div>
						<div class="detail-item">
							<span class="detail-label">Start Date</span>
							<span class="detail-value">${rd.startDate}</span>
						</div>
						<div class="detail-item">
							<span class="detail-label">Maturity Date</span>
							<span class="detail-value">${rd.maturityDate}</span>
						</div>
					</div>
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

