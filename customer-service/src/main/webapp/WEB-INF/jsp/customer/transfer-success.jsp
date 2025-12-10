<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>MyFin Bank - Transfer Successful</title>
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
		.container-success {
			padding: 30px 20px;
			max-width: 650px;
			margin: 0 auto;
		}
		.success-card {
			border-radius: 12px;
			box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
			border: none;
			overflow: hidden;
		}
		.success-header {
			background: linear-gradient(135deg, #10b981, #059669);
			color: white;
			padding: 40px 20px;
			text-align: center;
		}
		.success-icon {
			font-size: 64px;
			margin-bottom: 15px;
			animation: slideDown 0.6s ease-out;
		}
		@keyframes slideDown {
			from {
				opacity: 0;
				transform: translateY(-30px);
			}
			to {
				opacity: 1;
				transform: translateY(0);
			}
		}
		.success-message {
			font-size: 24px;
			font-weight: 700;
			margin-bottom: 10px;
		}
		.success-subtitle {
			font-size: 14px;
			opacity: 0.9;
		}
		.transaction-details {
			padding: 30px;
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
			font-size: 13px;
			color: #6b7280;
			text-transform: uppercase;
			font-weight: 600;
		}
		.detail-value {
			font-size: 14px;
			color: #1f2937;
			font-weight: 500;
		}
		.transaction-id {
			font-family: 'Courier New', monospace;
			font-weight: 700;
			color: #0891b2;
			font-size: 14px;
		}
		.amount-transferred {
			font-size: 28px;
			font-weight: 700;
			color: #3b82f6;
		}
		.new-balance {
			font-size: 18px;
			font-weight: 700;
			color: #10b981;
		}
		.balance-zero-alert {
			background: #fee2e2;
			border-left: 4px solid #ef4444;
			padding: 15px;
			border-radius: 6px;
			margin-top: 20px;
		}
		.balance-zero-alert-title {
			color: #b91c1c;
			font-weight: 600;
			margin-bottom: 5px;
		}
		.recipient-section {
			background: #f0f9ff;
			border-left: 4px solid #3b82f6;
			padding: 15px;
			border-radius: 6px;
			margin-bottom: 20px;
		}
		.recipient-name {
			font-size: 16px;
			font-weight: 600;
			color: #1f2937;
		}
		.recipient-account {
			font-size: 13px;
			color: #6b7280;
			margin-top: 5px;
		}
		.action-buttons {
			padding: 20px 30px;
			display: flex;
			gap: 10px;
		}
		.btn-action {
			flex: 1;
			padding: 12px;
			border-radius: 6px;
			font-weight: 600;
			border: none;
			cursor: pointer;
			transition: all 0.3s ease;
			text-decoration: none;
			display: inline-block;
			text-align: center;
		}
		.btn-dashboard {
			background: linear-gradient(135deg, #0891b2, #06b6d4);
			color: white;
		}
		.btn-dashboard:hover {
			transform: translateY(-2px);
			box-shadow: 0 10px 20px rgba(8, 145, 178, 0.3);
		}
		.btn-transfer-again {
			background: #f0f9ff;
			color: #0891b2;
			border: 2px solid #0891b2;
		}
		.btn-transfer-again:hover {
			background: #e0f2fe;
		}
		.receipt-info {
			background: #fef3c7;
			border-left: 4px solid #f59e0b;
			padding: 12px 15px;
			border-radius: 6px;
			margin-top: 20px;
			font-size: 13px;
			color: #78350f;
		}
		.copy-button {
			background: none;
			border: none;
			color: #0891b2;
			cursor: pointer;
			font-weight: 600;
			padding: 0;
			margin-left: 10px;
		}
		.copy-button:hover {
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

<div class="container-success">
	<div class="success-card">
		<!-- Success Header -->
		<div class="success-header">
			<div class="success-icon">✅</div>
			<div class="success-message">Transfer Successful!</div>
			<div class="success-subtitle">Money has been transferred successfully</div>
		</div>

		<!-- Transaction Details -->
		<div class="transaction-details">
			<h5 style="color: #1f2937; font-weight: 700; margin-bottom: 20px;">📋 Transfer Details</h5>

			<!-- Recipient Information -->
			<div class="recipient-section">
				<div class="recipient-name">${result.receiver.firstName} ${result.receiver.lastName}</div>
				<div class="recipient-account">Account: ${result.receiver.accountNo}</div>
			</div>

			<!-- Transfer Amount -->
			<div class="detail-row">
				<span class="detail-label">Amount Transferred</span>
				<span class="detail-value">
					<span class="amount-transferred">₹${result.amount}</span>
				</span>
			</div>

			<!-- Your Transaction ID -->
			<div class="detail-row">
				<span class="detail-label">Your Transaction ID</span>
				<span class="detail-value">
					<span class="transaction-id">${result.senderTransactionId}</span>
					<button class="copy-button" onclick="copyToClipboard('${result.senderTransactionId}')">Copy</button>
				</span>
			</div>

			<!-- Recipient Transaction ID -->
			<div class="detail-row">
				<span class="detail-label">Recipient Transaction ID</span>
				<span class="detail-value">
					<span class="transaction-id">${result.receiverTransactionId}</span>
					<button class="copy-button" onclick="copyToClipboard('${result.receiverTransactionId}')">Copy</button>
				</span>
			</div>

			<!-- Your New Balance -->
			<div class="detail-row">
				<span class="detail-label">Your New Balance</span>
				<span class="detail-value">
					<span class="new-balance">₹${customer.amount}</span>
				</span>
			</div>


			<!-- Date & Time -->
			<div class="detail-row">
				<span class="detail-label">Date & Time</span>
				<span class="detail-value">${result.timestamp}</span>
			</div>

			<!-- Status -->
			<div class="detail-row">
				<span class="detail-label">Status</span>
				<span class="detail-value">
					<span style="background: #d1fae5; color: #065f46; padding: 4px 12px; border-radius: 12px; font-weight: 600;">
						✓ SUCCESS
					</span>
				</span>
			</div>

			<!-- Zero Balance Alert (if applicable) -->
			<c:if test="${customer.amount == 0}">
				<div class="balance-zero-alert">
					<div class="balance-zero-alert-title">⚠️ Zero Balance Alert</div>
					<p style="margin-bottom: 0; font-size: 13px;">
						Your account balance is now zero. The bank administrator has been notified.
					</p>
				</div>
			</c:if>

			<!-- Receipt Info -->
			<div class="receipt-info">
				💡 Keep both Transaction IDs for your records. They will help you track this transfer in your transaction history.
			</div>
		</div>

		<!-- Action Buttons -->
		<div class="action-buttons">
			<a href="${pageContext.request.contextPath}/customer/dashboard" class="btn-action btn-dashboard">
				← Back to Dashboard
			</a>
			<a href="${pageContext.request.contextPath}/customer/transfer" class="btn-action btn-transfer-again">
				🔄 Transfer Again
			</a>
		</div>
	</div>

	<!-- Additional Info -->
	<div class="card" style="margin-top: 20px;">
		<div class="card-body p-4" style="background: linear-gradient(135deg, #f3f4f6 0%, #e5e7eb 100%); border-radius: 12px;">
			<h6 style="color: #1f2937; font-weight: 600; margin-bottom: 10px;">✨ What's Next?</h6>
			<ul style="font-size: 13px; color: #4b5563; margin-bottom: 0; padding-left: 20px;">
				<li>Your new balance is ₹${customer.amount}</li>
				<li>You can make deposits to increase your balance</li>
				<li>Transfer more money to other accounts</li>
				<li>Check your complete transaction history</li>
				<li>View both deposit and transfer transactions</li>
			</ul>
		</div>
	</div>
</div>

<script>
	function copyToClipboard(text) {
		navigator.clipboard.writeText(text).then(() => {
			alert('Transaction ID copied to clipboard!');
		}).catch(err => {
			console.error('Failed to copy:', err);
		});
	}
</script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
</body>
</html>

