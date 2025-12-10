package com.bank.customer_service.dto;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for ChatMessage
 * Used for transferring chat message data via REST APIs
 */
public class ChatMessageDTO {

	private Long id;
	private String senderId;
	private String senderType;
	private String senderName;
	private String roomId;
	private String message;
	private String messageType;
	private LocalDateTime timestamp;
	private Boolean readStatus;

	// Constructors
	public ChatMessageDTO() {
	}

	public ChatMessageDTO(Long id, String senderId, String senderType, String senderName, String roomId,
	                      String message, String messageType, LocalDateTime timestamp, Boolean readStatus) {
		this.id = id;
		this.senderId = senderId;
		this.senderType = senderType;
		this.senderName = senderName;
		this.roomId = roomId;
		this.message = message;
		this.messageType = messageType;
		this.timestamp = timestamp;
		this.readStatus = readStatus;
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

