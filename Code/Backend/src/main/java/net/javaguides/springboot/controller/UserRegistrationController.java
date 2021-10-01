package net.javaguides.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.javaguides.springboot.controller.dto.UserRegistrationDto;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.service.user.UserService;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

	private UserService userService;

	public UserRegistrationController(UserService userService) {
		super();
		this.userService = userService;
	}

	/**
	 * Create a UserRegistrationDto object to create a new user
	 * 
	 * @return UserRegistrationDto
	 */
	@ModelAttribute("user")
	public UserRegistrationDto userRegistrationDto() {
		return new UserRegistrationDto();
	}

	/**
	 * Redirect to the registration page
	 * 
	 * @return String
	 */
	@GetMapping
	public String showRegistrationForm() {
		return "registration";
	}

	/**
	 * Save an user in the database and redirect to the corresponding login page
	 * 
	 * @param registrationDto
	 * @return String
	 */
	@PostMapping
	public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto) {
		boolean exist = false;
		for (User user : userService.getAllUsers()) {
			if (user.getUserName().equals(registrationDto.getEmail().split("@")[0])) {
				exist = true;
				break;
			}
		}
		if (!exist) {
			userService.save(registrationDto);
			return "redirect:/login";
		} else {
			return "redirect:/login?error";
		}
	}
}
