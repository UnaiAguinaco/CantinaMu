package net.javaguides.springboot.controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.javaguides.springboot.controller.dto.BuildingRegistrationDto;

import net.javaguides.springboot.service.building.BuildingService;

@Controller
@RequestMapping("/admin/create_building")
public class BuildingRegistrationController {

	private BuildingService buildingService;

	public BuildingRegistrationController(BuildingService buildingService) {
		super();
		this.buildingService = buildingService;
	}

	
	/** 
	 * Create new buildingRegistrationDto
	 * @return BuildingRegistrationDto
	 */
	@ModelAttribute("building")
	public BuildingRegistrationDto buildingRegistrationDto() {

		return new BuildingRegistrationDto();
	}

	
	/** 
	 * Redirect to the create building form
	 * @return String
	 */
	@GetMapping
	public String showBuildingRegistrationForm() {
		return "admin/create_building";
	}

	
	/** 
	 * Save a building in the database and redirect to the admin page
	 * @param registrationDto
	 * @return String
	 */
	@PostMapping
	public String registerBuildingAccount(@ModelAttribute("building") BuildingRegistrationDto registrationDto) {
		buildingService.save(registrationDto);
		return "redirect:/admin/main";
	}
}
