<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>MyFin Bank - Customer Chats</title>
	<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
	<style>
		body {
			background: #f0f9ff;
			min-height: 100vh;
			font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
		}
		.navbar-custom {
			background: linear-gradient(135deg, #991b1b 0%, #7f1d1d 100%);
		}
		.navbar-custom .navbar-brand, .navbar-custom .nav-link, .navbar-custom .navbar-text {
			color: #f9fafb !important;
		}
		.container-chats {
			padding: 30px 20px;
			max-width: 1000px;
			margin: 0 auto;
		}
		.page-header {
			margin-bottom: 30px;
		}
		.page-header h2 {
			color: #1f2937;
			font-weight: 700;
		}
		.alert {
			border-radius: 8px;
			padding: 12px 15px;
			margin-bottom: 20px;
		}
		.chat-room-card {
			background: white;
			border-radius: 12px;
			box-shadow: 0 5px 20px rgba(0, 0, 0, 0.08);
			padding: 20px;
			margin-bottom: 15px;
			cursor: pointer;
			transition: transform 0.2s, box-shadow 0.2s;
			border-left: 4px solid #991b1b;
		}
		.chat-room-card:hover {
			transform: translateY(-2px);
			box-shadow: 0 8px 25px rgba(0, 0, 0, 0.12);
		}
		.room-header {
			display: flex;
			justify-content: space-between;
			align-items: start;
			margin-bottom: 10px;
		}
		.customer-name {
			font-size: 18px;
			font-weight: 700;
			color: #1f2937;
		}
		.customer-email {
			font-size: 14px;
			color: #6b7280;
		}
		.unread-badge {
			background: #ef4444;
			color: white;
			padding: 4px 10px;
			border-radius: 12px;
			font-size: 12px;
			font-weight: 600;
		}
		.last-message {
			color: #6b7280;
			font-size: 14px;
			margin-top: 8px;
			overflow: hidden;
			text-overflow: ellipsis;
			white-space: nowrap;
		}
		.time-ago {
			font-size: 12px;
			color: #9ca3af;
			margin-top: 5px;
		}
		.empty-state {
			text-align: center;
			padding: 60px 20px;
			background: white;
			border-radius: 12px;
			box-shadow: 0 5px 20px rgba(0, 0, 0, 0.08);
		}
		.empty-state-icon {
			font-size: 64px;
			color: #d1d5db;
			margin-bottom: 20px;
		}
		.back-button {
			display: inline-block;
			color: #991b1b;
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
		<a class="navbar-brand" href="${pageContext.request.contextPath}/admin/dashboard">
			🏦 MyFin Bank - Admin Panel
		</a>
		<div class="d-flex align-items-center">
			<span class="navbar-text me-3">Admin: ${admin.adminName}</span>
			<a href="${pageContext.request.contextPath}/admin/logout" class="btn btn-outline-light btn-sm">Logout</a>
		</div>
	</div>
</nav>

<div class="container-chats">
	<div class="page-header">
		<h2>💬 Customer Chats</h2>
		<p class="text-muted">View and respond to customer queries</p>
	</div>

	<c:if test="${not empty error}">
		<div class="alert alert-danger">${error}</div>
	</c:if>

	<c:choose>
		<c:when test="${empty chatRooms}">
			<div class="empty-state">
				<div class="empty-state-icon">📭</div>
				<h4>No Active Chats</h4>
				<p>No customers have initiated chat conversations yet.</p>
			</div>
		</c:when>
		<c:otherwise>
			<c:forEach var="room" items="${chatRooms}">
				<div class="chat-room-card" onclick="location.href='${pageContext.request.contextPath}/admin/chat/room/${room.roomId}'">
					<div class="room-header">
						<div>
							<div class="customer-name">${room.customerName}</div>
							<div class="customer-email">${room.customerEmail}</div>
						</div>
						<c:if test="${room.unreadCount > 0}">
							<div class="unread-badge">${room.unreadCount} unread</div>
						</c:if>
					</div>
					<c:if test="${not empty room.lastMessage}">
						<div class="last-message">${room.lastMessage}</div>
						<div class="time-ago">${room.lastMessageTime}</div>
					</c:if>
				</div>
			</c:forEach>
		</c:otherwise>
	</c:choose>

	<a href="${pageContext.request.contextPath}/admin/dashboard" class="back-button">
		← Back to Dashboard
	</a>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
</body>
</html>

