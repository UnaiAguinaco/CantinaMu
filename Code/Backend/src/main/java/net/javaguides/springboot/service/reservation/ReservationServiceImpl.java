package net.javaguides.springboot.service.reservation;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.javaguides.springboot.model.Reservation;
import net.javaguides.springboot.repository.ReservationRepository;

@Service
public class ReservationServiceImpl implements ReservationService {

	@Autowired
	private ReservationRepository reservationRepository;

	public ReservationServiceImpl(ReservationRepository reservationRepository) {
		super();
		this.reservationRepository = reservationRepository;
	}

	
	/** 
	 * @return List<Reservation>
	 */
	@Override
	public List<Reservation> getAllReservations() {

		return reservationRepository.findAll();
	}

	
	/** 
	 * @param reservation
	 */
	@Override
	public void saveReservation(Reservation reservation) {
		this.reservationRepository.save(reservation);

	}

	
	/** 
	 * @param idal
	 * @return Reservation
	 */
	@Override
	public Reservation getReservationByIdal(int idal) {

		return reservationRepository.getOne(idal);
	}




	
	/** 
	 * @param idal
	 */
	@Override
	public void deleteReservationByIdal(int idal) {
		this.reservationRepository.deleteById(idal);

	}

}
