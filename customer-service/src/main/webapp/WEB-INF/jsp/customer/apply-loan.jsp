<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>MyFin Bank - Apply for Loan</title>
	<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
	<style>
		body {
			background: #f0f9ff;
			min-height: 100vh;
			font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
		}
		.navbar-custom {
			background: linear-gradient(135deg, #9333ea 0%, #7e22ce 100%);
		}
		.navbar-custom .navbar-brand,
		.navbar-custom .nav-link,
		.navbar-custom .navbar-text {
			color: #f0f9ff !important;
		}
		.container-apply {
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
		.form-header {
			text-align: center;
			margin-bottom: 30px;
		}
		.form-header h2 {
			color: #1f2937;
			font-weight: 700;
			font-size: 28px;
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
		.form-group input,
		.form-group select,
		.form-group textarea {
			width: 100%;
			padding: 12px;
			border: 2px solid #e5e7eb;
			border-radius: 6px;
			font-size: 14px;
			font-family: inherit;
		}
		.form-group input:focus,
		.form-group select:focus,
		.form-group textarea:focus {
			outline: none;
			border-color: #9333ea;
			box-shadow: 0 0 0 3px rgba(147, 51, 234, 0.1);
		}
		.info-box {
			background: #ede9fe;
			border-left: 4px solid #9333ea;
			padding: 15px;
			border-radius: 6px;
			margin: 20px 0;
		}
		.info-box-item {
			display: flex;
			justify-content: space-between;
			padding: 8px 0;
			font-size: 14px;
		}
		.info-label {
			color: #6b7280;
			font-weight: 600;
		}
		.info-value {
			color: #9333ea;
			font-weight: 700;
		}
		.form-row {
			display: grid;
			grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
			gap: 20px;
		}
		.btn-apply {
			background: linear-gradient(135deg, #9333ea 0%, #7e22ce 100%);
			color: white;
			border: none;
			padding: 14px 40px;
			border-radius: 6px;
			font-weight: 600;
			font-size: 16px;
			cursor: pointer;
			width: 100%;
			transition: all 0.3s;
			margin-top: 20px;
		}
		.btn-apply:hover {
			transform: translateY(-2px);
			box-shadow: 0 5px 15px rgba(147, 51, 234, 0.3);
		}
		.alert {
			border-radius: 8px;
			padding: 12px 15px;
			margin-bottom: 20px;
		}
		.back-button {
			display: inline-block;
			color: #9333ea;
			text-decoration: none;
			font-weight: 600;
			margin-top: 20px;
		}
		.back-button:hover {
			text-decoration: underline;
		}
		.required {
			color: #ef4444;
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
				${sessionScope.loggedInCustomer.firstName} ${sessionScope.loggedInCustomer.lastName}
			</span>
			<a href="${pageContext.request.contextPath}/customer/logout" class="btn btn-outline-light btn-sm">Logout</a>
		</div>
	</div>
</nav>

<div class="container-apply">
	<div class="form-card">
		<div class="form-header">
			<h2>📋 Apply for Loan</h2>
			<p class="text-muted">Fill in the details below to apply for a loan</p>
		</div>

		<% if (request.getAttribute("error") != null) { %>
		<div class="alert alert-danger">${error}</div>
		<% } %>

		<form method="post" action="${pageContext.request.contextPath}/customer/loan/apply">
			<div class="form-row">
				<div class="form-group">
					<label for="amount">Loan Amount (₹) <span class="required">*</span></label>
					<input type="number" id="amount" name="amount"
					       placeholder="Enter loan amount" min="1000" max="10000000" step="1000"
					       value="${param.amount}" required onchange="updateEmiEstimate()"/>
					<small class="text-muted">Min: ₹1,000 | Max: ₹1,00,00,000</small>
				</div>
				<div class="form-group">
					<label for="months">Duration (Months) <span class="required">*</span></label>
					<input type="number" id="months" name="months"
					       placeholder="Enter duration in months" min="1" max="600" step="1"
					       value="${param.months}" required onchange="updateEmiEstimate()"/>
					<small class="text-muted">Min: 1 | Max: 600</small>
				</div>
			</div>

			<div class="form-group">
				<label for="purpose">Purpose of Loan <span class="required">*</span></label>
				<select id="purpose" name="purpose" required>
					<option value="">-- Select Purpose --</option>
					<option value="Personal">Personal</option>
					<option value="Home">Home</option>
					<option value="Auto">Auto</option>
					<option value="Business">Business</option>
					<option value="Education">Education</option>
					<option value="Other">Other</option>
				</select>
			</div>

			<div class="form-row">
				<div class="form-group">
					<label for="requiredByDate">Required By Date (Optional)</label>
					<input type="date" id="requiredByDate" name="requiredByDate"/>
				</div>
			</div>

			<div class="form-group">
				<label for="remarks">Additional Remarks (Optional)</label>
				<textarea id="remarks" name="remarks" placeholder="Enter any additional information..." rows="4"></textarea>
			</div>

			<!-- Info Box -->
			<div class="info-box">
				<h5 style="margin-bottom: 10px; color: #1f2937;">Loan Summary</h5>
				<div class="info-box-item">
					<span class="info-label">Interest Rate:</span>
					<span class="info-value"><span id="estimatedRate">8.00</span>%</span>
				</div>
				<div class="info-box-item">
					<span class="info-label">Estimated Monthly EMI:</span>
					<span class="info-value">₹<span id="estimatedEmi">0</span></span>
				</div>
				<div class="info-box-item">
					<span class="info-label">Total Amount Payable:</span>
					<span class="info-value">₹<span id="estimatedTotal">0</span></span>
				</div>
			</div>

			<button type="submit" class="btn-apply">Submit Loan Application</button>
		</form>
	</div>

	<a href="${pageContext.request.contextPath}/customer/dashboard" class="back-button">
		← Back to Dashboard
	</a>
</div>

<script>
	function getInterestRate(amount) {
		const amountNum = parseFloat(amount);
		if (amountNum < 100000) return 8;
		else if (amountNum < 500000) return 9;
		else if (amountNum < 1000000) return 10;
		else return 11;
	}

	function updateEmiEstimate() {
		const amount = parseFloat(document.getElementById('amount').value) || 0;
		const months = parseInt(document.getElementById('months').value) || 0;

		if (amount <= 0 || months <= 0) {
			document.getElementById('estimatedEmi').textContent = '0';
			document.getElementById('estimatedTotal').textContent = '0';
			return;
		}

		const rate = getInterestRate(amount);
		const monthlyRate = rate / 12 / 100;

		// EMI = P × [r(1+r)^n] / [(1+r)^n - 1]
		const rPlusOne = 1 + monthlyRate;
		const rPlusOneToN = Math.pow(rPlusOne, months);
		const numerator = monthlyRate * rPlusOneToN;
		const denominator = rPlusOneToN - 1;
		const emi = amount * (numerator / denominator);
		const total = emi * months;

		document.getElementById('estimatedRate').textContent = rate.toFixed(2);
		document.getElementById('estimatedEmi').textContent = Math.round(emi).toLocaleString('en-IN');
		document.getElementById('estimatedTotal').textContent = Math.round(total).toLocaleString('en-IN');
	}

	// Initialize on page load
	document.addEventListener('DOMContentLoaded', updateEmiEstimate);
</script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
</body>
</html>

