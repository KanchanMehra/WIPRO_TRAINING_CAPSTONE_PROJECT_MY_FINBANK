package com.bank.admin_service.controller;

import com.bank.admin_service.entity.Admin;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Controller for Admin Chat functionality
 */
@Controller
@RequestMapping("/admin/chat")
public class AdminChatController {

	private final RestTemplate restTemplate;
	private static final String CUSTOMER_SERVICE_URL = "http://localhost:8081";

	public AdminChatController(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	/**
	 * Show chat dashboard with list of active customer chats
	 */
	@GetMapping
	public String showChatDashboard(HttpSession session, Model model) {
		Admin admin = (Admin) session.getAttribute("loggedInAdmin");
		if (admin == null) {
			return "redirect:/admin/login";
		}

		try {
			// Get active chat rooms from customer service
			String url = CUSTOMER_SERVICE_URL + "/api/chat/rooms";
			ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
					url,
					HttpMethod.GET,
					null,
					new ParameterizedTypeReference<List<Map<String, Object>>>() {}
			);

			List<Map<String, Object>> chatRooms = response.getBody();
			model.addAttribute("chatRooms", chatRooms);
			model.addAttribute("admin", admin);
			return "admin/admin-chat-dashboard";

		} catch (Exception e) {
			model.addAttribute("error", "Error loading chat rooms: " + e.getMessage());
			model.addAttribute("chatRooms", List.of());
			model.addAttribute("admin", admin);
			return "admin/admin-chat-dashboard";
		}
	}

	/**
	 * Show specific chat room with customer
	 */
	@GetMapping("/room/{roomId}")
	public String showChatRoom(@PathVariable String roomId,
	                           HttpSession session,
	                           Model model) {
		Admin admin = (Admin) session.getAttribute("loggedInAdmin");
		if (admin == null) {
			return "redirect:/admin/login";
		}

		// Extract customer ID from roomId (format: room-{customerId})
		String customerIdStr = roomId.replace("room-", "");

		model.addAttribute("admin", admin);
		model.addAttribute("roomId", roomId);
		model.addAttribute("customerId", customerIdStr);
		return "admin/admin-chat-room";
	}
}

