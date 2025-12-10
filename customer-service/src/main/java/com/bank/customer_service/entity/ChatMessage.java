package com.bank.customer_service.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * ChatMessage Entity
 * Stores chat messages between customers and admins
 */
@Entity
@Table(name = "CHAT_MESSAGE")
public class ChatMessage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "SENDER_ID", nullable = false, length = 50)
	private String senderId; // Can be numeric (customer) or string (admin like "ADM1001")

	@Column(name = "SENDER_TYPE", nullable = false, length = 10)
	private String senderType; // CUSTOMER or ADMIN

	@Column(name = "SENDER_NAME", nullable = false, length = 100)
	private String senderName;

	@Column(name = "ROOM_ID", nullable = false, length = 50)
	private String roomId; // Format: room-{customerId}

	@Column(name = "MESSAGE", nullable = false, columnDefinition = "TEXT")
	private String message;

	@Column(name = "MESSAGE_TYPE", nullable = false, length = 20)
	private String messageType; // TEXT, JOIN, LEAVE, SYSTEM

	@Column(name = "TIMESTAMP", nullable = false)
	private LocalDateTime timestamp;

	@Column(name = "READ_STATUS")
	private Boolean readStatus = false;

	// Constructors
	public ChatMessage() {
		this.timestamp = LocalDateTime.now();
	}

	public ChatMessage(String senderId, String senderType, String senderName, String roomId,
	                   String message, String messageType) {
		this.senderId = senderId;
		this.senderType = senderType;
		this.senderName = senderName;
		this.roomId = roomId;
		this.message = message;
		this.messageType = messageType;
		this.timestamp = LocalDateTime.now();
		this.readStatus = false;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getSenderType() {
		return senderType;
	}

	public void setSenderType(String senderType) {
		this.senderType = senderType;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public Boolean getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(Boolean readStatus) {
		this.readStatus = readStatus;
	}
}

