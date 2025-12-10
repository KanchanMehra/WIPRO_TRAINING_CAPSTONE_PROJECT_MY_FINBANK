<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>MyFin Bank - Pending Customer Approvals</title>
	<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
	<style>
		body {
			background: #f3f4f6;
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
		.content-container {
			padding: 30px 20px;
		}
		.card {
			border-radius: 12px;
			box-shadow: 0 10px 30px rgba(0, 0, 0, 0.08);
			border: none;
			margin-bottom: 20px;
		}
		.card-header {
			background: linear-gradient(135deg, #991b1b 0%, #7f1d1d 100%);
			color: white;
			font-weight: 600;
			border-radius: 12px 12px 0 0 !important;
		}
		.table {
			margin-bottom: 0;
		}
		.btn-activate {
			background: #10b981;
			color: white;
			border: none;
			padding: 5px 15px;
			border-radius: 5px;
			font-size: 14px;
		}
		.btn-activate:hover {
			background: #059669;
			color: white;
		}
		.alert {
			border-radius: 8px;
		}
		.badge-pending {
			background: #f59e0b;
			color: white;
			padding: 5px 10px;
			border-radius: 5px;
			font-size: 12px;
		}
		.empty-state {
			text-align: center;
			padding: 60px 20px;
			color: #6b7280;
		}
		.empty-state i {
			font-size: 64px;
			margin-bottom: 20px;
			color: #d1d5db;
		}
	</style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-custom">
	<div class="container-fluid">
		<a class="navbar-brand" href="${pageContext.request.contextPath}/admin/dashboard">MyFin Bank - Admin</a>
		<div class="d-flex align-items-center">
			<span class="navbar-text me-3">
				Welcome,
				<c:choose>
					<c:when test="${not empty sessionScope.loggedInAdmin}">
						${sessionScope.loggedInAdmin.adminName}
					</c:when>
					<c:otherwise>Admin</c:otherwise>
				</c:choose>
			</span>
			<a href="${pageContext.request.contextPath}/admin/dashboard" class="btn btn-outline-light btn-sm me-2">Dashboard</a>
			<a href="${pageContext.request.contextPath}/admin/logout" class="btn btn-outline-light btn-sm">Logout</a>
		</div>
	</div>
</nav>

<div class="container content-container">
	<div class="row">
		<div class="col-12">
			<h2 class="mb-4">
				<i class="bi bi-person-check"></i> Pending Customer Approvals
				<span class="badge bg-warning ms-2">${customerCount}</span>
			</h2>

			<!-- Success Message -->
			<c:if test="${not empty successMessage}">
				<div class="alert alert-success alert-dismissible fade show" role="alert">
					<strong>Success!</strong> ${successMessage}
					<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
				</div>
			</c:if>

			<!-- Error Message -->
			<c:if test="${not empty errorMessage}">
				<div class="alert alert-danger alert-dismissible fade show" role="alert">
					<strong>Error!</strong> ${errorMessage}
					<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
				</div>
			</c:if>

			<!-- Error from service -->
			<c:if test="${not empty error}">
				<div class="alert alert-danger alert-dismissible fade show" role="alert">
					<strong>Error!</strong> ${error}
					<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
				</div>
			</c:if>

			<!-- Customers Table -->
			<c:choose>
				<c:when test="${not empty customers and customerCount > 0}">
					<div class="card">
						<div class="card-header">
							<h5 class="mb-0">Customers Waiting for Approval</h5>
						</div>
						<div class="card-body p-0">
							<div class="table-responsive">
								<table class="table table-hover">
									<thead class="table-light">
										<tr>
											<th>ID</th>
											<th>Name</th>
											<th>Email</th>
											<th>Mobile</th>
											<th>Account Type</th>
											<th>Initial Amount</th>
											<th>Registration Date</th>
											<th>Status</th>
											<th>Action</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="customer" items="${customers}">
											<tr>
												<td>${customer.id}</td>
												<td>
													<strong>${customer.firstName} ${customer.lastName}</strong><br>
													<small class="text-muted">Username: ${customer.userName}</small>
												</td>
												<td>${customer.email}</td>
												<td>${customer.mobileNo}</td>
												<td>
													<span class="badge bg-info">${customer.accountType}</span>
												</td>
												<td>
													<strong>₹${customer.amount}</strong>
												</td>
												<td>
													${customer.dateOfOpen}
												</td>
												<td>
													<span class="badge-pending">PENDING</span>
												</td>
												<td>
													<form method="post" action="${pageContext.request.contextPath}/admin/customers/activate" style="display:inline;">
														<input type="hidden" name="customerId" value="${customer.id}"/>
														<button type="submit" class="btn-activate"
														        onclick="return confirm('Are you sure you want to activate customer ${customer.firstName} ${customer.lastName}? This will generate an account number.')">
															Activate
														</button>
													</form>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<div class="card">
						<div class="card-body">
							<div class="empty-state">
								<div>✅</div>
								<h4>No Pending Approvals</h4>
								<p>All customers have been processed. Great job!</p>
								<a href="${pageContext.request.contextPath}/admin/dashboard" class="btn btn-primary mt-3">
									Back to Dashboard
								</a>
							</div>
						</div>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
</body>
</html>

