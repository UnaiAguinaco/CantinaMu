package net.javaguides.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.javaguides.springboot.model.RoomRecord;

@Repository
public interface RoomRecordRepository extends JpaRepository<RoomRecord, Integer> {
}
