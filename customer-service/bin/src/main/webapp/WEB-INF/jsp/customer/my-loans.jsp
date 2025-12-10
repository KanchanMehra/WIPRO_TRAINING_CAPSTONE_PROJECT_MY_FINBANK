<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>MyFin Bank - My Loans</title>
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
		.container-loans {
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
		.btn-new-loan {
			background: linear-gradient(135deg, #0891b2 0%, #06b6d4 100%);
			color: white;
			border: none;
			padding: 10px 20px;
			border-radius: 6px;
			font-weight: 600;
			text-decoration: none;
			cursor: pointer;
		}
		.btn-new-loan:hover {
			color: white;
			text-decoration: none;
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
			align-items: start;
			margin-bottom: 15px;
		}
		.loan-app-no {
			font-size: 14px;
			color: #6b7280;
			font-weight: 600;
			font-family: 'Courier New', monospace;
		}
		.status-badge {
			display: inline-block;
			padding: 6px 12px;
			border-radius: 20px;
			font-size: 12px;
			font-weight: 600;
		}
		.status-pending {
			background: #fef3c7;
			color: #92400e;
		}
		.status-approved {
			background: #d1fae5;
			color: #065f46;
		}
		.status-rejected {
			background: #fee2e2;
			color: #991b1b;
		}
		.status-disbursed {
			background: #dbeafe;
			color: #1e40af;
		}
		.loan-amount {
			font-size: 24px;
			font-weight: 700;
			color: #0891b2;
			margin-bottom: 10px;
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
		.btn-view {
			background: #3b82f6;
			color: white;
			border: none;
			padding: 8px 16px;
			border-radius: 4px;
			font-size: 12px;
			font-weight: 600;
			text-decoration: none;
			cursor: pointer;
		}
		.btn-view:hover {
			background: #2563eb;
			color: white;
			text-decoration: none;
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
				${sessionScope.loggedInCustomer.firstName} ${sessionScope.loggedInCustomer.lastName}
			</span>
			<a href="${pageContext.request.contextPath}/customer/logout" class="btn btn-outline-light btn-sm">Logout</a>
		</div>
	</div>
</nav>

<div class="container-loans">
	<div class="page-header">
		<h2>📊 My Loan Applications</h2>
		<a href="${pageContext.request.contextPath}/customer/loan/apply" class="btn-new-loan">+ Apply for New Loan</a>
	</div>

	<c:choose>
		<c:when test="${empty loans}">
			<div class="empty-state">
				<div class="empty-state-icon">📭</div>
				<h4>No Loan Applications</h4>
				<p>You haven't applied for any loans yet.</p>
				<a href="${pageContext.request.contextPath}/customer/loan/apply" class="btn-new-loan" style="margin-top: 20px;">
					Apply for Your First Loan
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
						<span class="status-badge status-${fn:toLowerCase(loan.status)}">
							<c:choose>
								<c:when test="${loan.status == 'PENDING'}">🟡 PENDING</c:when>
								<c:when test="${loan.status == 'APPROVED'}">✅ APPROVED</c:when>
								<c:when test="${loan.status == 'REJECTED'}">❌ REJECTED</c:when>
								<c:when test="${loan.status == 'DISBURSED'}">💰 DISBURSED</c:when>
								<c:otherwise>${loan.status}</c:otherwise>
							</c:choose>
						</span>
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
						<a href="${pageContext.request.contextPath}/customer/loan/view/${loan.id}" class="btn-view">
							View Details
						</a>
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

