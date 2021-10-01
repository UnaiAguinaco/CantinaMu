package net.javaguides.springboot.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import net.javaguides.springboot.model.Desk;
import net.javaguides.springboot.model.Reservation;
import net.javaguides.springboot.model.Room;
import net.javaguides.springboot.service.building.BuildingService;
import net.javaguides.springboot.service.desk.DeskService;
import net.javaguides.springboot.service.reservation.ReservationService;
import net.javaguides.springboot.service.room.RoomService;

@Controller
public class RoomController {

	private RoomService roomService;
	private BuildingService buildingService;
	private DeskService deskService;
	private ReservationService reservationService;

	public RoomController(RoomService roomService, BuildingService buildingService, DeskService deskService,
			ReservationService reservationService) {
		super();
		this.roomService = roomService;
		this.buildingService = buildingService;
		this.deskService = deskService;
		this.reservationService = reservationService;
	}

	
	/** 
	 * Get the buildings from the database to create a room
	 * @param model
	 * @return String
	 */
	@GetMapping("/admin/create_room")
	public String newRoom(Model model) {
		List<net.javaguides.springboot.model.Building> buildings = buildingService.getAllBuildings();
		model.addAttribute("buildingList", buildings);
		model.addAttribute("newRoom", new Room());
		return "/admin/create_room";
	}

	
	/** 
	 * Show the currect rooms list
	 * @param model
	 * @return String
	 */
	@GetMapping("/admin/room_list")
	public String roomList(Model model) {
		List<net.javaguides.springboot.model.Room> rooms = roomService.getAllRooms();
		model.addAttribute("roomList", rooms);
		return "/admin/room_list";
	}

	
	/** 
	 * Create a new room
	 * @param model
	 * @param room
	 * @return String
	 */
	@PostMapping("/admin/room/new")
	public String newRoomAttr(Model model, @ModelAttribute("newRoom") Room room) {
		roomService.saveRoom(room);
		return "redirect:/admin/room_map/" + room.getIdRoom();// meter id
	}

	
	/** 
	 * Save the room into the database and the program
	 * @param model
	 * @param "id"
	 * @return String
	 */
	@PostMapping("/admin/saveDesks/{id}")
	public String saveRoom(Model model, @PathVariable(value = "id") int id,
			@ModelAttribute("deskList") DeskListContainer newRoomDesks) {
		List<Desk> desks = newRoomDesks.getDesks();
		List<Desk> delete = deskService.getAllDesk();
		for (Desk delet : delete) {
			if (delet.getIdRoom().getIdRoom() == id) {
				deskService.deleteDeskById(delet.getIdDesk());
			}
		}
		for (Desk desk : desks) {
			desk.setIdRoom(roomService.getRoomById(id));
			deskService.saveDesk(desk);
		}
		return "redirect:/admin/main";
	}

	
	/** 
	 * Show the map of the room display
	 * @param model
	 * @param id
	 * @return String
	 */
	@GetMapping("/admin/room_map/{id}")
	public String roomMap(Model model, @PathVariable(value = "id") int id) {
		Room room = roomService.getRoomById(id);
		List<Desk> newRoomDesks = new ArrayList<Desk>();
		DeskListContainer deskList = new DeskListContainer();
		int deskNumber = 0;
		for (int i = 0; i < room.getRowCount(); i++) {
			for (int j = 0; j < room.getColumnCount(); j++) {
				Desk newDesk = new Desk();
				newDesk.setIdRoom(roomService.getRoomById(room.getIdRoom()));
				newDesk.setDeskNumber(deskNumber);
				newDesk.setRow(i);
				newDesk.setColumn(j);
				newDesk.setType(0);// not selected (green)
				newRoomDesks.add(newDesk);
				deskNumber++;
			}
		}
		deskList.setDesks(newRoomDesks);
		model.addAttribute("newRoom", room);
		model.addAttribute("deskList", deskList);
		return "/admin/room_map";
	}

	
	/** 
	 * Update the information of a room 
	 * @param id
	 * @param model
	 * @return String
	 */
	@GetMapping("/admin/update_room/{id}")
	public String update_room(@PathVariable(value = "id") int id, Model model) {
		Room room = roomService.getRoomById(id);
		List<net.javaguides.springboot.model.Building> buildings = buildingService.getAllBuildings();
		model.addAttribute("buildingList", buildings);
		model.addAttribute("newRoom", room);
		return "/admin/create_room";
	}

	
	/** 
	 * Delete a room from the database and the program
	 * @param id
	 * @param model
	 * @return String
	 */
	@GetMapping("/admin/deleteRoom/{id}")
	public String deleteRoom(@PathVariable(value = "id") int id, Model model) {
		roomService.deleteRoomById(id);
		return "redirect:/admin/room_list";
	}

	
	/** 
	 * Make a reservation and save it in the database and program
	 * @param model
	 * @param id
	 * @return String
	 */
	@GetMapping("/user/reservation/{id}")
	public String makeReservation(Model model, @PathVariable(value = "id") int id) {

		try {
			List<Desk> allDesks = deskService.getAllDesk();
			List<Desk> selectedDesks = new ArrayList<Desk>();
			Reservation reservation = reservationService.getReservationByIdal(id);
			List<Reservation> allReservations = reservationService.getAllReservations();
			List<Reservation> filteredReservations = new ArrayList<Reservation>();

			for (Reservation r : allReservations) {

				if (r.getIdRoom().getIdBuilding().getIdBuilding() == reservation.getIdBuilding().getIdBuilding()
						&& r.getIdRoom().getIdRoom() == reservation.getIdRoom().getIdRoom()
						&& r.getSquedule().toString().substring(0, r.getSquedule().toString().length() - 2)
								.equals(reservation.getSquedule().toString().substring(0, r.getSquedule().toString().length() - 2))
						&& r.getReservationId() != id) {
					filteredReservations.add(r);
				}
			}

			for (Desk desk : allDesks) {
				if (desk.getIdRoom().getIdBuilding().getIdBuilding() == reservation.getIdBuilding().getIdBuilding()
						&& desk.getIdRoom().getIdRoom() == reservation.getIdRoom().getIdRoom()) {
					Desk cDesk = new Desk();
					cDesk.setIdDesk(desk.getIdDesk());
					cDesk.setType(desk.getType());
					selectedDesks.add(cDesk);
				}
			}
			for (Desk desk : selectedDesks) {
				for (Reservation r : filteredReservations) {
					try {
						if (desk.getIdDesk() == r.getDeskId().getIdDesk()) {
							desk.setType(2);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}

				}

			}
			DeskListContainer deskList = new DeskListContainer();
			deskList.setDesks(selectedDesks);
			Room room = roomService.getRoomById(reservation.getIdRoom().getIdRoom());
			reservationService.deleteReservationByIdal(reservation.getReservationId());
			Reservation newReservation = reservation;
			newReservation.setDeskId(new Desk());
			model.addAttribute("newRoom", room);
			model.addAttribute("newResDesks", deskList);
			model.addAttribute("reservation", newReservation);

			return "/user/reservation";
		} catch (Exception e) {

			return "redirect:/user/main";
		}

	}


	public class DeskListContainer {
		private List<Desk> desks;

		public List<Desk> getDesks() {
			return desks;
		}

		public void setDesks(List<Desk> desks) {
			this.desks = desks;
		}
	}
}
