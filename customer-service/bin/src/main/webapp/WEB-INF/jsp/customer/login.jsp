<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>MyFin Bank - Customer Login</title>
	<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
	<style>
		body {
			background: linear-gradient(135deg, #0891b2 0%, #06b6d4 100%);
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
			padding: 40px;
			animation: slideUp 0.5s ease-out;
		}
		@keyframes slideUp {
			from {
				opacity: 0;
				transform: translateY(30px);
			}
			to {
				opacity: 1;
				transform: translateY(0);
			}
		}
		.login-header {
			text-align: center;
			margin-bottom: 30px;
			border-bottom: 3px solid #0891b2;
			padding-bottom: 20px;
		}
		.login-header h1 {
			color: #0891b2;
			font-size: 28px;
			font-weight: 700;
			margin-bottom: 8px;
		}
		.login-header .badge-customer {
			display: inline-block;
			background: #0891b2;
			color: white;
			padding: 6px 14px;
			border-radius: 20px;
			font-size: 12px;
			font-weight: 600;
			margin-top: 8px;
		}
		.form-group {
			margin-bottom: 20px;
		}
		.form-group label {
			display: block;
			margin-bottom: 8px;
			color: #1f2937;
			font-weight: 500;
			font-size: 14px;
		}
		.form-group input[type="text"],
		.form-group input[type="password"] {
			width: 100%;
			padding: 12px 15px;
			border: 2px solid #e5e7eb;
			border-radius: 6px;
			font-size: 14px;
			transition: all 0.3s ease;
			background-color: #f9fafb;
		}
		.form-group input[type="text"]:focus,
		.form-group input[type="password"]:focus {
			outline: none;
			border-color: #0891b2;
			background-color: white;
			box-shadow: 0 0 0 3px rgba(8, 145, 178, 0.1);
		}
		.submit-btn {
			width: 100%;
			padding: 12px;
			background: linear-gradient(135deg, #0891b2, #06b6d4);
			color: white;
			border: none;
			border-radius: 6px;
			font-size: 16px;
			font-weight: 600;
			cursor: pointer;
			transition: all 0.3s ease;
			margin-top: 10px;
		}
		.submit-btn:hover {
			transform: translateY(-2px);
			box-shadow: 0 10px 20px rgba(8, 145, 178, 0.3);
		}
		.submit-btn:active {
			transform: translateY(0);
		}
		.error-message {
			margin-bottom: 20px;
			color: #b91c1c;
			background-color: #fee2e2;
			border: 1px solid #fecaca;
			border-radius: 6px;
			padding: 12px 15px;
			font-size: 14px;
			display: flex;
			align-items: center;
		}
		.error-message::before {
			content: "⚠️";
			margin-right: 10px;
			font-size: 18px;
		}
		.register-link {
			text-align: center;
			margin-top: 20px;
			color: #6b7280;
			font-size: 14px;
		}
		.register-link a {
			color: #0891b2;
			text-decoration: none;
			font-weight: 600;
		}
		.register-link a:hover {
			text-decoration: underline;
		}
	</style>
</head>
<body>
<div class="login-container">
	<div class="login-header">
		<h1>🏦 MyFin Bank</h1>
		<div class="badge-customer">CUSTOMER PORTAL</div>
		<p class="text-muted small mt-2">Sign in to access your account</p>
	</div>

	<c:if test="${not empty error}">
		<div class="error-message">${error}</div>
	</c:if>

	<form method="post" action="${pageContext.request.contextPath}/customer/login">
		<div class="form-group">
			<label for="username">Username</label>
			<input type="text" id="username" name="username" required autofocus placeholder="Enter your username"/>
		</div>

		<div class="form-group">
			<label for="password">Password</label>
			<input type="password" id="password" name="password" required placeholder="Enter your password"/>
		</div>

		<button type="submit" class="submit-btn">Login to Account</button>
	</form>

	<div class="register-link">
		Don't have an account? <a href="${pageContext.request.contextPath}/customer/register">Register here</a>
	</div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
</body>
</html>

