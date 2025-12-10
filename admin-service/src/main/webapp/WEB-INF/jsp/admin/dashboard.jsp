<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>MyFin Bank - Admin Dashboard</title>
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
		.dashboard-container {
			padding: 30px 20px;
		}
		.card {
			border-radius: 12px;
			box-shadow: 0 10px 30px rgba(0, 0, 0, 0.08);
			border: none;
		}
		.card-icon {
			font-size: 32px;
			margin-bottom: 10px;
		}
	</style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-custom">
	<div class="container-fluid">
		<a class="navbar-brand" href="#">MyFin Bank - Admin</a>
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
			<a href="${pageContext.request.contextPath}/admin/logout" class="btn btn-outline-light btn-sm">Logout</a>
		</div>
	</div>
</nav>

<div class="container dashboard-container">
	<div class="row g-3">
		<div class="col-md-4">
			<div class="card p-3">
				<div class="card-body">
					<div class="card-icon">👤</div>
					<h5 class="card-title">Pending Customers</h5>
					<p class="card-text text-muted">View and approve customer registrations.</p>
					<a href="${pageContext.request.contextPath}/admin/customers/pending" class="btn btn-sm btn-danger">View Pending</a>
				</div>
			</div>
		</div>
		<div class="col-md-4">
			<div class="card p-3">
				<div class="card-body">
					<div class="card-icon">👥</div>
					<h5 class="card-title">All Customers</h5>
					<p class="card-text text-muted">View, search, and manage all customers.</p>
					<a href="${pageContext.request.contextPath}/admin/customers/all" class="btn btn-sm btn-danger">View All</a>
				</div>
			</div>
		</div>
		<div class="col-md-4">
			<div class="card p-3">
				<div class="card-body">
					<div class="card-icon">💰</div>
					<h5 class="card-title">Pending Loans</h5>
					<p class="card-text text-muted">Review and approve or deny loan applications.</p>
					<a href="${pageContext.request.contextPath}/admin/loans/pending" class="btn btn-sm btn-danger">View Pending</a>
				</div>
			</div>
		</div>
		<div class="col-md-4">
			<div class="card p-3">
				<div class="card-body">
					<div class="card-icon">💬</div>
					<h5 class="card-title">Customer Chats</h5>
					<p class="card-text text-muted">View and respond to customer queries.</p>
					<a href="${pageContext.request.contextPath}/admin/chat" class="btn btn-sm btn-danger">View Chats</a>
				</div>
			</div>
		</div>
		<div class="col-md-4">
			<div class="card p-3">
				<div class="card-body">
					<div class="card-icon">📨</div>
					<h5 class="card-title">Notifications</h5>
					<p class="card-text text-muted">View and configure email notifications.</p>
					<a href="#" class="btn btn-sm btn-danger disabled">Coming soon</a>
				</div>
			</div>
		</div>
	</div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
</body>
</html>

