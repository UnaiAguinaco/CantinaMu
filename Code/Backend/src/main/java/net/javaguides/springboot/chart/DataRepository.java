package net.javaguides.springboot.chart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import net.javaguides.springboot.model.Reservation;
import java.util.List;

@Repository
public interface DataRepository extends JpaRepository<Reservation, Integer>{
    
    @Query(value = "SELECT count(*) as num FROM covid19_infections WHERE day(squedule) = day(date_sub(CURDATE(),INTERVAL ?1 DAY)) AND month(squedule) = month(date_sub(CURDATE(),INTERVAL ?1 DAY)) AND year(squedule) = year(date_sub(CURDATE(),INTERVAL ?1 DAY))", nativeQuery = true)
    Integer findNumInfections(int i);

    @Query(value = "SELECT COUNT(*) as week1 FROM covid19_infections WHERE squedule between date_sub(now(),INTERVAL ?1 WEEK) and NOW();", nativeQuery = true)
    Integer findInfectionsPerWeek(int i);

    @Query(value = "SELECT COUNT(*) FROM reservation WHERE day(squedule) = day(date_sub(CURDATE(),INTERVAL ?1 DAY)) AND month(squedule) = month(date_sub(CURDATE(),INTERVAL ?1 DAY)) AND year(squedule) = year(date_sub(CURDATE(),INTERVAL ?1 DAY));", nativeQuery = true)
    Integer findNumReservations(int i);

    @Query(value = "SELECT id_room FROM reservation GROUP BY id_room;", nativeQuery = true)
    List<Integer> findRooms();

    @Query(value = "SELECT COUNT(*) FROM reservation WHERE id_room LIKE ?1 AND day(squedule) = day(date_sub(CURDATE(),INTERVAL ?2 DAY)) AND month(squedule) = month(date_sub(CURDATE(),INTERVAL ?2 DAY)) AND year(squedule) = year(date_sub(CURDATE(),INTERVAL ?2 DAY)) GROUP BY id_room;", nativeQuery = true)
    Integer findNumReservationsPerRoom(int numRoom, int i);
}
