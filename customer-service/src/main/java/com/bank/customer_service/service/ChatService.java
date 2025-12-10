package com.bank.customer_service.service;

import com.bank.customer_service.entity.ChatMessage;

import java.util.List;
import java.util.Map;

/**
 * Service interface for Chat operations
 */
public interface ChatService {

	/**
	 * Save a chat message
	 */
	ChatMessage saveMessage(ChatMessage message);

	/**
	 * Get all messages in a room
	 */
	List<ChatMessage> getMessagesByRoom(String roomId);

	/**
	 * Get unread messages in a room
	 */
	List<ChatMessage> getUnreadMessages(String roomId);

	/**
	 * Mark messages as read in a room
	 */
	void markMessagesAsRead(String roomId);

	/**
	 * Get unread count for a room
	 */
	long getUnreadCount(String roomId);

	/**
	 * Get all active chat rooms with unread counts (for admin dashboard)
	 */
	List<Map<String, Object>> getActiveChatRooms();
}

