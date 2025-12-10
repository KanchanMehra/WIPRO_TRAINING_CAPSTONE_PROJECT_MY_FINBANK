<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>MyFin Bank - Admin Login</title>
	<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
	<style>
		body {
			background: linear-gradient(135deg, #991b1b 0%, #7f1d1d 100%);
			min-height: 100vh;
			display: flex;
			align-items: center;
			justify-content: center;
			font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
			padding: 20px;
		}
		.login-container {
			background: white;
			border-radius: 12px;
			box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
			width: 100%;
			max-width: 420px;
			padding: 30px;
		}
		.login-header {
			text-align: center;
			margin-bottom: 20px;
			border-bottom: 3px solid #dc2626;
			padding-bottom: 15px;
		}
		.login-header h1 {
			color: #dc2626;
			font-size: 24px;
			font-weight: 700;
			margin-bottom: 8px;
		}
		.login-header .badge-admin {
			display: inline-block;
			background: #dc2626;
			color: white;
			padding: 6px 12px;
			border-radius: 20px;
			font-size: 11px;
			font-weight: 600;
			margin-top: 8px;
		}
		.form-group {
			margin-bottom: 16px;
		}
		.form-group label {
			display: block;
			margin-bottom: 6px;
			color: #1f2937;
			font-weight: 500;
			font-size: 14px;
		}
		.form-group input[type="text"],
		.form-group input[type="password"] {
			width: 100%;
			padding: 10px 12px;
			border: 2px solid #e5e7eb;
			border-radius: 6px;
			font-size: 14px;
			transition: all 0.3s ease;
			background-color: #f9fafb;
		}
		.form-group input[type="text"]:focus,
		.form-group input[type="password"]:focus {
			outline: none;
			border-color: #dc2626;
			background-color: white;
			box-shadow: 0 0 0 3px rgba(220, 38, 38, 0.1);
		}
		.submit-btn {
			width: 100%;
			padding: 11px;
			background: linear-gradient(135deg, #dc2626, #ef4444);
			color: white;
			border: none;
			border-radius: 6px;
			font-size: 15px;
			font-weight: 600;
			cursor: pointer;
			transition: all 0.3s ease;
			margin-top: 10px;
		}
		.submit-btn:hover {
			transform: translateY(-1px);
			box-shadow: 0 10px 20px rgba(220, 38, 38, 0.3);
		}
		.error-message {
			margin-bottom: 12px;
			color: #b91c1c;
			background-color: #fee2e2;
			border: 1px solid #fecaca;
			border-radius: 6px;
			padding: 8px 10px;
			font-size: 13px;
		}
	</style>
</head>
<body>
<div class="login-container">
	<div class="login-header">
		<h1>🔐 MyFin Bank</h1>
		<div class="badge-admin">ADMIN LOGIN</div>
		<p class="text-muted small mt-2">Sign in to access the admin dashboard</p>
	</div>

	<c:if test="${not empty error}">
		<div class="error-message">${error}</div>
	</c:if>

	<form method="post" action="${pageContext.request.contextPath}/admin/login">
		<div class="form-group">
			<label for="username">Username</label>
			<input type="text" id="username" name="username" required />
		</div>

		<div class="form-group">
			<label for="password">Password</label>
			<input type="password" id="password" name="password" required />
		</div>

		<button type="submit" class="submit-btn">Login</button>
	</form>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
</body>
</html>

