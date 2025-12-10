package com.bank.customer_service.controller;

import com.bank.customer_service.entity.ChatMessage;
import com.bank.customer_service.service.ChatService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * WebSocket controller for handling chat messages
 */
@Controller
public class ChatController {

	private final ChatService chatService;

	public ChatController(ChatService chatService) {
		this.chatService = chatService;
	}

	@MessageMapping("/chat.send/{roomId}")
	@SendTo("/topic/room/{roomId}")
	public ChatMessage sendMessage(@DestinationVariable String roomId, ChatMessage message) {
		message.setRoomId(roomId);
		message.setMessageType("TEXT");
		return chatService.saveMessage(message);
	}

	@MessageMapping("/chat.join/{roomId}")
	@SendTo("/topic/room/{roomId}")
	public ChatMessage joinRoom(@DestinationVariable String roomId, ChatMessage message) {
		message.setRoomId(roomId);
		message.setMessageType("JOIN");
		message.setMessage(message.getSenderName() + " joined the chat");
		return chatService.saveMessage(message);
	}

	@MessageMapping("/chat.leave/{roomId}")
	@SendTo("/topic/room/{roomId}")
	public ChatMessage leaveRoom(@DestinationVariable String roomId, ChatMessage message) {
		message.setRoomId(roomId);
		message.setMessageType("LEAVE");
		message.setMessage(message.getSenderName() + " left the chat");
		return chatService.saveMessage(message);
	}
}

