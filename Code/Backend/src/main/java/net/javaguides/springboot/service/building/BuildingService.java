package net.javaguides.springboot.service.building;

import java.util.List;

import net.javaguides.springboot.controller.dto.BuildingRegistrationDto;
import net.javaguides.springboot.model.Building;

public interface BuildingService {
	List<Building> getAllBuildings();

	void saveBuilding(Building building);

	Building getBuildingById(int id);

	void deleteBuildingById(int id);

	Building save(BuildingRegistrationDto registrationDto);
}
