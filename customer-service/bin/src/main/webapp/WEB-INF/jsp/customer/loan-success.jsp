<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>MyFin Bank - Loan Application Submitted</title>
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
		.container-success {
			padding: 40px 20px;
			max-width: 800px;
			margin: 0 auto;
		}
		.success-card {
			background: white;
			border-radius: 12px;
			box-shadow: 0 5px 20px rgba(0, 0, 0, 0.08);
			padding: 40px;
			text-align: center;
		}
		.success-icon {
			font-size: 64px;
			margin-bottom: 20px;
			animation: scaleIn 0.5s ease-in;
		}
		@keyframes scaleIn {
			from {
				transform: scale(0);
				opacity: 0;
			}
			to {
				transform: scale(1);
				opacity: 1;
			}
		}
		.success-title {
			color: #10b981;
			font-size: 28px;
			font-weight: 700;
			margin-bottom: 10px;
		}
		.success-subtitle {
			color: #6b7280;
			font-size: 16px;
			margin-bottom: 30px;
		}
		.details-card {
			background: #f0fdf4;
			border-left: 4px solid #10b981;
			padding: 25px;
			border-radius: 8px;
			margin: 30px 0;
			text-align: left;
		}
		.detail-row {
			display: flex;
			justify-content: space-between;
			padding: 12px 0;
			border-bottom: 1px solid #e5e7eb;
		}
		.detail-row:last-child {
			border-bottom: none;
		}
		.detail-label {
			font-weight: 600;
			color: #1f2937;
		}
		.detail-value {
			color: #10b981;
			font-weight: 700;
		}
		.app-no-box {
			background: #dbeafe;
			padding: 15px;
			border-radius: 6px;
			margin: 15px 0;
		}
		.app-no-label {
			font-size: 12px;
			color: #1e40af;
			text-transform: uppercase;
			font-weight: 600;
		}
		.app-no {
			font-size: 18px;
			font-weight: 700;
			color: #0284c7;
			font-family: 'Courier New', monospace;
			word-break: break-all;
		}
		.copy-btn {
			background: #3b82f6;
			color: white;
			border: none;
			padding: 6px 12px;
			border-radius: 4px;
			font-size: 12px;
			cursor: pointer;
			margin-top: 5px;
		}
		.copy-btn:hover {
			background: #2563eb;
		}
		.status-badge {
			display: inline-block;
			background: #fef3c7;
			color: #92400e;
			padding: 8px 16px;
			border-radius: 20px;
			font-weight: 600;
			font-size: 14px;
			margin: 15px 0;
		}
		.info-box {
			background: #fef3c7;
			border-left: 4px solid #f59e0b;
			padding: 15px;
			border-radius: 6px;
			margin: 20px 0;
			text-align: left;
			color: #78350f;
		}
		.action-buttons {
			display: flex;
			gap: 10px;
			justify-content: center;
			margin-top: 30px;
			flex-wrap: wrap;
		}
		.btn-custom {
			padding: 12px 24px;
			border-radius: 6px;
			font-weight: 600;
			text-decoration: none;
			border: none;
			cursor: pointer;
			transition: all 0.3s;
		}
		.btn-primary-custom {
			background: #9333ea;
			color: white;
		}
		.btn-primary-custom:hover {
			background: #7e22ce;
			transform: translateY(-2px);
		}
		.btn-secondary-custom {
			background: #e5e7eb;
			color: #1f2937;
		}
		.btn-secondary-custom:hover {
			background: #d1d5db;
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

<div class="container-success">
	<div class="success-card">
		<div class="success-icon">✅</div>
		<h2 class="success-title">Loan Application Submitted Successfully!</h2>
		<p class="success-subtitle">Your loan application has been received and is under review</p>

		<div class="app-no-box">
			<div class="app-no-label">Loan Application Number</div>
			<div class="app-no">${loan.loanApplicationNo}</div>
			<button class="copy-btn" onclick="copyAppNo('${loan.loanApplicationNo}')">Copy Application No</button>
		</div>

		<div class="details-card">
			<h4 style="margin-bottom: 15px; color: #1f2937;">Loan Details</h4>

			<div class="detail-row">
				<span class="detail-label">Loan Amount</span>
				<span class="detail-value">₹${String.format("%.2f", loan.loanAmount)}</span>
			</div>
			<div class="detail-row">
				<span class="detail-label">Duration</span>
				<span class="detail-value">${loan.durationInMonths} Months</span>
			</div>
			<div class="detail-row">
				<span class="detail-label">Interest Rate</span>
				<span class="detail-value">${String.format("%.2f", loan.interestRate)}% p.a.</span>
			</div>
			<div class="detail-row">
				<span class="detail-label">Monthly EMI</span>
				<span class="detail-value">₹${String.format("%.2f", loan.emiAmount)}</span>
			</div>
			<div class="detail-row">
				<span class="detail-label">Total Amount Payable</span>
				<span class="detail-value">₹${String.format("%.2f", loan.totalAmountPayable)}</span>
			</div>
			<div class="detail-row">
				<span class="detail-label">Total Interest</span>
				<span class="detail-value">₹${String.format("%.2f", loan.totalInterest)}</span>
			</div>
			<div class="detail-row">
				<span class="detail-label">Purpose</span>
				<span class="detail-value">${loan.purpose}</span>
			</div>
			<div class="detail-row">
				<span class="detail-label">Application Date</span>
				<span class="detail-value">${loan.applicationDate}</span>
			</div>
		</div>

		<div class="status-badge">🟡 PENDING APPROVAL</div>

		<div class="info-box">
			<strong>ℹ️ Status Update:</strong> Your loan application is currently under review by our admin team.
			You will receive an email notification once your application is approved or denied.
			Kindly keep your application number handy for future reference.
		</div>

		<div class="action-buttons">
			<a href="${pageContext.request.contextPath}/customer/loan/my-loans" class="btn-custom btn-primary-custom">
				View My Loans
			</a>
			<a href="${pageContext.request.contextPath}/customer/dashboard" class="btn-custom btn-secondary-custom">
				Back to Dashboard
			</a>
			<a href="${pageContext.request.contextPath}/customer/loan/apply" class="btn-custom btn-secondary-custom">
				Apply for Another Loan
			</a>
		</div>
	</div>
</div>

<script>
	function copyAppNo(appNo) {
		navigator.clipboard.writeText(appNo).then(() => {
			alert('Application number copied to clipboard!');
		}).catch(err => {
			console.error('Failed to copy:', err);
		});
	}
</script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
</body>
</html>

