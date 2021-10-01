package net.javaguides.springboot.chart;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.javaguides.springboot.model.Reservation;

@Service
public class DataServiceImpl implements DataService {

    List<Integer> numInfections = new ArrayList<Integer>();
    List<Integer> numReservations = new ArrayList<Integer>();
    List<Integer> infectionsPerWeek = new ArrayList<Integer>();
    List<List<Integer>> reservationPerBuilding = new ArrayList<List<Integer>>();
    List<Reservation> prueba = new ArrayList<Reservation>();
    List<Integer> rooms = new ArrayList<Integer>();
    List<List<Integer>> reservationPerRoom = new ArrayList<List<Integer>>();

    @Autowired
    private DataRepository dataRepository;

    public DataServiceImpl() {
    }

    
    /** 
     * Get the list of infected IDs of users
     * @return List<Integer>
     */
    @Override
    public List<Integer> getNumInfections() {
        numInfections.clear();
        for (int i = 1; i <= 5; i++) {
            numInfections.add(dataRepository.findNumInfections(i));
        }
        return numInfections;
    }

    
    /** 
     * Get the list of resrvations
     * @return List<Integer>
     */
    @Override
    public List<Integer> getNumReservations() {
        numReservations.clear();
        for (int i = 1; i <= 15; i++) {
            numReservations.add(dataRepository.findNumReservations(i));
        }
        return numReservations;
    }

    
    /** 
     * Get the list of infections per week 
     * @return List<Integer>
     */
    @Override
    public List<Integer> getInfectionsPerWeek() {
        infectionsPerWeek.clear();
        for (int i = 1; i <= 3; i++) {
            infectionsPerWeek.add(dataRepository.findInfectionsPerWeek(i));
        }
        return infectionsPerWeek;
    }

    
    /** 
     * Get the list of revervations per room
     * @return List<List<Integer>>
     */
    @Override
    public List<List<Integer>> getReservationPerRoom() {
        reservationPerRoom.clear();
        rooms = dataRepository.findRooms();
        int num;
        for (Integer id_room : rooms) {
            List<Integer> room = new ArrayList<Integer>();
            room.add(id_room);
            for (int i = 5; i > 0; i--) {
                try {
                    num = dataRepository.findNumReservationsPerRoom(id_room, i);
                } catch (NullPointerException e) {
                    num = 0;
                }
                room.add(num);
            }
            reservationPerRoom.add(room);
        }

        return reservationPerRoom;
    }
}
