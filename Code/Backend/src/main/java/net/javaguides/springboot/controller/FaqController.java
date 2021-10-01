package net.javaguides.springboot.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import net.javaguides.springboot.model.Faq;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.service.EmailService;
import net.javaguides.springboot.service.building.BuildingService;
import net.javaguides.springboot.service.covid19_infections.Covid19InfectionsService;
import net.javaguides.springboot.service.desk.DeskService;
import net.javaguides.springboot.service.faq.FaqService;
import net.javaguides.springboot.service.notification.NotificationService;
import net.javaguides.springboot.service.reservation.ReservationService;
import net.javaguides.springboot.service.room.RoomService;
import net.javaguides.springboot.service.roomRecord.RoomRecordService;
import net.javaguides.springboot.service.user.UserService;

@Controller
public class FaqController {

	private FaqService faqService;
	private UserService userService;
	private EmailService emailService;
	private BuildingService buildingService;
	private RoomService roomService;
	private DeskService deskService;
	private Covid19InfectionsService covid19InfectionsService;
	private NotificationService notificationService;
	private BCryptPasswordEncoder passwordEncoder;
	private ReservationService reservationService;
	private RoomRecordService roomRecordService;

	public FaqController(FaqService faqService, UserService userService, EmailService emailService,
			BuildingService buildingService, RoomService roomService, Covid19InfectionsService covid19InfectionsService,
			DeskService deskService, NotificationService notificationService, ReservationService reservationService,
			BCryptPasswordEncoder passwordEncoder, RoomRecordService roomRecordService) {
		super();
		this.faqService = faqService;
		this.passwordEncoder = passwordEncoder;
		this.emailService = emailService;
		this.userService = userService;
		this.buildingService = buildingService;
		this.roomService = roomService;
		this.deskService = deskService;
		this.covid19InfectionsService = covid19InfectionsService;
		this.notificationService = notificationService;
		this.reservationService = reservationService;
		this.roomRecordService = roomRecordService;

	}

	/**
	 * Redirect to the faq page
	 * 
	 * @param model
	 * @return String
	 */
	@GetMapping("/faq")
	public String faqAdmin(Model model) {
		UserController userController = new UserController(userService, buildingService, roomService,
				covid19InfectionsService, notificationService, deskService, reservationService, passwordEncoder,
				roomRecordService);
		userController.main_user(model);
		List<net.javaguides.springboot.model.Faq> faqs = faqService.getAllFaqs();

		model.addAttribute("faqList", faqs);
		model.addAttribute("newFaq", new Faq());
		return "/faq";
	}

	/**
	 * Delete a faq from the database and the program
	 * 
	 * @param idal
	 * @param model
	 * @return String
	 */
	@GetMapping("/faq/delete/{id}")
	public String faqDelete(@PathVariable("id") int idal, Model model) {
		faqService.deleteFaqByIdal(idal);
		model.addAttribute("newFaq", new Faq());
		return "redirect:/faq";
	}

	/**
	 * Determine faq attributes depending the user ROLE
	 * 
	 * @param faq
	 * @return String
	 */
	@PostMapping("/faq/saveFaq")
	public String registerUserAccount(@ModelAttribute("newFaq") Faq faq) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService
				.getUserByUsername((auth.getPrincipal().toString().split(";")[0].split(" ")[2].split("@")[0]));
		faq.setIdal(user.getIdal());
		if (auth.getPrincipal().toString().contains("ROLE_ADMIN")) {
			faq.setFaq(true);
		} else {
			faq.setFaq(false);
		}
		if (faq.getDefinition() != "" && auth.getPrincipal().toString().contains("ROLE_USER")) {
			faqService.saveFaq(faq);
			emailService.sendMail(faq.getDefinition(), user.getEmail());
		} else if ((!(faq.getDefinition().equals("")) && !(faq.getAnswer().equals("")))
				&& auth.getPrincipal().toString().contains("ROLE_ADMIN")) {

			faqService.saveFaq(faq);
		}

		return "redirect:/faq";
	}
}
