<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>MyFin Bank - EMI Calculator</title>
	<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
	<style>
		body {
			background: #f0f9ff;
			min-height: 100vh;
			font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
		}
		.navbar-custom {
			background: linear-gradient(135deg, #0891b2 0%, #06b6d4 100%);
		}
		.navbar-custom .navbar-brand,
		.navbar-custom .nav-link,
		.navbar-custom .navbar-text {
			color: #f0f9ff !important;
		}
		.container-calculator {
			padding: 40px 20px;
			max-width: 1000px;
			margin: 0 auto;
		}
		.calculator-card {
			background: white;
			border-radius: 12px;
			box-shadow: 0 5px 20px rgba(0, 0, 0, 0.08);
			padding: 30px;
			margin-bottom: 30px;
		}
		.calculator-header {
			text-align: center;
			margin-bottom: 40px;
		}
		.calculator-header h2 {
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
			border-color: #0891b2;
			box-shadow: 0 0 0 3px rgba(8, 145, 178, 0.1);
		}
		.input-row {
			display: grid;
			grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
			gap: 20px;
		}
		.btn-calculate {
			background: linear-gradient(135deg, #0891b2 0%, #06b6d4 100%);
			color: white;
			border: none;
			padding: 14px 40px;
			border-radius: 6px;
			font-weight: 600;
			font-size: 16px;
			cursor: pointer;
			width: 100%;
			transition: all 0.3s;
		}
		.btn-calculate:hover {
			transform: translateY(-2px);
			box-shadow: 0 5px 15px rgba(8, 145, 178, 0.3);
		}
		.results-card {
			display: none;
			background: #f0fdf4;
			border-left: 4px solid #10b981;
			border-radius: 8px;
			padding: 25px;
			margin-top: 30px;
		}
		.results-card.show {
			display: block;
		}
		.result-item {
			display: flex;
			justify-content: space-between;
			padding: 15px 0;
			border-bottom: 1px solid #e5e7eb;
		}
		.result-item:last-child {
			border-bottom: none;
		}
		.result-label {
			font-weight: 600;
			color: #1f2937;
		}
		.result-value {
			font-size: 18px;
			font-weight: 700;
			color: #10b981;
		}
		.emi-highlight {
			background: #dbeafe;
			padding: 20px;
			border-radius: 8px;
			text-align: center;
			margin: 20px 0;
		}
		.emi-label {
			font-size: 14px;
			color: #6b7280;
			text-transform: uppercase;
			font-weight: 600;
		}
		.emi-value {
			font-size: 36px;
			font-weight: 700;
			color: #0284c7;
			margin: 10px 0;
		}
		.breakdown-table {
			width: 100%;
			border-collapse: collapse;
			margin-top: 20px;
		}
		.breakdown-table th,
		.breakdown-table td {
			padding: 12px;
			text-align: right;
			border-bottom: 1px solid #e5e7eb;
		}
		.breakdown-table th {
			background: #f3f4f6;
			font-weight: 600;
			color: #1f2937;
		}
		.breakdown-table td:first-child,
		.breakdown-table th:first-child {
			text-align: left;
		}
		.btn-apply {
			background: #10b981;
			color: white;
			border: none;
			padding: 12px 30px;
			border-radius: 6px;
			font-weight: 600;
			cursor: pointer;
			margin-top: 20px;
		}
		.btn-apply:hover {
			background: #059669;
		}
		.back-button {
			display: inline-block;
			color: #0891b2;
			text-decoration: none;
			font-weight: 600;
			margin-top: 20px;
		}
		.back-button:hover {
			text-decoration: underline;
		}
		.error-message {
			background: #fee2e2;
			color: #991b1b;
			padding: 12px 15px;
			border-radius: 6px;
			margin-bottom: 20px;
			display: none;
		}
		.error-message.show {
			display: block;
		}
		.loading {
			display: none;
			text-align: center;
			padding: 20px;
		}
		.loading.show {
			display: block;
		}
		.spinner {
			border: 4px solid #f3f4f6;
			border-top: 4px solid #0891b2;
			border-radius: 50%;
			width: 30px;
			height: 30px;
			animation: spin 1s linear infinite;
			margin: 0 auto;
		}
		@keyframes spin {
			0% { transform: rotate(0deg); }
			100% { transform: rotate(360deg); }
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

<div class="container-calculator">
	<div class="calculator-header">
		<h2>🧮 Loan EMI Calculator</h2>
		<p class="text-muted">Calculate your monthly EMI and understand your loan better</p>
	</div>

	<div class="calculator-card">
		<div class="error-message" id="errorMessage"></div>

		<form id="calculatorForm">
			<div class="input-row">
				<div class="form-group">
					<label for="loanAmount">Loan Amount (₹)</label>
					<input type="number" id="loanAmount" name="loanAmount" placeholder="Enter loan amount"
					       min="1000" max="10000000" step="1000" required/>
					<small class="text-muted">Min: ₹1,000 | Max: ₹1,00,00,000</small>
				</div>
				<div class="form-group">
					<label for="interestRate">Interest Rate (% per annum)</label>
					<input type="number" id="interestRate" name="interestRate" placeholder="Interest rate"
					       min="0" max="20" step="0.01" required readonly/>
					<small class="text-muted">Auto-calculated based on amount</small>
				</div>
				<div class="form-group">
					<label for="months">Duration (Months)</label>
					<input type="number" id="months" name="months" placeholder="Enter duration in months"
					       min="1" max="600" step="1" required/>
					<small class="text-muted">Min: 1 | Max: 600</small>
				</div>
			</div>

			<button type="button" class="btn-calculate" onclick="calculateEMI()">Calculate EMI</button>
		</form>

		<!-- Loading Indicator -->
		<div class="loading" id="loading">
			<div class="spinner"></div>
			<p>Calculating EMI...</p>
		</div>

		<!-- Results Section -->
		<div class="results-card" id="resultsCard">
			<h4 style="margin-bottom: 20px; color: #1f2937;">📊 Calculation Results</h4>

			<div class="emi-highlight">
				<div class="emi-label">Monthly EMI</div>
				<div class="emi-value">₹<span id="emiAmount">0</span></div>
			</div>

			<div class="result-item">
				<span class="result-label">Total Amount Payable</span>
				<span class="result-value">₹<span id="totalAmount">0</span></span>
			</div>
			<div class="result-item">
				<span class="result-label">Total Interest Amount</span>
				<span class="result-value">₹<span id="totalInterest">0</span></span>
			</div>

			<h5 style="margin-top: 30px; margin-bottom: 15px; color: #1f2937;">Month-by-Month Breakdown</h5>
			<div style="overflow-x: auto;">
				<table class="breakdown-table">
					<thead>
					<tr>
						<th>Month</th>
						<th>Principal</th>
						<th>Interest</th>
						<th>EMI</th>
						<th>Balance</th>
					</tr>
					</thead>
					<tbody id="breakdownBody">
					</tbody>
				</table>
			</div>

			<button class="btn-apply" onclick="applyForLoan()">Apply for This Loan</button>
		</div>
	</div>

	<a href="${pageContext.request.contextPath}/customer/dashboard" class="back-button">
		← Back to Dashboard
	</a>
</div>

<script>
	function calculateEMI() {
		const amount = document.getElementById('loanAmount').value;
		const months = document.getElementById('months').value;

		// Validate inputs
		if (!amount || !months) {
			showError('Please fill in all required fields');
			return;
		}

		if (amount <= 0 || months <= 0) {
			showError('Amount and months must be greater than zero');
			return;
		}

		// Get interest rate based on amount
		const interestRate = getInterestRate(parseFloat(amount));
		document.getElementById('interestRate').value = interestRate.toFixed(2);

		// Show loading
		document.getElementById('loading').classList.add('show');
		document.getElementById('resultsCard').classList.remove('show');
		hideError();

		// Make API call
		fetch('${pageContext.request.contextPath}/customer/loan/calculate', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/x-www-form-urlencoded'
			},
			body: 'amount=' + encodeURIComponent(amount) +
			      '&rate=' + encodeURIComponent(interestRate) +
			      '&months=' + encodeURIComponent(months)
		})
		.then(response => {
			if (!response.ok) throw new Error('Calculation failed');
			return response.json();
		})
		.then(data => {
			displayResults(data, interestRate);
			document.getElementById('loading').classList.remove('show');
			document.getElementById('resultsCard').classList.add('show');
		})
		.catch(error => {
			console.error('Error:', error);
			showError('Error calculating EMI: ' + error.message);
			document.getElementById('loading').classList.remove('show');
		});
	}

	function getInterestRate(amount) {
		if (amount < 100000) return 8;
		else if (amount < 500000) return 9;
		else if (amount < 1000000) return 10;
		else return 11;
	}

	function displayResults(data, interestRate) {
		// Display main results
		document.getElementById('emiAmount').textContent = formatCurrency(data.emi);
		document.getElementById('totalAmount').textContent = formatCurrency(data.totalAmount);
		document.getElementById('totalInterest').textContent = formatCurrency(data.totalInterest);

		// Display monthly breakdown
		const breakdownBody = document.getElementById('breakdownBody');
		breakdownBody.innerHTML = '';

		data.monthlyBreakdown.forEach(month => {
			// Format values first
			const principalFormatted = formatCurrency(month.principalPaid);
			const interestFormatted = formatCurrency(month.interestPaid);
			const emiFormatted = formatCurrency(month.emi);
			const balanceFormatted = formatCurrency(month.balanceRemaining);

			// Build row with already-formatted values
			const row = '<tr>' +
				'<td>' + month.month + '</td>' +
				'<td>₹' + principalFormatted + '</td>' +
				'<td>₹' + interestFormatted + '</td>' +
				'<td>₹' + emiFormatted + '</td>' +
				'<td>₹' + balanceFormatted + '</td>' +
				'</tr>';
			breakdownBody.innerHTML += row;
		});
	}

	function formatCurrency(value) {
		return parseFloat(value).toLocaleString('en-IN', {
			minimumFractionDigits: 2,
			maximumFractionDigits: 2
		});
	}


	function applyForLoan() {
		const amount = document.getElementById('loanAmount').value;
		const months = document.getElementById('months').value;
		// Redirect to loan application with params
		window.location.href = '${pageContext.request.contextPath}/customer/loan/apply?amount=' + amount + '&months=' + months;
	}

	function showError(message) {
		const errorEl = document.getElementById('errorMessage');
		errorEl.textContent = message;
		errorEl.classList.add('show');
	}

	function hideError() {
		document.getElementById('errorMessage').classList.remove('show');
	}

	// Update interest rate when amount changes
	document.getElementById('loanAmount').addEventListener('change', function() {
		const amount = parseFloat(this.value);
		if (amount > 0) {
			const rate = getInterestRate(amount);
			document.getElementById('interestRate').value = rate.toFixed(2);
		}
	});
</script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
</body>
</html>

