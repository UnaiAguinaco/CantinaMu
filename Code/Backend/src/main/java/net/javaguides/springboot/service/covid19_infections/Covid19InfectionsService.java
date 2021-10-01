package net.javaguides.springboot.service.covid19_infections;

import java.util.List;

import net.javaguides.springboot.model.covid19_infections;

public interface Covid19InfectionsService {
	List<covid19_infections> getAllInfections();

	void saveInfections(covid19_infections covidInfection);

	covid19_infections getInfectionsByIdal(int idal);
	covid19_infections getInfectionsByUser(int idal);
	void deleteInfectionsByIdal(int idal);

}
