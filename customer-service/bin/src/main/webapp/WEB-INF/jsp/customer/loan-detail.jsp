<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>MyFin Bank - Loan Details</title>
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
		.container-detail {
			padding: 40px 20px;
			max-width: 900px;
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
		.status-badge {
			display: inline-block;
			padding: 8px 16px;
			border-radius: 20px;
			font-size: 14px;
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
		.detail-card {
			background: white;
			border-radius: 12px;
			box-shadow: 0 5px 20px rgba(0, 0, 0, 0.08);
			padding: 30px;
			margin-bottom: 20px;
		}
		.card-title {
			font-size: 18px;
			font-weight: 700;
			color: #1f2937;
			margin-bottom: 20px;
			padding-bottom: 10px;
			border-bottom: 2px solid #e5e7eb;
		}
		.detail-grid {
			display: grid;
			grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
			gap: 20px;
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
		.highlight-value {
			font-size: 24px;
			color: #0891b2;
			font-weight: 700;
		}
		.app-no-box {
			background: #dbeafe;
			padding: 15px;
			border-radius: 6px;
			margin: 15px 0;
		}
		.app-no {
			font-size: 18px;
			font-weight: 700;
			color: #0284c7;
			font-family: 'Courier New', monospace;
		}
		.breakdown-table {
			width: 100%;
			border-collapse: collapse;
			margin-top: 15px;
		}
		.breakdown-table th,
		.breakdown-table td {
			padding: 12px;
			text-align: right;
			border-bottom: 1px solid #e5e7eb;
		}
		.breakdown-table th {
			background: #f3f4f6;
			font-weight: 600;
			color: #1f2937;
			font-size: 13px;
		}
		.breakdown-table td:first-child,
		.breakdown-table th:first-child {
			text-align: left;
		}
		.action-buttons {
			display: flex;
			gap: 10px;
			justify-content: flex-start;
			margin-top: 20px;
		}
		.btn-custom {
			padding: 12px 24px;
			border-radius: 6px;
			font-weight: 600;
			text-decoration: none;
			border: none;
			cursor: pointer;
			transition: all 0.3s;
		}
		.btn-back {
			background: #e5e7eb;
			color: #1f2937;
		}
		.btn-back:hover {
			background: #d1d5db;
		}
		.info-box {
			background: #fef3c7;
			border-left: 4px solid #f59e0b;
			padding: 15px;
			border-radius: 6px;
			color: #78350f;
			font-size: 14px;
			margin-top: 20px;
		}
		.summary-section {
			background: linear-gradient(135deg, #f0f9ff 0%, #e0f7ff 100%);
			padding: 20px;
			border-radius: 8px;
			margin-bottom: 20px;
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

<div class="container-detail">
	<div class="page-header">
		<h2>📋 Loan Application Details</h2>
		<span class="status-badge status-${fn:toLowerCase(loan.status)}">
			<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
			<c:choose xmlns:c="http://java.sun.com/jsp/jstl/core">
				<c:when test="${loan.status == 'PENDING'}">🟡 PENDING</c:when>
				<c:when test="${loan.status == 'APPROVED'}">✅ APPROVED</c:when>
				<c:when test="${loan.status == 'REJECTED'}">❌ REJECTED</c:when>
				<c:when test="${loan.status == 'DISBURSED'}">💰 DISBURSED</c:when>
				<c:otherwise>${loan.status}</c:otherwise>
			</c:choose>
		</span>
	</div>

	<!-- Application Number -->
	<div class="summary-section">
		<div class="app-no-box">
			<div style="font-size: 12px; color: #1e40af; text-transform: uppercase; font-weight: 600; margin-bottom: 5px;">Loan Application Number</div>
			<div class="app-no">${loan.loanApplicationNo}</div>
		</div>
	</div>

	<!-- Basic Loan Details -->
	<div class="detail-card">
		<div class="card-title">💰 Loan Details</div>
		<div class="detail-grid">
			<div class="detail-item">
				<span class="detail-label">Loan Amount</span>
				<span class="highlight-value">₹${String.format("%.2f", loan.loanAmount)}</span>
			</div>
			<div class="detail-item">
				<span class="detail-label">Duration</span>
				<span class="detail-value">${loan.durationInMonths} Months</span>
			</div>
			<div class="detail-item">
				<span class="detail-label">Interest Rate</span>
				<span class="detail-value">${String.format("%.2f", loan.interestRate)}% per annum</span>
			</div>
			<div class="detail-item">
				<span class="detail-label">Purpose</span>
				<span class="detail-value">${loan.purpose}</span>
			</div>
			<div class="detail-item">
				<span class="detail-label">Application Date</span>
				<span class="detail-value">${loan.applicationDate}</span>
			</div>
			<div class="detail-item">
				<span class="detail-label">Required By Date</span>
				<span class="detail-value">
					<c:choose xmlns:c="http://java.sun.com/jsp/jstl/core">
						<c:when test="${loan.requiredByDate != null}">
							${loan.requiredByDate}
						</c:when>
						<c:otherwise>
							Not Specified
						</c:otherwise>
					</c:choose>
				</span>
			</div>
		</div>
	</div>

	<!-- EMI Details -->
	<div class="detail-card">
		<div class="card-title">📊 EMI & Payment Details</div>
		<div class="detail-grid">
			<div class="detail-item">
				<span class="detail-label">Monthly EMI</span>
				<span class="highlight-value">₹${String.format("%.2f", loan.emiAmount)}</span>
			</div>
			<div class="detail-item">
				<span class="detail-label">Total Amount Payable</span>
				<span class="detail-value">₹${String.format("%.2f", loan.totalAmountPayable)}</span>
			</div>
			<div class="detail-item">
				<span class="detail-label">Total Interest</span>
				<span class="detail-value">₹${String.format("%.2f", loan.totalInterest)}</span>
			</div>
			<div class="detail-item">
				<span class="detail-label">Interest on Principal</span>
				<span class="detail-value">
					<%
						java.math.BigDecimal principal = (java.math.BigDecimal) request.getAttribute("loan").getClass().getDeclaredMethod("getLoanAmount").invoke(request.getAttribute("loan"));
						java.math.BigDecimal totalInterest = (java.math.BigDecimal) request.getAttribute("loan").getClass().getDeclaredMethod("getTotalInterest").invoke(request.getAttribute("loan"));
						double interestPercentage = (totalInterest.doubleValue() / principal.doubleValue()) * 100;
					%>
					<%= String.format("%.2f", interestPercentage) %>%
				</span>
			</div>
		</div>
	</div>

	<!-- Status Information -->
	<div class="detail-card">
		<div class="card-title">ℹ️ Status Information</div>
		<div class="detail-grid">
			<div class="detail-item">
				<span class="detail-label">Current Status</span>
				<span class="detail-value">${loan.status}</span>
			</div>
			<c:if test="${loan.approvalDate != null}">
				<div class="detail-item">
					<span class="detail-label">Approval Date</span>
					<span class="detail-value">${loan.approvalDate}</span>
				</div>
			</c:if>
			<c:if test="${loan.adminRemarks != null && loan.adminRemarks != ''}">
				<div class="detail-item">
					<span class="detail-label">Admin Remarks</span>
					<span class="detail-value">${loan.adminRemarks}</span>
				</div>
			</div>
			</c:if>
		</div>

		<c:if test="${loan.remarks != null && loan.remarks != ''}">
			<div style="margin-top: 20px; padding-top: 20px; border-top: 1px solid #e5e7eb;">
				<div class="detail-label" style="margin-bottom: 10px;">Your Remarks</div>
				<div style="background: #f3f4f6; padding: 12px; border-radius: 6px; color: #1f2937;">
					${loan.remarks}
				</div>
			</div>
		</c:if>
	</div>

	<!-- EMI Payment Schedule (Sample - First 12 months) -->
	<div class="detail-card">
		<div class="card-title">📅 Estimated Payment Schedule (Sample - First 12 Months)</div>
		<div style="overflow-x: auto;">
			<table class="breakdown-table">
				<thead>
				<tr>
					<th>Month</th>
					<th>Principal (₹)</th>
					<th>Interest (₹)</th>
					<th>EMI (₹)</th>
					<th>Balance (₹)</th>
				</tr>
				</thead>
				<tbody>
				<%
					java.math.BigDecimal loanAmount = (java.math.BigDecimal) request.getAttribute("loan").getClass().getDeclaredMethod("getLoanAmount").invoke(request.getAttribute("loan"));
					java.math.BigDecimal interestRate = (java.math.BigDecimal) request.getAttribute("loan").getClass().getDeclaredMethod("getInterestRate").invoke(request.getAttribute("loan"));
					Integer months = (Integer) request.getAttribute("loan").getClass().getDeclaredMethod("getDurationInMonths").invoke(request.getAttribute("loan"));
					java.math.BigDecimal emiAmount = (java.math.BigDecimal) request.getAttribute("loan").getClass().getDeclaredMethod("getEmiAmount").invoke(request.getAttribute("loan"));

					// Fixed: Add scale and rounding mode to first division
					java.math.BigDecimal monthlyRate = interestRate.divide(java.math.BigDecimal.valueOf(12), 10, java.math.RoundingMode.HALF_UP).divide(java.math.BigDecimal.valueOf(100), 10, java.math.RoundingMode.HALF_UP);
					java.math.BigDecimal remainingBalance = loanAmount;

					for (int i = 1; i <= Math.min(12, months); i++) {
						java.math.BigDecimal interestPaid = remainingBalance.multiply(monthlyRate).setScale(2, java.math.RoundingMode.HALF_UP);
						java.math.BigDecimal principalPaid = emiAmount.subtract(interestPaid).setScale(2, java.math.RoundingMode.HALF_UP);
						remainingBalance = remainingBalance.subtract(principalPaid);
						if (remainingBalance.compareTo(java.math.BigDecimal.ZERO) < 0) {
							remainingBalance = java.math.BigDecimal.ZERO;
						}
				%>
				<tr>
					<td><%= i %></td>
					<td>₹<%= String.format("%.2f", principalPaid) %></td>
					<td>₹<%= String.format("%.2f", interestPaid) %></td>
					<td>₹<%= String.format("%.2f", emiAmount) %></td>
					<td>₹<%= String.format("%.2f", remainingBalance) %></td>
				</tr>
				<%
					}
					if (months > 12) {
				%>
				<tr style="font-weight: 600; background: #f3f4f6;">
					<td colspan="5" style="text-align: center;">... and <%= (months - 12) %> more months</td>
				</tr>
				<% } %>
				</tbody>
			</table>
		</div>
	</div>

	<!-- Info Box -->
	<div class="info-box">
		<strong>ℹ️ Note:</strong> The payment schedule above shows the first 12 months of your loan.
		The actual EMI will be ₹${String.format("%.2f", loan.emiAmount)} per month for the entire loan duration.
		For complete details and any queries, please contact our support team.
	</div>

	<div class="action-buttons">
		<a href="${pageContext.request.contextPath}/customer/loan/my-loans" class="btn-custom btn-back">
			← Back to My Loans
		</a>
	</div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
</body>
</html>

