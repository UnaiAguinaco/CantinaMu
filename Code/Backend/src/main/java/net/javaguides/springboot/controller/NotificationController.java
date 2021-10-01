package net.javaguides.springboot.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import net.javaguides.springboot.model.Notification;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.service.notification.NotificationService;
import net.javaguides.springboot.service.user.UserService;

@Controller
public class NotificationController {

	private NotificationService NotificationService;
	private UserService userService;

	public NotificationController(NotificationService NotificationService, UserService userService) {
		super();
		this.NotificationService = NotificationService;

		this.userService = userService;
	}

	
	/** 
	 * Show notifications from database
	 * @param model
	 * @return String
	 */
	@GetMapping("/notification")
	public String NotificationAdmin(Model model) {
		List<net.javaguides.springboot.model.Notification> notifications = NotificationService.getAllNotifications();

		model.addAttribute("notificationList", notifications);
		model.addAttribute("newNotification", new Notification());
		System.out.println("demo");
		return "/Notification";
	}

	
	/** 
	 * Delete notifications from database
	 * @param id
	 * @param model
	 * @return String
	 */
	@GetMapping("/notification/delete/{id}")
	public String deleteBuilding(@PathVariable(value = "id") int id, Model model) {
		this.NotificationService.deleteNotificationByIdal(id);
		return "redirect:/user/main";
	}

	
	/** 
	 * Save a notification with the user 
	 * @param notification
	 * @return String
	 */
	@PostMapping("/notification/saveNotification")
	public String registerUserAccount(@ModelAttribute("newNotification") Notification notification) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService
				.getUserByUsername((auth.getPrincipal().toString().split(";")[0].split(" ")[2].split("@")[0]));
		notification.setUserId(user.getIdal());
		NotificationService.saveNotification(notification);

		return "redirect:/Notification";
	}
}
