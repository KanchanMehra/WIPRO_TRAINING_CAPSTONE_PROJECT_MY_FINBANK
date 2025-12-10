<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>MyFin Bank - Create Fixed Deposit</title>
	<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
	<style>
		body {
			background: #f0f9ff;
			min-height: 100vh;
			font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
		}
		.navbar-custom {
			background: linear-gradient(135deg, #059669 0%, #047857 100%);
		}
		.navbar-custom .navbar-brand, .navbar-custom .nav-link, .navbar-custom .navbar-text {
			color: #f0f9ff !important;
		}
		.container-fd {
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
		.form-group input, .form-group select {
			width: 100%;
			padding: 12px;
			border: 2px solid #e5e7eb;
			border-radius: 6px;
			font-size: 14px;
		}
		.form-group input:focus, .form-group select:focus {
			outline: none;
			border-color: #059669;
			box-shadow: 0 0 0 3px rgba(5, 150, 105, 0.1);
		}
		.info-box {
			background: #d1fae5;
			border-left: 4px solid #059669;
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
			color: #059669;
			font-size: 18px;
		}
		.btn-submit {
			background: linear-gradient(135deg, #059669 0%, #047857 100%);
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
			box-shadow: 0 5px 15px rgba(5, 150, 105, 0.3);
		}
		.alert {
			border-radius: 8px;
			padding: 12px 15px;
			margin-bottom: 20px;
		}
		.back-button {
			display: inline-block;
			color: #059669;
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

<div class="container-fd">
	<div class="form-card">
		<div class="page-header">
			<h2>💎 Create Fixed Deposit</h2>
			<p class="text-muted">Invest lump sum and earn guaranteed returns</p>
		</div>

		<% if (request.getAttribute("error") != null) { %>
		<div class="alert alert-danger">${error}</div>
		<% } %>

		<form method="post" action="${pageContext.request.contextPath}/customer/investment/fd/create">
			<div class="form-group">
				<label for="principalAmount">Principal Amount (₹) <span style="color: #ef4444;">*</span></label>
				<input type="number" id="principalAmount" name="principalAmount"
				       placeholder="Enter principal amount" min="1000" max="1000000" step="100" required
				       onchange="calculateFD()"/>
				<small class="text-muted">Min: ₹1,000 | Max: ₹10,00,000</small>
			</div>

			<div class="form-group">
				<label for="durationInMonths">Duration <span style="color: #ef4444;">*</span></label>
				<select id="durationInMonths" name="durationInMonths" required onchange="calculateFD()">
					<option value="">-- Select Duration --</option>
					<option value="1">1 Month</option>
					<option value="3">3 Months</option>
					<option value="6">6 Months</option>
					<option value="12">1 Year (12 Months)</option>
					<option value="24">2 Years (24 Months)</option>
					<option value="36">3 Years (36 Months)</option>
					<option value="60">5 Years (60 Months)</option>
					<option value="120">10 Years (120 Months)</option>
				</select>
			</div>

			<div class="info-box" id="calculationBox" style="display: none;">
				<h5 style="margin-bottom: 15px; color: #065f46;">FD Summary</h5>
				<div class="info-item">
					<span class="info-label">Interest Rate:</span>
					<span class="info-value"><span id="interestRate">0</span>%</span>
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

			<button type="submit" class="btn-submit">Create Fixed Deposit</button>
		</form>
	</div>

	<a href="${pageContext.request.contextPath}/customer/dashboard" class="back-button">
		← Back to Dashboard
	</a>
</div>

<script>
	function calculateFD() {
		const principal = parseFloat(document.getElementById('principalAmount').value);
		const months = parseInt(document.getElementById('durationInMonths').value);

		if (!principal || !months || principal < 1000) {
			document.getElementById('calculationBox').style.display = 'none';
			return;
		}

		// Call backend API to calculate
		fetch('${pageContext.request.contextPath}/customer/investment/fd/calculate?principalAmount=' + principal + '&durationInMonths=' + months)
			.then(response => response.json())
			.then(data => {
				if (data.error) {
					console.error(data.error);
					return;
				}

				document.getElementById('interestRate').textContent = parseFloat(data.interestRate).toFixed(2);
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

