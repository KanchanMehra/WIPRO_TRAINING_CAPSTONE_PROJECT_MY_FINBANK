package com.bank.admin_service.controller;

import com.bank.admin_service.entity.Admin;
import com.bank.admin_service.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminRegistrationController {

	private final AdminService adminService;

	public AdminRegistrationController(AdminService adminService) {
		this.adminService = adminService;
	}

	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("admin", new Admin());
		return "admin/register";
	}

	@PostMapping("/register")
	public String handleRegistration(@ModelAttribute("admin") Admin admin, Model model) {
		Admin savedAdmin = adminService.registerAdmin(admin);
		model.addAttribute("admin", savedAdmin);
		return "admin/register-success";
	}
}

