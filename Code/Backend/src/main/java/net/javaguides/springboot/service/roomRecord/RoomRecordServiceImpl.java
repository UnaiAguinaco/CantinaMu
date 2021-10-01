package net.javaguides.springboot.service.roomRecord;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.javaguides.springboot.model.RoomRecord;
import net.javaguides.springboot.repository.RoomRecordRepository;

@Service
public class RoomRecordServiceImpl implements RoomRecordService {

	@Autowired
	private RoomRecordRepository roomRecordRepository;

	public RoomRecordServiceImpl(RoomRecordRepository roomRecordRepository) {
		super();
		this.roomRecordRepository = roomRecordRepository;
	}

	
	/** 
	 * @return List<RoomRecord>
	 */
	@Override
	public List<RoomRecord> getAllRoomRecords() {
		return roomRecordRepository.findAll();
	}

	
	/** 
	 * @param roomRecord
	 */
	@Override
	public void saveRoomRecord(RoomRecord roomRecord) {
		this.roomRecordRepository.save(roomRecord);

	}

	
	/** 
	 * @param idal
	 * @return RoomRecord
	 */
	@Override
	public RoomRecord getRoomRecordByIdal(int idal) {
		return roomRecordRepository.getOne(idal);
	}

	
	/** 
	 * @param idal
	 */
	@Override
	public void deleteRoomRecordByIdal(int idal) {
		this.roomRecordRepository.deleteById(idal);

	}

}
