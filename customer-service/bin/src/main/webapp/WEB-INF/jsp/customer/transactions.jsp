<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>MyFin Bank - Transaction History</title>
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
		.container-transactions {
			padding: 30px 20px;
		}
		.card {
			border-radius: 12px;
			box-shadow: 0 5px 20px rgba(0, 0, 0, 0.08);
			border: none;
			margin-bottom: 20px;
		}
		.card-header-custom {
			background: linear-gradient(135deg, #0ea5e9 0%, #0284c7 100%);
			color: white;
			font-weight: 600;
			border-radius: 12px 12px 0 0 !important;
			padding: 15px;
		}
		.summary-cards {
			display: grid;
			grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
			gap: 15px;
			margin-bottom: 30px;
		}
		.summary-card {
			background: white;
			padding: 20px;
			border-radius: 12px;
			box-shadow: 0 5px 20px rgba(0, 0, 0, 0.08);
			text-align: center;
		}
		.summary-card .label {
			font-size: 12px;
			color: #6b7280;
			text-transform: uppercase;
			font-weight: 600;
			margin-bottom: 10px;
		}
		.summary-card .value {
			font-size: 24px;
			font-weight: 700;
			color: #1f2937;
		}
		.summary-card.deposits .value { color: #10b981; }
		.summary-card.withdrawals .value { color: #ef4444; }
		.summary-card.net .value { color: #0891b2; }
		.summary-card.total .value { color: #8b5cf6; }

		.filter-section {
			background: white;
			padding: 20px;
			border-radius: 12px;
			margin-bottom: 20px;
			box-shadow: 0 5px 20px rgba(0, 0, 0, 0.08);
		}
		.filter-row {
			display: grid;
			grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
			gap: 15px;
			margin-bottom: 15px;
		}
		.form-group {
			margin-bottom: 0;
		}
		.form-group label {
			font-size: 13px;
			color: #6b7280;
			text-transform: uppercase;
			font-weight: 600;
			margin-bottom: 5px;
			display: block;
		}
		.form-group select,
		.form-group input {
			width: 100%;
			padding: 10px;
			border: 2px solid #e5e7eb;
			border-radius: 6px;
			font-size: 14px;
		}
		.form-group select:focus,
		.form-group input:focus {
			outline: none;
			border-color: #0891b2;
			box-shadow: 0 0 0 3px rgba(8, 145, 178, 0.1);
		}
		.filter-buttons {
			display: flex;
			gap: 10px;
		}
		.btn-filter {
			background: #0891b2;
			color: white;
			border: none;
			padding: 10px 20px;
			border-radius: 6px;
			font-weight: 600;
			cursor: pointer;
			transition: all 0.3s;
		}
		.btn-filter:hover {
			background: #06b6d4;
		}
		.btn-reset {
			background: #e5e7eb;
			color: #1f2937;
			border: none;
			padding: 10px 20px;
			border-radius: 6px;
			font-weight: 600;
			cursor: pointer;
			transition: all 0.3s;
		}
		.btn-reset:hover {
			background: #d1d5db;
		}
		.table-responsive {
			border-radius: 12px;
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
		}
		.table td {
			padding: 12px 15px;
			border-bottom: 1px solid #f0f0f0;
		}
		.transaction-type {
			display: inline-flex;
			align-items: center;
			gap: 8px;
			padding: 6px 12px;
			border-radius: 6px;
			font-size: 13px;
			font-weight: 600;
		}
		.type-deposit {
			background: #d1fae5;
			color: #065f46;
		}
		.type-withdraw {
			background: #fee2e2;
			color: #991b1b;
		}
		.type-transfer-out {
			background: #dbeafe;
			color: #1e40af;
		}
		.type-transfer-in {
			background: #dbeafe;
			color: #1e40af;
		}
		.amount {
			font-weight: 600;
		}
		.amount-positive {
			color: #10b981;
		}
		.amount-negative {
			color: #ef4444;
		}
		.empty-state {
			text-align: center;
			padding: 60px 20px;
			color: #6b7280;
		}
		.empty-state-icon {
			font-size: 64px;
			margin-bottom: 20px;
			color: #d1d5db;
		}
		.pagination {
			justify-content: center;
			margin-top: 20px;
		}
		.back-button {
			display: inline-block;
			margin-top: 20px;
			color: #0891b2;
			text-decoration: none;
			font-weight: 600;
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
				${sessionScope.loggedInCustomer.firstName} ${sessionScope.loggedInCustomer.lastName}
			</span>
			<a href="${pageContext.request.contextPath}/customer/logout" class="btn btn-outline-light btn-sm">Logout</a>
		</div>
	</div>
</nav>

<!-- Hidden input to store customer ID from server -->
<input type="hidden" id="hiddenCustomerId" value="${sessionScope.loggedInCustomer.id}" />

<div class="container-transactions">
	<h2 class="mb-4">📊 Transaction History</h2>

	<!-- Summary Cards -->
	<div class="summary-cards" id="summaryCards">
		<!-- Loaded via JavaScript -->
	</div>

	<!-- Filter Section -->
	<div class="filter-section">
		<h5 class="mb-3">🔍 Filter Transactions</h5>
		<form id="filterForm" method="get">
			<div class="filter-row">
				<div class="form-group">
					<label for="typeFilter">Transaction Type</label>
					<select id="typeFilter" name="type">
						<option value="">All Types</option>
						<option value="DEPOSIT">💰 Deposits</option>
						<option value="WITHDRAW">💸 Withdrawals</option>
						<option value="TRANSFER_OUT">➡️ Transfers Out</option>
						<option value="TRANSFER_IN">⬅️ Transfers In</option>
					</select>
				</div>
				<div class="form-group">
					<label for="fromDate">From Date</label>
					<input type="date" id="fromDate" name="fromDate"/>
				</div>
				<div class="form-group">
					<label for="toDate">To Date</label>
					<input type="date" id="toDate" name="toDate"/>
				</div>
			</div>
			<div class="filter-buttons">
				<button type="button" class="btn-filter" onclick="applyFilters()">Apply Filters</button>
				<button type="button" class="btn-reset" onclick="resetFilters()">Reset</button>
			</div>
		</form>
	</div>

	<!-- Transactions Table -->
	<div class="card">
		<div class="card-header-custom">
			<h5 class="mb-0">📋 Recent Transactions</h5>
		</div>
		<div class="table-responsive" id="transactionsContainer">
			<!-- Loaded via JavaScript -->
		</div>
	</div>

	<a href="${pageContext.request.contextPath}/customer/dashboard" class="back-button">
		← Back to Dashboard
	</a>
</div>

<script>
	// Declare customerId in global scope
	var customerId = null;
	var allTransactions = [];

	// Load transactions on page load
	document.addEventListener('DOMContentLoaded', function() {
		// Get customer ID from hidden input AFTER DOM is loaded
		const hiddenInput = document.getElementById('hiddenCustomerId');
		if (hiddenInput) {
			customerId = hiddenInput.value;
			console.log('Customer ID set to:', customerId);
		}

		// If no customer ID, show error
		if (!customerId || customerId === 'undefined' || customerId === '' || customerId === 'null') {
			console.error('Customer ID is invalid:', customerId);
			document.getElementById('transactionsContainer').innerHTML =
				'<div style="padding: 40px; text-align: center; color: #ef4444;"><strong>Error:</strong> Customer ID not found. Please login again.</div>';
			document.getElementById('summaryCards').innerHTML = '';
			return;
		}

		console.log('Page loaded, loading transactions for customer:', customerId);
		loadTransactions();
		loadSummary();
	});

	function loadTransactions(type, fromDate, toDate) {
		// Use default parameters
		type = type || '';
		fromDate = fromDate || '';
		toDate = toDate || '';

		console.log('loadTransactions called with customerId:', customerId);

		if (!customerId || customerId === 'undefined' || customerId === '' || customerId === 'null') {
			console.error('Customer ID is not set in loadTransactions');
			return;
		}

		// Build URL with customer ID
		var url = '/api/transactions/customer/' + customerId;
		var params = new URLSearchParams();

		if (type) params.append('type', type);
		if (fromDate) params.append('fromDate', fromDate);
		if (toDate) params.append('toDate', toDate);

		if (params.toString()) {
			url += '?' + params.toString();
		}

		console.log('Fetching from URL:', url);

		fetch(url)
			.then(function(response) {
				console.log('Response status:', response.status);
				if (!response.ok) {
					throw new Error('API returned status ' + response.status);
				}
				return response.json();
			})
			.then(function(transactions) {
				console.log('Transactions received:', transactions);
				allTransactions = transactions;
				displayTransactions(transactions);
			})
			.catch(function(error) {
				console.error('Error loading transactions:', error);
				document.getElementById('transactionsContainer').innerHTML =
					'<div style="padding: 40px; text-align: center; color: #ef4444;"><strong>Error:</strong> ' + error.message + '</div>';
			});
	}

	function loadSummary() {
		console.log('loadSummary called with customerId:', customerId);

		if (!customerId || customerId === 'undefined' || customerId === '' || customerId === 'null') {
			console.error('Customer ID is not set for summary');
			return;
		}

		// Build URL with customer ID
		var url = '/api/transactions/customer/' + customerId + '/summary';
		console.log('Loading summary from URL:', url);

		fetch(url)
			.then(function(response) {
				console.log('Summary response status:', response.status);
				if (!response.ok) {
					throw new Error('Summary API returned status ' + response.status);
				}
				return response.json();
			})
			.then(function(summary) {
				console.log('Summary received:', summary);
				displaySummary(summary);
			})
			.catch(function(error) {
				console.error('Error loading summary:', error);
			});
	}

	function displaySummary(summary) {
		var html = '';
		html += '<div class="summary-card total">';
		html += '<div class="label">Total Transactions</div>';
		html += '<div class="value">' + summary.totalTransactions + '</div>';
		html += '</div>';
		html += '<div class="summary-card deposits">';
		html += '<div class="label">Total Deposits</div>';
		html += '<div class="value">₹' + summary.totalDeposits + '</div>';
		html += '</div>';
		html += '<div class="summary-card withdrawals">';
		html += '<div class="label">Total Withdrawals</div>';
		html += '<div class="value">₹' + summary.totalWithdrawals + '</div>';
		html += '</div>';
		html += '<div class="summary-card net">';
		html += '<div class="label">Net Change</div>';
		html += '<div class="value">₹' + summary.netChange + '</div>';
		html += '</div>';
		document.getElementById('summaryCards').innerHTML = html;
	}

	function displayTransactions(transactions) {
		console.log('Displaying transactions, count:', transactions.length);

		if (!transactions || transactions.length === 0) {
			document.getElementById('transactionsContainer').innerHTML =
				'<div class="empty-state">' +
				'<div class="empty-state-icon">📭</div>' +
				'<h4>No Transactions</h4>' +
				'<p>You have no transactions matching the current filters.</p>' +
				'</div>';
			return;
		}

		var html = '';
		html += '<table class="table">';
		html += '<thead><tr>';
		html += '<th>Transaction ID</th>';
		html += '<th>Type</th>';
		html += '<th>Amount</th>';
		html += '<th>Balance Before</th>';
		html += '<th>Balance After</th>';
		html += '<th>Date & Time</th>';
		html += '</tr></thead>';
		html += '<tbody>';

		for (var i = 0; i < transactions.length; i++) {
			var txn = transactions[i];
			var typeLabel = getTransactionTypeLabel(txn.type);
			var amountClass = (txn.type === 'DEPOSIT' || txn.type === 'TRANSFER_IN') ? 'amount-positive' : 'amount-negative';
			var amountSign = (txn.type === 'DEPOSIT' || txn.type === 'TRANSFER_IN') ? '+' : '-';
			var icon = getTransactionIcon(txn.type);
			var typeClass = getTypeClass(txn.type);

			// Format date
			var formattedDate = '';
			try {
				var date = new Date(txn.transactionDate);
				formattedDate = date.toLocaleString('en-IN');
			} catch (e) {
				formattedDate = txn.transactionDate;
			}

			html += '<tr>';
			html += '<td><strong>' + txn.transactionId + '</strong></td>';
			html += '<td><span class="transaction-type ' + typeClass + '">' + icon + ' ' + typeLabel + '</span></td>';
			html += '<td><span class="amount ' + amountClass + '">' + amountSign + '₹' + txn.amount + '</span></td>';
			html += '<td>₹' + txn.balanceBefore + '</td>';
			html += '<td>₹' + txn.balanceAfter + '</td>';
			html += '<td><small>' + formattedDate + '</small></td>';
			html += '</tr>';
		}

		html += '</tbody>';
		html += '</table>';

		document.getElementById('transactionsContainer').innerHTML = html;
		console.log('Transactions displayed successfully');
	}

	function getTransactionTypeLabel(type) {
		const labels = {
			'DEPOSIT': 'Deposit',
			'WITHDRAW': 'Withdrawal',
			'TRANSFER_OUT': 'Transfer Sent',
			'TRANSFER_IN': 'Transfer Received'
		};
		return labels[type] || type;
	}

	function getTransactionIcon(type) {
		const icons = {
			'DEPOSIT': '💰',
			'WITHDRAW': '💸',
			'TRANSFER_OUT': '➡️',
			'TRANSFER_IN': '⬅️'
		};
		return icons[type] || '💳';
	}

	function getTypeClass(type) {
		const classes = {
			'DEPOSIT': 'type-deposit',
			'WITHDRAW': 'type-withdraw',
			'TRANSFER_OUT': 'type-transfer-out',
			'TRANSFER_IN': 'type-transfer-in'
		};
		return classes[type] || '';
	}

	function applyFilters() {
		const type = document.getElementById('typeFilter').value;
		const fromDate = document.getElementById('fromDate').value;
		const toDate = document.getElementById('toDate').value;

		console.log('Applying filters:', { type, fromDate, toDate });
		loadTransactions(type, fromDate, toDate);
	}

	function resetFilters() {
		document.getElementById('filterForm').reset();
		console.log('Filters reset');
		loadTransactions();
		loadSummary();
	}
</script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
</body>
</html>

