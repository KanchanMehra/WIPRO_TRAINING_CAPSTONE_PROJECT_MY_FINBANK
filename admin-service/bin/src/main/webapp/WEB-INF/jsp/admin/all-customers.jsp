<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>MyFin Bank - All Customers</title>
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
		.container-customers {
			padding: 30px 20px;
		}
		.page-header {
			margin-bottom: 30px;
		}
		.page-header h2 {
			color: #1f2937;
			font-weight: 700;
		}
		.filter-section {
			background: white;
			padding: 20px;
			border-radius: 12px;
			margin-bottom: 20px;
			box-shadow: 0 5px 20px rgba(0, 0, 0, 0.08);
		}
		.filter-row {
			display: flex;
			gap: 15px;
			align-items: flex-end;
			flex-wrap: wrap;
		}
		.filter-group {
			flex: 1;
			min-width: 200px;
		}
		.filter-group label {
			font-size: 13px;
			color: #6b7280;
			text-transform: uppercase;
			font-weight: 600;
			margin-bottom: 5px;
			display: block;
		}
		.filter-group select,
		.filter-group input {
			width: 100%;
			padding: 10px;
			border: 2px solid #e5e7eb;
			border-radius: 6px;
			font-size: 14px;
		}
		.btn-filter {
			background: #7c3aed;
			color: white;
			border: none;
			padding: 10px 20px;
			border-radius: 6px;
			font-weight: 600;
			cursor: pointer;
		}
		.btn-filter:hover {
			background: #6d28d9;
		}
		.btn-reset {
			background: #e5e7eb;
			color: #1f2937;
			border: none;
			padding: 10px 20px;
			border-radius: 6px;
			font-weight: 600;
			cursor: pointer;
		}
		.alert {
			border-radius: 8px;
			padding: 12px 15px;
			margin-bottom: 20px;
		}
		.table-card {
			background: white;
			border-radius: 12px;
			box-shadow: 0 5px 20px rgba(0, 0, 0, 0.08);
			overflow: hidden;
		}
		.table {
			margin-bottom: 0;
		}
		.table thead {
			background: #f3f4f6;
		}
		.table th {
			font-weight: 600;
			color: #1f2937;
			border-bottom: 2px solid #e5e7eb;
			padding: 15px;
			font-size: 13px;
			text-transform: uppercase;
		}
		.table td {
			padding: 12px 15px;
			border-bottom: 1px solid #f0f0f0;
			vertical-align: middle;
		}
		.status-badge {
			display: inline-block;
			padding: 4px 12px;
			border-radius: 12px;
			font-size: 12px;
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
		.btn-sm-custom {
			padding: 6px 12px;
			font-size: 12px;
			border-radius: 4px;
			border: none;
			cursor: pointer;
			font-weight: 600;
			margin-right: 5px;
		}
		.btn-view {
			background: #3b82f6;
			color: white;
		}
		.btn-view:hover {
			background: #2563eb;
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
		.empty-state {
			text-align: center;
			padding: 60px 20px;
			color: #6b7280;
		}
		.back-button {
			display: inline-block;
			margin-top: 20px;
			color: #7c3aed;
			text-decoration: none;
			font-weight: 600;
		}
		.back-button:hover {
			text-decoration: underline;
		}
		.customer-count {
			color: #6b7280;
			font-size: 14px;
			margin-bottom: 15px;
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

<div class="container-customers">
	<div class="page-header">
		<h2>👥 All Customers</h2>
	</div>

	<!-- Success/Error Messages -->
	<c:if test="${not empty success}">
		<div class="alert alert-success">${success}</div>
	</c:if>
	<c:if test="${not empty error}">
		<div class="alert alert-danger">${error}</div>
	</c:if>

	<!-- Filter Section -->
	<div class="filter-section">
		<form method="get" action="${pageContext.request.contextPath}/admin/customers/all">
			<div class="filter-row">
				<div class="filter-group">
					<label for="status">Filter by Status</label>
					<select id="status" name="status">
						<option value="">All Customers</option>
						<option value="ACTIVE" ${currentStatus == 'ACTIVE' ? 'selected' : ''}>Active Only</option>
						<option value="INACTIVE" ${currentStatus == 'INACTIVE' ? 'selected' : ''}>Inactive Only</option>
					</select>
				</div>
				<div class="filter-group">
					<label for="search">Search Customer</label>
					<input type="text" id="search" name="search" placeholder="Name, Email, or Account No" value="${searchTerm}"/>
				</div>
				<div>
					<button type="submit" class="btn-filter">Apply Filters</button>
					<a href="${pageContext.request.contextPath}/admin/customers/all" class="btn-reset">Reset</a>
				</div>
			</div>
		</form>
	</div>

	<!-- Customer Count -->
	<div class="customer-count">
		<strong>Showing ${customers.size()} customer(s)</strong>
	</div>

	<!-- Customers Table -->
	<div class="table-card">
		<c:choose>
			<c:when test="${empty customers}">
				<div class="empty-state">
					<div style="font-size: 64px; color: #d1d5db;">👤</div>
					<h4>No Customers Found</h4>
					<p>No customers match the current filters.</p>
				</div>
			</c:when>
			<c:otherwise>
				<table class="table">
					<thead>
					<tr>
						<th>Account No</th>
						<th>Name</th>
						<th>Email</th>
						<th>Mobile</th>
						<th>Registration Date</th>
						<th>Balance</th>
						<th>Status</th>
						<th>Actions</th>
					</tr>
					</thead>
					<tbody>
					<c:forEach var="customer" items="${customers}">
						<tr>
							<td><strong>${customer.accountNo != null ? customer.accountNo : 'Pending'}</strong></td>
							<td>${customer.firstName} ${customer.lastName}</td>
							<td>${customer.email}</td>
							<td>${customer.mobileNo}</td>
							<td>${customer.dateOfOpen}</td>
							<td>₹${customer.amount}</td>
							<td>
								<c:choose>
									<c:when test="${customer.status == 'ACTIVE'}">
										<span class="status-badge status-active">✓ ACTIVE</span>
									</c:when>
									<c:otherwise>
										<span class="status-badge status-inactive">✗ INACTIVE</span>
									</c:otherwise>
								</c:choose>
							</td>
							<td>
								<a href="${pageContext.request.contextPath}/admin/customers/detail/${customer.id}" class="btn-sm-custom btn-view">View</a>
								<c:choose>
									<c:when test="${customer.status == 'ACTIVE'}">
										<form method="post" action="${pageContext.request.contextPath}/admin/customers/deactivate/${customer.id}" style="display: inline;"
										      onsubmit="return confirm('Are you sure you want to deactivate ${customer.firstName} ${customer.lastName}?');">
											<button type="submit" class="btn-sm-custom btn-deactivate">Deactivate</button>
										</form>
									</c:when>
									<c:otherwise>
										<form method="post" action="${pageContext.request.contextPath}/admin/customers/activate/${customer.id}" style="display: inline;"
										      onsubmit="return confirm('Are you sure you want to activate ${customer.firstName} ${customer.lastName}?');">
											<button type="submit" class="btn-sm-custom btn-activate">Activate</button>
										</form>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</c:otherwise>
		</c:choose>
	</div>

	<a href="${pageContext.request.contextPath}/admin/dashboard" class="back-button">
		← Back to Dashboard
	</a>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
</body>
</html>

