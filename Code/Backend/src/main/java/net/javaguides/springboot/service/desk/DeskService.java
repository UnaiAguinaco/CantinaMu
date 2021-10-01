package net.javaguides.springboot.service.desk;

import java.util.List;

import net.javaguides.springboot.model.Desk;

public interface DeskService {
	List<Desk> getAllDesk();

	void saveDesk(Desk room);

	Desk getDeskById(int id);

	void deleteDeskById(int id);
}
