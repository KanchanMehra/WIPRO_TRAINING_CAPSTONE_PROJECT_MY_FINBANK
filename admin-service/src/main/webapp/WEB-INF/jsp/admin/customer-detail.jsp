<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>MyFin Bank - Customer Details</title>
	<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
	<style>
		body {
			background: #f0f9ff;
			min-height: 100vh;
			font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
		}
		.navbar-custom {
			background: linear-gradient(135deg, #7c3aed 0%, #6d28d9 100%);
		}
		.navbar-custom .navbar-brand,
		.navbar-custom .nav-link,
		.navbar-custom .navbar-text {
			color: #f0f9ff !important;
		}
		.container-detail {
			padding: 30px 20px;
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
		.status-active {
			background: #d1fae5;
			color: #065f46;
		}
		.status-inactive {
			background: #fee2e2;
			color: #991b1b;
		}
		.info-section {
			background: white;
			border-radius: 12px;
			padding: 25px;
			margin-bottom: 20px;
			box-shadow: 0 5px 20px rgba(0, 0, 0, 0.08);
		}
		.section-title {
			font-size: 18px;
			font-weight: 700;
			color: #1f2937;
			margin-bottom: 20px;
			padding-bottom: 10px;
			border-bottom: 2px solid #e5e7eb;
		}
		.info-grid {
			display: grid;
			grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
			gap: 20px;
		}
		.info-item {
			margin-bottom: 15px;
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
		}
		.account-number {
			font-size: 20px;
			font-weight: 700;
			color: #7c3aed;
			font-family: 'Courier New', monospace;
		}
		.balance-highlight {
			font-size: 28px;
			font-weight: 700;
			color: #10b981;
		}
		.action-buttons {
			display: flex;
			gap: 10px;
			margin-top: 20px;
		}
		.btn-custom {
			padding: 12px 24px;
			border-radius: 6px;
			font-weight: 600;
			border: none;
			cursor: pointer;
			text-decoration: none;
			display: inline-block;
		}
		.btn-back {
			background: #e5e7eb;
			color: #1f2937;
		}
		.btn-back:hover {
			background: #d1d5db;
		}
		.btn-deactivate {
			background: #ef4444;
			color: white;
		}
		.btn-deactivate:hover {
			background: #dc2626;
		}
		.btn-activate {
			background: #10b981;
			color: white;
		}
		.btn-activate:hover {
			background: #059669;
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

<div class="container-detail">
	<div class="page-header">
		<h2>👤 Customer Details</h2>
		<c:choose>
			<c:when test="${customer.status == 'ACTIVE'}">
				<span class="status-badge status-active">✓ ACTIVE</span>
			</c:when>
			<c:otherwise>
				<span class="status-badge status-inactive">✗ INACTIVE</span>
			</c:otherwise>
		</c:choose>
	</div>

	<!-- Personal Information -->
	<div class="info-section">
		<div class="section-title">👤 Personal Information</div>
		<div class="info-grid">
			<div class="info-item">
				<div class="info-label">Full Name</div>
				<div class="info-value">${customer.firstName} ${customer.lastName}</div>
			</div>
			<div class="info-item">
				<div class="info-label">Date of Birth</div>
				<div class="info-value">${customer.dob}</div>
			</div>
			<div class="info-item">
				<div class="info-label">Email Address</div>
				<div class="info-value">${customer.email}</div>
			</div>
			<div class="info-item">
				<div class="info-label">Mobile Number</div>
				<div class="info-value">${customer.mobileNo}</div>
			</div>
		</div>
	</div>

	<!-- Account Information -->
	<div class="info-section">
		<div class="section-title">💳 Account Information</div>
		<div class="info-grid">
			<div class="info-item">
				<div class="info-label">Account Number</div>
				<div class="account-number">${customer.accountNo != null ? customer.accountNo : 'Not Generated'}</div>
			</div>
			<div class="info-item">
				<div class="info-label">Account Type</div>
				<div class="info-value">${customer.accountType}</div>
			</div>
			<div class="info-item">
				<div class="info-label">Date of Opening</div>
				<div class="info-value">${customer.dateOfOpen}</div>
			</div>
			<div class="info-item">
				<div class="info-label">Cheque Facility</div>
				<div class="info-value">${customer.cheqFacil}</div>
			</div>
			<div class="info-item">
				<div class="info-label">Username</div>
				<div class="info-value">${customer.userName}</div>
			</div>
			<div class="info-item">
				<div class="info-label">Current Balance</div>
				<div class="balance-highlight">₹${customer.amount}</div>
			</div>
		</div>
	</div>

	<!-- Address Information -->
	<div class="info-section">
		<div class="section-title">📍 Address Information</div>
		<div class="info-grid">
			<div class="info-item">
				<div class="info-label">Address Line 1</div>
				<div class="info-value">${customer.address1}</div>
			</div>
			<div class="info-item">
				<div class="info-label">Address Line 2</div>
				<div class="info-value">${customer.address2 != null ? customer.address2 : 'N/A'}</div>
			</div>
			<div class="info-item">
				<div class="info-label">City</div>
				<div class="info-value">${customer.city}</div>
			</div>
			<div class="info-item">
				<div class="info-label">State</div>
				<div class="info-value">${customer.state}</div>
			</div>
			<div class="info-item">
				<div class="info-label">Zip Code</div>
				<div class="info-value">${customer.zipCode}</div>
			</div>
			<div class="info-item">
				<div class="info-label">Country</div>
				<div class="info-value">${customer.country}</div>
			</div>
		</div>
	</div>

	<!-- Action Buttons -->
	<div class="action-buttons">
		<a href="${pageContext.request.contextPath}/admin/customers/all" class="btn-custom btn-back">
			← Back to All Customers
		</a>
		<c:choose>
			<c:when test="${customer.status == 'ACTIVE'}">
				<form method="post" action="${pageContext.request.contextPath}/admin/customers/deactivate/${customer.id}" style="display: inline;"
				      onsubmit="return confirm('Are you sure you want to deactivate this customer?');">
					<button type="submit" class="btn-custom btn-deactivate">Deactivate Customer</button>
				</form>
			</c:when>
			<c:otherwise>
				<form method="post" action="${pageContext.request.contextPath}/admin/customers/activate/${customer.id}" style="display: inline;"
				      onsubmit="return confirm('Are you sure you want to activate this customer?');">
					<button type="submit" class="btn-custom btn-activate">Activate Customer</button>
				</form>
			</c:otherwise>
		</c:choose>
	</div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
</body>
</html>

