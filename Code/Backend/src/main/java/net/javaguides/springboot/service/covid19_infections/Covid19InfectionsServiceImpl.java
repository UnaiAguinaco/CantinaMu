package net.javaguides.springboot.service.covid19_infections;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.javaguides.springboot.model.covid19_infections;
import net.javaguides.springboot.repository.Covid19InfectionsRepository;

@Service
public class Covid19InfectionsServiceImpl implements Covid19InfectionsService {

	@Autowired
	private Covid19InfectionsRepository covid19InfectionsRepository;

	public Covid19InfectionsServiceImpl(Covid19InfectionsRepository covid19InfectionsRepository) {
		super();
		this.covid19InfectionsRepository = covid19InfectionsRepository;
	}

	
	/** 
	 * @return List<covid19_infections>
	 */
	@Override
	public List<covid19_infections> getAllInfections() {

		return covid19InfectionsRepository.findAll();
	}

	
	/** 
	 * @param covid19Infections
	 */
	@Override
	public void saveInfections(covid19_infections covid19Infections) {
		this.covid19InfectionsRepository.save(covid19Infections);

	}

	
	/** 
	 * @param idal
	 * @return covid19_infections
	 */
	@Override
	public covid19_infections getInfectionsByIdal(int idal) {

		return covid19InfectionsRepository.getOne(idal);
	}

	
	/** 
	 * @param idal
	 * @return covid19_infections
	 */
	@Override
	public covid19_infections getInfectionsByUser(int idal) {
		List<covid19_infections> list = getAllInfections();
		for (covid19_infections covid : list) {

			if (covid.getUserId().getIdal()==idal) {
				System.out.println("ouyea");
				return covid;
			}
		}
		return null;
	}

	
	
	/** 
	 * @param idal
	 */
	@Override
	public void deleteInfectionsByIdal(int idal) {
		this.covid19InfectionsRepository.deleteById(idal);

	}

}
