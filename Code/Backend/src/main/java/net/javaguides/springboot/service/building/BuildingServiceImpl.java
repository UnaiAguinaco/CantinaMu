package net.javaguides.springboot.service.building;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.javaguides.springboot.controller.dto.BuildingRegistrationDto;

import net.javaguides.springboot.model.Building;
import net.javaguides.springboot.repository.BuildingRepository;

@Service
public class BuildingServiceImpl implements BuildingService {

	@Autowired
	private BuildingRepository buildingRepository;

	public BuildingServiceImpl(BuildingRepository buildingRepository) {
		super();
		this.buildingRepository = buildingRepository;
	}

	
	/** 
	 * @return List<Building>
	 */
	@Override
	public List<Building> getAllBuildings() {
		return buildingRepository.findAll();
	}

	
	/** 
	 * @param building
	 */
	@Override
	public void saveBuilding(Building building) {
		this.buildingRepository.save(building);
	}

	
	/** 
	 * @param registrationDto
	 * @return Building
	 */
	@Override
	public Building save(BuildingRegistrationDto registrationDto) {
		Building building = new Building(registrationDto.getId(), registrationDto.getName(), registrationDto.getAddress(),
				null);

		return buildingRepository.save(building);
	}

	
	/** 
	 * @param id
	 */
	@Override
	public void deleteBuildingById(int id) {
		this.buildingRepository.deleteById(id);
	}

	
	/** 
	 * @param id
	 * @return Building
	 */
	@Override
	public Building getBuildingById(int id) {
		return this.buildingRepository.getOne(id);
	}

}
