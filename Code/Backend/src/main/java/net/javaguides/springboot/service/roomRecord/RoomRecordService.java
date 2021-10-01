package net.javaguides.springboot.service.roomRecord;

import java.util.List;

import net.javaguides.springboot.model.RoomRecord;

public interface RoomRecordService {
	List<RoomRecord> getAllRoomRecords();

	void saveRoomRecord(RoomRecord roomRecord);

	RoomRecord getRoomRecordByIdal(int idal);

	void deleteRoomRecordByIdal(int idal);

}
