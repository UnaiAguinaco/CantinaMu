package net.javaguides.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import net.javaguides.springboot.Monitor;
import net.javaguides.springboot.model.Building;
import net.javaguides.springboot.service.building.BuildingService;

@Controller
public class BuildingController {
    private BuildingService buildingService;

    public BuildingController(BuildingService buildingService) {
        super();

        this.buildingService = buildingService;
    }

    
    /** 
     * Show the formulary to edit a building
     * @param id
     * @param model
     * @return String
     */
    @GetMapping("/admin/showFormForUpdate/{id}")
    public synchronized String showFormForUpdate(@PathVariable(value = "id") int id, Model model) {
        Monitor.setId(id);
        System.out.println(Monitor.getId()+"mendipetas");
        while (Monitor.getWritter() == true || Monitor.getReaderInside() > 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        Monitor.setWritter(true);
        Building building = buildingService.getBuildingById(id);

        model.addAttribute("building", building);
        return "/admin/update_building";
    }

    
    /** 
     * Redirect to building edition page
     * @return String
     */
    @GetMapping("/admin/update_building")
    public String update_building() {
        return "/admin/update_building";
    }

    
    /** 
     * Save the building entry in the database
     * @param building
     * @return String
     */
    @PostMapping("/saveBuilding")
    public synchronized String saveBuilding(@ModelAttribute("building") Building building) {
        Monitor.setWritter(false);
        notifyAll();
        buildingService.saveBuilding(building);

        return "redirect:/admin/building_list";
    }

    
    /** 
     * Delete a building by the id from the database
     * @param id
     * @param model
     * @return String
     */
    @GetMapping("/admin/deleteBuilding/{id}")
    public String deleteBuilding(@PathVariable(value = "id") int id, Model model) {
        this.buildingService.deleteBuildingById(id);
        return "redirect:/admin/building_list";
    }
}
