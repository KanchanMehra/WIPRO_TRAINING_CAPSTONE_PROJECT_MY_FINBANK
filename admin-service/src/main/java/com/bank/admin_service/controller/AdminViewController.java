package com.bank.admin_service.controller;

import com.bank.admin_service.entity.Admin;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * Controller for displaying admin pages (JSP views)
 */
@Controller
@RequestMapping("/admin")
public class AdminViewController {

	/**
	 * Show admin customers list page
	 */
	@GetMapping("/customers")
	public String showCustomers(HttpSession session, Model model) {
		Admin admin = (Admin) session.getAttribute("loggedInAdmin");
		if (admin == null) {
			return "redirect:/admin/login";
		}

		model.addAttribute("admin", admin);
		return "admin/customers";
	}

	/**
	 * Show pending customer approvals page
	 */
	@GetMapping("/pending-approvals")
	public String showPendingApprovals(HttpSession session, Model model) {
		Admin admin = (Admin) session.getAttribute("loggedInAdmin");
		if (admin == null) {
			return "redirect:/admin/login";
		}

		model.addAttribute("admin", admin);
		return "admin/pending-approvals";
	}

	/**
	 * Show loan approvals page
	 */
	@GetMapping("/loan-approvals")
	public String showLoanApprovals(HttpSession session, Model model) {
		Admin admin = (Admin) session.getAttribute("loggedInAdmin");
		if (admin == null) {
			return "redirect:/admin/login";
		}

		model.addAttribute("admin", admin);
		return "admin/loan-approval";
	}

	/**
	 * Show admin chat page
	 */
	@GetMapping("/chats")
	public String showChats(HttpSession session, Model model) {
		Admin admin = (Admin) session.getAttribute("loggedInAdmin");
		if (admin == null) {
			return "redirect:/admin/login";
		}

		model.addAttribute("admin", admin);
		return "admin/customer-chats";
	}
}

