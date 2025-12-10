<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>MyFin Bank - Loan Approval</title>
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
		.container-approval {
			padding: 40px 20px;
			max-width: 900px;
			margin: 0 auto;
		}
		.page-header {
			text-align: center;
			margin-bottom: 30px;
		}
		.page-header h2 {
			color: #1f2937;
			font-weight: 700;
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
			color: #f59e0b;
			font-weight: 700;
		}
		.balance-box {
			background: #f0fdf4;
			border-left: 4px solid #10b981;
			padding: 20px;
			border-radius: 6px;
			margin: 20px 0;
		}
		.balance-insufficient {
			background: #fee2e2;
			border-left: 4px solid #ef4444;
		}
		.action-section {
			background: #f3f4f6;
			padding: 25px;
			border-radius: 8px;
			margin-top: 20px;
		}
		.action-buttons {
			display: flex;
			gap: 15px;
			margin-top: 20px;
		}
		.btn-custom {
			flex: 1;
			padding: 14px;
			border-radius: 6px;
			font-weight: 600;
			border: none;
			cursor: pointer;
			font-size: 16px;
		}
		.btn-approve {
			background: #10b981;
			color: white;
		}
		.btn-approve:hover {
			background: #059669;
		}
		.btn-deny {
			background: #ef4444;
			color: white;
		}
		.btn-deny:hover {
			background: #dc2626;
		}
		.btn-back {
			background: #e5e7eb;
			color: #1f2937;
			text-decoration: none;
			display: inline-block;
			padding: 12px 24px;
			border-radius: 6px;
			font-weight: 600;
			margin-top: 20px;
		}
		.btn-back:hover {
			background: #d1d5db;
			text-decoration: none;
		}
		.form-group {
			margin-bottom: 15px;
		}
		.form-group label {
			font-weight: 600;
			color: #1f2937;
			margin-bottom: 8px;
			display: block;
		}
		.form-group textarea {
			width: 100%;
			padding: 12px;
			border: 2px solid #e5e7eb;
			border-radius: 6px;
			font-size: 14px;
			font-family: inherit;
		}
		.warning-box {
			background: #fef3c7;
			border-left: 4px solid #f59e0b;
			padding: 15px;
			border-radius: 6px;
			margin: 20px 0;
			color: #78350f;
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

<div class="container-approval">
	<div class="page-header">
		<h2>📋 Loan Application Review</h2>
		<p class="text-muted">Review the loan application and approve or deny</p>
	</div>

	<!-- Customer Information -->
	<div class="detail-card">
		<div class="card-title">👤 Customer Information</div>
		<div class="detail-grid">
			<div class="detail-item">
				<span class="detail-label">Customer Name</span>
				<span class="detail-value">${loan.customerFirstName} ${loan.customerLastName}</span>
			</div>
			<div class="detail-item">
				<span class="detail-label">Email</span>
				<span class="detail-value">${loan.customerEmail}</span>
			</div>
		</div>

		<div class="balance-box ${loan.customerBalance < 1000 ? 'balance-insufficient' : ''}">
			<div class="detail-label">Current Account Balance</div>
			<div class="highlight-value">₹${String.format("%.2f", loan.customerBalance)}</div>
			<c:choose>
				<c:when test="${loan.customerBalance >= 1000}">
					<div style="margin-top: 10px; color: #065f46;">
						✓ Customer has sufficient balance (Minimum ₹1,000 required)
					</div>
				</c:when>
				<c:otherwise>
					<div style="margin-top: 10px; color: #991b1b;">
						✗ Customer balance is insufficient (Minimum ₹1,000 required)
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>

	<!-- Loan Details -->
	<div class="detail-card">
		<div class="card-title">💰 Loan Details</div>
		<div style="background: #dbeafe; padding: 15px; border-radius: 6px; margin-bottom: 20px;">
			<div class="detail-label">Loan Application Number</div>
			<div style="font-size: 18px; font-weight: 700; color: #0284c7; font-family: 'Courier New', monospace;">
				${loan.loanApplicationNo}
			</div>
		</div>
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
				<span class="detail-value">${String.format("%.2f", loan.interestRate)}%</span>
			</div>
			<div class="detail-item">
				<span class="detail-label">Monthly EMI</span>
				<span class="detail-value">₹${String.format("%.2f", loan.emiAmount)}</span>
			</div>
			<div class="detail-item">
				<span class="detail-label">Total Payable</span>
				<span class="detail-value">₹${String.format("%.2f", loan.totalAmountPayable)}</span>
			</div>
			<div class="detail-item">
				<span class="detail-label">Total Interest</span>
				<span class="detail-value">₹${String.format("%.2f", loan.totalInterest)}</span>
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

		<c:if test="${loan.remarks != null && loan.remarks != ''}">
			<div style="margin-top: 20px; padding-top: 20px; border-top: 1px solid #e5e7eb;">
				<div class="detail-label" style="margin-bottom: 10px;">Customer Remarks</div>
				<div style="background: #f3f4f6; padding: 12px; border-radius: 6px; color: #1f2937;">
					${loan.remarks}
				</div>
			</div>
		</c:if>
	</div>

	<c:if test="${loan.customerBalance < 1000}">
		<div class="warning-box">
			<strong>⚠️ Warning:</strong> Customer's current balance (₹${String.format("%.2f", loan.customerBalance)})
			is below the minimum threshold of ₹1,000. Approving this loan may not be advisable.
		</div>
	</c:if>

	<!-- Action Section -->
	<div class="action-section">
		<h5 style="margin-bottom: 20px; color: #1f2937;">Take Action</h5>

		<!-- Approve Form -->
		<div id="approveSection" style="display: block;">
			<form method="post" action="${pageContext.request.contextPath}/admin/loans/approve/${loan.id}">
				<div class="form-group">
					<label for="approveRemarks">Approval Remarks (Optional)</label>
					<textarea id="approveRemarks" name="adminRemarks" rows="3"
					          placeholder="Enter any remarks for approval..."></textarea>
				</div>
				<button type="submit" class="btn-custom btn-approve"
				        onclick="return confirm('Are you sure you want to approve this loan? The loan amount will be disbursed to customer account.');">
					✓ Approve Loan
				</button>
			</form>
		</div>

		<div style="text-align: center; margin: 20px 0; color: #6b7280;">OR</div>

		<!-- Deny Form -->
		<div id="denySection">
			<form method="post" action="${pageContext.request.contextPath}/admin/loans/reject/${loan.id}"
			      onsubmit="return validateDenyForm()">
				<div class="form-group">
					<label for="denyRemarks">Denial Reason (Required) <span style="color: #ef4444;">*</span></label>
					<textarea id="denyRemarks" name="adminRemarks" rows="3"
					          placeholder="Enter reason for denying the loan..." required></textarea>
				</div>
				<button type="submit" class="btn-custom btn-deny">
					✗ Deny Loan
				</button>
			</form>
		</div>
	</div>

	<a href="${pageContext.request.contextPath}/admin/loans/pending" class="btn-back">
		← Back to Pending Loans
	</a>
</div>

<script>
	function validateDenyForm() {
		const remarks = document.getElementById('denyRemarks').value.trim();
		if (remarks.length === 0) {
			alert('Please provide a reason for denying the loan.');
			return false;
		}
		return confirm('Are you sure you want to deny this loan application?');
	}
</script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
</body>
</html>

