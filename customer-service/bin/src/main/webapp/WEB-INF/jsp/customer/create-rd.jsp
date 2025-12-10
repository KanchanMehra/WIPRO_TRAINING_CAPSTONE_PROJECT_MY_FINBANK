<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>MyFin Bank - Create Recurring Deposit</title>
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
		.container-rd {
			padding: 40px 20px;
			max-width: 800px;
			margin: 0 auto;
		}
		.form-card {
			background: white;
			border-radius: 12px;
			box-shadow: 0 5px 20px rgba(0, 0, 0, 0.08);
			padding: 30px;
		}
		.page-header {
			text-align: center;
			margin-bottom: 30px;
		}
		.page-header h2 {
			color: #1f2937;
			font-weight: 700;
			font-size: 32px;
		}
		.form-group {
			margin-bottom: 20px;
		}
		.form-group label {
			font-weight: 600;
			color: #1f2937;
			margin-bottom: 8px;
			display: block;
		}
		.form-group input {
			width: 100%;
			padding: 12px;
			border: 2px solid #e5e7eb;
			border-radius: 6px;
			font-size: 14px;
		}
		.form-group input:focus {
			outline: none;
			border-color: #10b981;
			box-shadow: 0 0 0 3px rgba(16, 185, 129, 0.1);
		}
		.info-box {
			background: #d1fae5;
			border-left: 4px solid #10b981;
			padding: 20px;
			border-radius: 6px;
			margin: 20px 0;
		}
		.info-item {
			display: flex;
			justify-content: space-between;
			padding: 10px 0;
			border-bottom: 1px solid #a7f3d0;
		}
		.info-item:last-child {
			border-bottom: none;
		}
		.info-label {
			font-weight: 600;
			color: #065f46;
		}
		.info-value {
			font-weight: 700;
			color: #10b981;
			font-size: 18px;
		}
		.btn-submit {
			background: linear-gradient(135deg, #10b981 0%, #059669 100%);
			color: white;
			border: none;
			padding: 14px 40px;
			border-radius: 6px;
			font-weight: 600;
			font-size: 16px;
			cursor: pointer;
			width: 100%;
			margin-top: 20px;
		}
		.btn-submit:hover {
			transform: translateY(-2px);
			box-shadow: 0 5px 15px rgba(16, 185, 129, 0.3);
		}
		.alert {
			border-radius: 8px;
			padding: 12px 15px;
			margin-bottom: 20px;
		}
		.back-button {
			display: inline-block;
			color: #10b981;
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
				${customer.firstName} ${customer.lastName}
			</span>
			<a href="${pageContext.request.contextPath}/customer/logout" class="btn btn-outline-light btn-sm">Logout</a>
		</div>
	</div>
</nav>

<div class="container-rd">
	<div class="form-card">
		<div class="page-header">
			<h2>🏦 Create Recurring Deposit</h2>
			<p class="text-muted">Start saving with monthly deposits</p>
		</div>

		<% if (request.getAttribute("error") != null) { %>
		<div class="alert alert-danger">${error}</div>
		<% } %>

		<form method="post" action="${pageContext.request.contextPath}/customer/investment/rd/create">
			<div class="form-group">
				<label for="monthlyInstallment">Monthly Installment (₹) <span style="color: #ef4444;">*</span></label>
				<input type="number" id="monthlyInstallment" name="monthlyInstallment"
				       placeholder="Enter monthly installment" min="500" max="50000" step="100" required
				       onchange="calculateRD()"/>
				<small class="text-muted">Min: ₹500 | Max: ₹50,000</small>
			</div>

			<div class="form-group">
				<label for="durationInMonths">Duration (Months) <span style="color: #ef4444;">*</span></label>
				<input type="number" id="durationInMonths" name="durationInMonths"
				       placeholder="Enter duration in months" min="6" max="120" step="1" required
				       onchange="calculateRD()"/>
				<small class="text-muted">Min: 6 months | Max: 120 months (10 years)</small>
			</div>

			<div class="info-box" id="calculationBox" style="display: none;">
				<h5 style="margin-bottom: 15px; color: #065f46;">RD Summary</h5>
				<div class="info-item">
					<span class="info-label">Interest Rate:</span>
					<span class="info-value"><span id="interestRate">0</span>%</span>
				</div>
				<div class="info-item">
					<span class="info-label">Total Deposited:</span>
					<span class="info-value">₹<span id="totalDeposited">0</span></span>
				</div>
				<div class="info-item">
					<span class="info-label">Total Interest:</span>
					<span class="info-value">₹<span id="totalInterest">0</span></span>
				</div>
				<div class="info-item">
					<span class="info-label">Maturity Amount:</span>
					<span class="info-value">₹<span id="maturityAmount">0</span></span>
				</div>
				<div class="info-item">
					<span class="info-label">Maturity Date:</span>
					<span class="info-value" id="maturityDate">-</span>
				</div>
			</div>

			<button type="submit" class="btn-submit">Create Recurring Deposit</button>
		</form>
	</div>

	<a href="${pageContext.request.contextPath}/customer/dashboard" class="back-button">
		← Back to Dashboard
	</a>
</div>

<script>
	function calculateRD() {
		const installment = parseFloat(document.getElementById('monthlyInstallment').value);
		const months = parseInt(document.getElementById('durationInMonths').value);

		if (!installment || !months || installment < 500 || months < 6) {
			document.getElementById('calculationBox').style.display = 'none';
			return;
		}

		// Call backend API to calculate
		fetch('${pageContext.request.contextPath}/customer/investment/rd/calculate?monthlyInstallment=' + installment + '&durationInMonths=' + months)
			.then(response => response.json())
			.then(data => {
				if (data.error) {
					console.error(data.error);
					return;
				}

				document.getElementById('interestRate').textContent = parseFloat(data.interestRate).toFixed(2);
				document.getElementById('totalDeposited').textContent = parseFloat(data.totalDeposited).toLocaleString('en-IN', {minimumFractionDigits: 2});
				document.getElementById('totalInterest').textContent = parseFloat(data.totalInterest).toLocaleString('en-IN', {minimumFractionDigits: 2});
				document.getElementById('maturityAmount').textContent = parseFloat(data.maturityAmount).toLocaleString('en-IN', {minimumFractionDigits: 2});

				// Calculate maturity date
				const today = new Date();
				const maturityDate = new Date(today.setMonth(today.getMonth() + months));
				document.getElementById('maturityDate').textContent = maturityDate.toLocaleDateString('en-IN');

				document.getElementById('calculationBox').style.display = 'block';
			})
			.catch(error => console.error('Error:', error));
	}
</script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
</body>
</html>

