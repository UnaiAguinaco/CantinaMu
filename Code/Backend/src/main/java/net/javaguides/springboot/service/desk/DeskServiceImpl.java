package net.javaguides.springboot.service.desk;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.javaguides.springboot.model.Desk;
import net.javaguides.springboot.repository.DeskRepository;

@Service
public class DeskServiceImpl implements DeskService {

	@Autowired
	private DeskRepository deskRepository;

	public DeskServiceImpl(DeskRepository deskRepository) {
		super();
		this.deskRepository = deskRepository;
	}

	
	/** 
	 * @return List<Desk>
	 */
	@Override
	public List<Desk> getAllDesk() {
		return deskRepository.findAll();
	}

	
	/** 
	 * @param desk
	 */
	@Override
	public void saveDesk(Desk desk) {
		this.deskRepository.save(desk);
	}

	
	/** 
	 * @param id
	 */
	@Override
	public void deleteDeskById(int id) {
		this.deskRepository.deleteById(id);
	}

	
	/** 
	 * @param id
	 * @return Desk
	 */
	@Override
	public Desk getDeskById(int id) {
		return this.deskRepository.getOne(id);
	}

}
