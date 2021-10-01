package net.javaguides.springboot.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	
	/** 
	 * Redirect to login page
	 * @return String
	 */
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	
	/** 
	 * Redirect the user depending the role 
	 * @param request
	 * @return String
	 */
	@GetMapping("/")
	public String returnToMain(HttpServletRequest request) {
		if (request.isUserInRole("ROLE_ADMIN")) {
			return "redirect:/admin/main/";
		}
		return "redirect:/user/main/";
	}

	
	/** 
	 * Default redirection after login
	 * @param request
	 * @return String
	 */
	@GetMapping("/default")
	public String defaultAfterLogin(HttpServletRequest request) {
		if (request.isUserInRole("ROLE_ADMIN")) {
			return "redirect:/admin/main/";
		}
		return "redirect:/user/main/";
	}
}
