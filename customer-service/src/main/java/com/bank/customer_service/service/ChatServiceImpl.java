package com.bank.customer_service.service;

import com.bank.customer_service.entity.ChatMessage;
import com.bank.customer_service.entity.Customer;
import com.bank.customer_service.exception.InvalidInputException;
import com.bank.customer_service.repository.ChatMessageRepository;
import com.bank.customer_service.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service implementation for Chat operations
 * Validates all inputs and throws custom exceptions for invalid data
 */
@Service
public class ChatServiceImpl implements ChatService {

	private final ChatMessageRepository chatMessageRepository;
	private final CustomerRepository customerRepository;

	public ChatServiceImpl(ChatMessageRepository chatMessageRepository,
	                        CustomerRepository customerRepository) {
		this.chatMessageRepository = chatMessageRepository;
		this.customerRepository = customerRepository;
	}

	@Override
	@Transactional
	public ChatMessage saveMessage(ChatMessage message) {
		// Validate message
		if (message == null) {
			throw new InvalidInputException("message", "cannot be null");
		}
		if (message.getMessage() == null || message.getMessage().trim().isEmpty()) {
			throw new InvalidInputException("message content", "cannot be empty");
		}
		if (message.getRoomId() == null || message.getRoomId().trim().isEmpty()) {
			throw new InvalidInputException("roomId", "cannot be empty");
		}
		if (message.getSenderId() == null) {
			throw new InvalidInputException("senderId", "cannot be null");
		}
		if (message.getSenderType() == null || message.getSenderType().trim().isEmpty()) {
			throw new InvalidInputException("senderType", "cannot be empty");
		}

		return chatMessageRepository.save(message);
	}

	@Override
	public List<ChatMessage> getMessagesByRoom(String roomId) {
		// Validate roomId
		if (roomId == null || roomId.trim().isEmpty()) {
			throw new InvalidInputException("roomId", "cannot be empty");
		}

		return chatMessageRepository.findByRoomIdOrderByTimestampAsc(roomId);
	}

	@Override
	public List<ChatMessage> getUnreadMessages(String roomId) {
		// Validate roomId
		if (roomId == null || roomId.trim().isEmpty()) {
			throw new InvalidInputException("roomId", "cannot be empty");
		}

		return chatMessageRepository.findByRoomIdAndReadStatusFalseOrderByTimestampAsc(roomId);
	}

	@Override
	@Transactional
	public void markMessagesAsRead(String roomId) {
		// Validate roomId
		if (roomId == null || roomId.trim().isEmpty()) {
			throw new InvalidInputException("roomId", "cannot be empty");
		}

		List<ChatMessage> unreadMessages = getUnreadMessages(roomId);
		for (ChatMessage message : unreadMessages) {
			message.setReadStatus(true);
			chatMessageRepository.save(message);
		}
	}

	@Override
	public long getUnreadCount(String roomId) {
		return chatMessageRepository.countByRoomIdAndReadStatusFalse(roomId);
	}

	@Override
	public List<Map<String, Object>> getActiveChatRooms() {
		List<String> roomIds = chatMessageRepository.findDistinctRoomIds();

		return roomIds.stream()
				.map(roomId -> {
					Map<String, Object> roomInfo = new HashMap<>();

					// Extract customer ID from roomId (format: room-{customerId})
					String customerIdStr = roomId.replace("room-", "");
					try {
						Long customerId = Long.parseLong(customerIdStr);

						// Get customer details
						Optional<Customer> customerOpt = customerRepository.findById(customerId);
						if (customerOpt.isPresent()) {
							Customer customer = customerOpt.get();
							roomInfo.put("roomId", roomId);
							roomInfo.put("customerId", customerId);
							roomInfo.put("customerName", customer.getFirstName() + " " + customer.getLastName());
							roomInfo.put("customerEmail", customer.getEmail());

							// Get last message
							List<ChatMessage> messages = getMessagesByRoom(roomId);
							if (!messages.isEmpty()) {
								ChatMessage lastMessage = messages.get(messages.size() - 1);
								roomInfo.put("lastMessage", lastMessage.getMessage());
								roomInfo.put("lastMessageTime", lastMessage.getTimestamp());
							}

							// Get unread count
							roomInfo.put("unreadCount", getUnreadCount(roomId));

							return roomInfo;
						}
					} catch (NumberFormatException e) {
						// Invalid customer ID in room name
					}

					return null;
				})
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
	}
}

