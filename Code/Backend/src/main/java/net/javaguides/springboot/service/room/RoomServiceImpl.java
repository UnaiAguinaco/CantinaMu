package net.javaguides.springboot.service.room;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.javaguides.springboot.model.Room;
import net.javaguides.springboot.repository.RoomRepository;

@Service
public class RoomServiceImpl implements RoomService {

	@Autowired
	private RoomRepository roomRepository;

	public RoomServiceImpl(RoomRepository roomRepository) {
		super();
		this.roomRepository = roomRepository;
	}

	
	/** 
	 * @return List<Room>
	 */
	@Override
	public List<Room> getAllRooms() {
		return roomRepository.findAll();
	}

	
	/** 
	 * @param room
	 */
	@Override
	public void saveRoom(Room room) {
		this.roomRepository.save(room);
	}

	
	/** 
	 * @param id
	 */
	@Override
	public void deleteRoomById(int id) {
		this.roomRepository.deleteById(id);
	}

	
	/** 
	 * @param id
	 * @return Room
	 */
	@Override
	public Room getRoomById(int id) {
		return this.roomRepository.getOne(id);
	}

}
