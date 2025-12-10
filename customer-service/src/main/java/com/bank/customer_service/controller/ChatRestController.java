package com.bank.customer_service.controller;

import com.bank.customer_service.dto.ChatMessageDTO;
import com.bank.customer_service.dto.EntityDtoMapper;
import com.bank.customer_service.entity.ChatMessage;
import com.bank.customer_service.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST Controller for chat-related operations
 * Returns DTOs instead of entities for better security and decoupling
 */
@RestController
@RequestMapping("/api/chat")
public class ChatRestController {

	private final ChatService chatService;

	public ChatRestController(ChatService chatService) {
		this.chatService = chatService;
	}

	/**
	 * Get chat history for a room
	 * Returns ChatMessageDTO to decouple entity from API
	 */
	@GetMapping("/history/{roomId}")
	public ResponseEntity<List<ChatMessageDTO>> getChatHistory(@PathVariable String roomId) {
		List<ChatMessage> messages = chatService.getMessagesByRoom(roomId);

		// Convert to DTOs
		List<ChatMessageDTO> messageDTOs = EntityDtoMapper.toChatMessageDTOList(messages);
		return ResponseEntity.ok(messageDTOs);
	}

	/**
	 * Mark messages as read in a room
	 */
	@PostMapping("/read/{roomId}")
	public ResponseEntity<Void> markAsRead(@PathVariable String roomId) {
		chatService.markMessagesAsRead(roomId);
		return ResponseEntity.ok().build();
	}

	/**
	 * Get unread count for a room
	 */
	@GetMapping("/unread/{roomId}")
	public ResponseEntity<Map<String, Long>> getUnreadCount(@PathVariable String roomId) {
		long count = chatService.getUnreadCount(roomId);
		return ResponseEntity.ok(Map.of("unreadCount", count));
	}

	/**
	 * Get all active chat rooms (for admin)
	 */
	@GetMapping("/rooms")
	public ResponseEntity<List<Map<String, Object>>> getActiveChatRooms() {
		List<Map<String, Object>> rooms = chatService.getActiveChatRooms();
		return ResponseEntity.ok(rooms);
	}
}

