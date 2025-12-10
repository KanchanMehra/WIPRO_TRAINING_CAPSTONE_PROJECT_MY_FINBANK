package com.bank.customer_service.repository;

import com.bank.customer_service.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for ChatMessage operations
 */
@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

	/**
	 * Find all messages in a room, ordered by timestamp
	 */
	List<ChatMessage> findByRoomIdOrderByTimestampAsc(String roomId);

	/**
	 * Find unread messages for a specific room
	 */
	List<ChatMessage> findByRoomIdAndReadStatusFalseOrderByTimestampAsc(String roomId);

	/**
	 * Count unread messages in a room
	 */
	long countByRoomIdAndReadStatusFalse(String roomId);

	/**
	 * Get distinct room IDs (for admin dashboard)
	 */
	@Query("SELECT DISTINCT c.roomId FROM ChatMessage c")
	List<String> findDistinctRoomIds();
}

