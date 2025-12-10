<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>MyFin Bank - Chat Room</title>
	<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
	<style>
		body {
			background: #f0f9ff;
			font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
			height: 100vh;
			margin: 0;
			display: flex;
			flex-direction: column;
		}
		.navbar-custom {
			background: linear-gradient(135deg, #991b1b 0%, #7f1d1d 100%);
		}
		.navbar-custom .navbar-brand, .navbar-custom .nav-link, .navbar-custom .navbar-text {
			color: #f9fafb !important;
		}
		.chat-container {
			flex: 1;
			display: flex;
			flex-direction: column;
			max-width: 900px;
			margin: 20px auto;
			width: 100%;
			background: white;
			border-radius: 12px;
			box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
			overflow: hidden;
		}
		.chat-header {
			background: linear-gradient(135deg, #991b1b 0%, #7f1d1d 100%);
			color: white;
			padding: 15px 20px;
			display: flex;
			justify-content: space-between;
			align-items: center;
		}
		.chat-header h3 {
			margin: 0;
			font-size: 18px;
		}
		.status-indicator {
			display: flex;
			align-items: center;
			font-size: 14px;
		}
		.status-dot {
			width: 10px;
			height: 10px;
			border-radius: 50%;
			background: #10b981;
			margin-right: 5px;
			animation: pulse 2s infinite;
		}
		@keyframes pulse {
			0%, 100% { opacity: 1; }
			50% { opacity: 0.5; }
		}
		.chat-messages {
			flex: 1;
			overflow-y: auto;
			padding: 20px;
			background: #f9fafb;
		}
		.message {
			margin-bottom: 15px;
			display: flex;
			flex-direction: column;
		}
		.message.sent {
			align-items: flex-end;
		}
		.message.received {
			align-items: flex-start;
		}
		.message-bubble {
			max-width: 70%;
			padding: 10px 15px;
			border-radius: 12px;
			word-wrap: break-word;
		}
		.message.sent .message-bubble {
			background: linear-gradient(135deg, #991b1b 0%, #7f1d1d 100%);
			color: white;
		}
		.message.received .message-bubble {
			background: white;
			color: #1f2937;
			box-shadow: 0 2px 5px rgba(0, 0, 0, 0.05);
		}
		.message-info {
			font-size: 11px;
			color: #6b7280;
			margin-top: 3px;
		}
		.message.system {
			align-items: center;
		}
		.message.system .message-bubble {
			background: #fef3c7;
			color: #92400e;
			font-size: 13px;
			font-style: italic;
		}
		.chat-input-container {
			padding: 15px;
			background: white;
			border-top: 1px solid #e5e7eb;
			display: flex;
			gap: 10px;
		}
		.chat-input {
			flex: 1;
			padding: 10px 15px;
			border: 2px solid #e5e7eb;
			border-radius: 20px;
			outline: none;
		}
		.chat-input:focus {
			border-color: #991b1b;
		}
		.send-button {
			padding: 10px 25px;
			background: linear-gradient(135deg, #991b1b 0%, #7f1d1d 100%);
			color: white;
			border: none;
			border-radius: 20px;
			cursor: pointer;
			font-weight: 600;
		}
		.send-button:hover {
			opacity: 0.9;
		}
		.send-button:disabled {
			background: #d1d5db;
			cursor: not-allowed;
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
			<a href="${pageContext.request.contextPath}/admin/chat" class="btn btn-outline-light btn-sm me-2">Back to Chats</a>
			<a href="${pageContext.request.contextPath}/admin/dashboard" class="btn btn-outline-light btn-sm">Dashboard</a>
		</div>
	</div>
</nav>

<div class="chat-container">
	<div class="chat-header">
		<h3>💬 Chat with Customer #${customerId}</h3>
		<div class="status-indicator">
			<span class="status-dot" id="statusDot"></span>
			<span id="statusText">Connecting...</span>
		</div>
	</div>

	<div class="chat-messages" id="chatMessages"></div>

	<div class="chat-input-container">
		<input type="text" id="messageInput" class="chat-input" placeholder="Type your message..."
		       onkeypress="if(event.keyCode==13) sendMessage()" disabled />
		<button onclick="sendMessage()" class="send-button" id="sendButton" disabled>Send</button>
	</div>
</div>

<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/stompjs@2.3.3/lib/stomp.min.js"></script>
<script>
	const adminId = '${admin.adminId}';
	const adminName = '${admin.adminName}';
	const roomId = '${roomId}';
	const customerId = '${customerId}';

	let stompClient = null;
	let connected = false;

	function connect() {
		const socket = new SockJS('http://localhost:8081/ws-chat');
		stompClient = Stomp.over(socket);

		stompClient.connect({}, function(frame) {
			console.log('Connected: ' + frame);
			connected = true;
			updateStatus(true);

			// Subscribe to room messages
			stompClient.subscribe('/topic/room/' + roomId, function(message) {
				showMessage(JSON.parse(message.body));
			});

			// Send join message
			sendJoinMessage();

			// Load chat history
			loadChatHistory();

			// Mark messages as read
			markAsRead();
		}, function(error) {
			console.error('Connection error:', error);
			updateStatus(false);
			setTimeout(connect, 5000);
		});
	}

	function sendJoinMessage() {
		if (stompClient && connected) {
			const joinMessage = {
				senderId: adminId,
				senderType: 'ADMIN',
				senderName: adminName,
				roomId: roomId,
				message: adminName + ' (Admin) joined the chat',
				messageType: 'JOIN'
			};
			stompClient.send('/app/chat.join/' + roomId, {}, JSON.stringify(joinMessage));
		}
	}

	function sendMessage() {
		const input = document.getElementById('messageInput');
		const message = input.value.trim();

		if (message && stompClient && connected) {
			const chatMessage = {
				senderId: adminId,
				senderType: 'ADMIN',
				senderName: adminName,
				roomId: roomId,
				message: message,
				messageType: 'TEXT'
			};

			stompClient.send('/app/chat.send/' + roomId, {}, JSON.stringify(chatMessage));
			input.value = '';
		}
	}

	function showMessage(message) {
		console.log('Showing message:', message);
		const messagesDiv = document.getElementById('chatMessages');
		const messageDiv = document.createElement('div');

		if (message.messageType === 'JOIN' || message.messageType === 'LEAVE' || message.messageType === 'SYSTEM') {
			messageDiv.className = 'message system';
			messageDiv.innerHTML = '<div class="message-bubble">' + message.message + '</div>';
		} else {
			const isOwn = String(message.senderId) === String(adminId) && message.senderType === 'ADMIN';
			messageDiv.className = 'message ' + (isOwn ? 'sent' : 'received');

			const timestamp = new Date(message.timestamp).toLocaleTimeString('en-IN', {
				hour: '2-digit',
				minute: '2-digit'
			});

			messageDiv.innerHTML =
				'<div class="message-bubble">' + message.message + '</div>' +
				'<div class="message-info">' +
				(isOwn ? 'You' : message.senderName) + ' • ' + timestamp +
				'</div>';
		}

		messagesDiv.appendChild(messageDiv);
		messagesDiv.scrollTop = messagesDiv.scrollHeight;
		console.log('Message displayed successfully');
	}

	function loadChatHistory() {
		fetch('http://localhost:8081/api/chat/history/' + roomId)
			.then(response => response.json())
			.then(messages => {
				messages.forEach(message => showMessage(message));
			})
			.catch(error => console.error('Error loading history:', error));
	}

	function markAsRead() {
		fetch('http://localhost:8081/api/chat/read/' + roomId, {
			method: 'POST'
		}).catch(error => console.error('Error marking as read:', error));
	}

	function updateStatus(isConnected) {
		const statusDot = document.getElementById('statusDot');
		const statusText = document.getElementById('statusText');
		const messageInput = document.getElementById('messageInput');
		const sendButton = document.getElementById('sendButton');

		if (isConnected) {
			statusDot.style.background = '#10b981';
			statusText.textContent = 'Connected';
			messageInput.disabled = false;
			sendButton.disabled = false;
		} else {
			statusDot.style.background = '#ef4444';
			statusText.textContent = 'Disconnected';
			messageInput.disabled = true;
			sendButton.disabled = true;
		}
	}

	connect();

	window.addEventListener('beforeunload', function() {
		if (stompClient && connected) {
			const leaveMessage = {
				senderId: adminId,
				senderType: 'ADMIN',
				senderName: adminName,
				roomId: roomId,
				message: adminName + ' (Admin) left the chat',
				messageType: 'LEAVE'
			};
			stompClient.send('/app/chat.leave/' + roomId, {}, JSON.stringify(leaveMessage));
			stompClient.disconnect();
		}
	});
</script>
</body>
</html>

