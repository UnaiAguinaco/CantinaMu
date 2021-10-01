package net.javaguides.springboot.service.reservation;

import java.util.List;

import net.javaguides.springboot.model.Reservation;

public interface ReservationService {
	List<Reservation> getAllReservations();

	void saveReservation(Reservation reservation);

	Reservation getReservationByIdal(int idal);

	void deleteReservationByIdal(int idal);

}
