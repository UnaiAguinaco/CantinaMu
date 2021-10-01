package net.javaguides.springboot.service.room;

import java.util.List;

import net.javaguides.springboot.model.Room;

public interface RoomService {
	List<Room> getAllRooms();

	void saveRoom(Room room);

	Room getRoomById(int id);

	void deleteRoomById(int id);
}
