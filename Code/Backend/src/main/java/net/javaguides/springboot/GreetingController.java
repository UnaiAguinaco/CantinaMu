package net.javaguides.springboot;

import static java.lang.String.format;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import net.javaguides.springboot.model.Desk;
import net.javaguides.springboot.model.Reservation;
import net.javaguides.springboot.model.Room;
import net.javaguides.springboot.service.desk.DeskService;
import net.javaguides.springboot.service.reservation.ReservationService;
import net.javaguides.springboot.service.room.RoomService;

@Controller
public class GreetingController {

	@Autowired
	private SimpMessageSendingOperations messagingTemplate;
	private RoomService roomService;
	private DeskService deskService;
	private ReservationService reservationService;
	List<ChatMessage> roomList;

	public GreetingController(RoomService roomService, DeskService deskService, ReservationService reservationService) {
		super();
		this.roomService = roomService;
		this.deskService = deskService;
		this.reservationService = reservationService;
	}

	/**
	 * Send the data of the room to the new user which is connected to the room
	 * booking
	 * 
	 * @param roomId
	 * @param chatMessage
	 * @throws InterruptedException
	 */
	@MessageMapping("/enter/{roomId}")
	public void sendMessage(@DestinationVariable String roomId, @Payload ChatMessage chatMessage)
			throws InterruptedException {

		Room room = new Room();
		List<Room> allRooms = roomService.getAllRooms();
		for (Room rooms : allRooms) {
			if (rooms.getIdRoom() == Integer.parseInt(chatMessage.getRoomId().split(" ")[0])) {
				room = rooms;
				break;
			}
		}
		if (roomList == null) {
			roomList = new ArrayList<>();
		}
		boolean exist = false;
		for (ChatMessage chatMessage2 : roomList) {
			if (chatMessage2.getRoomId().equals(chatMessage.getRoomId())) {
				exist = true;
				chatMessage = chatMessage2;
				chatMessage.setUsers(chatMessage.getUsers() + 1);
				break;
			}
		}
		if (!exist) {

			int length = chatMessage.getRow() * chatMessage.getCol();
			int[] deskS = new int[length];

			List<Desk> desks = deskService.getAllDesk();
			List<Desk> filteredDesks = new ArrayList<>();
			for (Desk desk : desks) {
				if (desk.getIdRoom().getIdRoom() == room.getIdRoom()) {
					filteredDesks.add(desk);
				}
			}
			List<Reservation> reservations = reservationService.getAllReservations();
			List<Reservation> filteredreservations = new ArrayList<>();
			for (Reservation reservation : reservations) {
				if (reservation.getIdRoom().getIdRoom() == room.getIdRoom() && reservation.getTime()
						.equals(chatMessage.getRoomId().split(" ")[2] + " " + chatMessage.getRoomId().split(" ")[3])) {
					filteredreservations.add(reservation);
				}
			}
			int index = 0;
			for (Desk desk : filteredDesks) {
				for (Reservation reservation : filteredreservations) {
					if (reservation.getDeskId().getIdDesk() == desk.getIdDesk()) {
						deskS[index] = 2;
					}
				}
				if (deskS[index] != 2) {
					deskS[index] = desk.getType();
				}
				index++;
			}
			chatMessage.setDeskStatus(deskS);
			chatMessage.setUsers(1);
			roomList.add(chatMessage);
		}
		messagingTemplate.convertAndSend(format("/room/%s", roomId), chatMessage);
	}

	/**
	 * Update the data to all users connected to the room
	 * 
	 * @param roomId
	 * @param chatMessage
	 * @throws InterruptedException
	 */
	@MessageMapping("/change/{roomId}")
	public void changeDesks(@DestinationVariable String roomId, @Payload ChatMessage chatMessage)
			throws InterruptedException {
		for (ChatMessage chatMessage2 : roomList) {
			if (chatMessage2.getRoomId().equals(chatMessage.getRoomId())) {
				chatMessage2.setDeskStatus(chatMessage.getDeskStatus());
			}
		}
		messagingTemplate.convertAndSend(format("/room/%s", roomId), chatMessage);
	}

	/**
	 * Disconnect user from room
	 * 
	 * @param chatMessage
	 */
	@MessageMapping("/disconnect")
	public void disconnect(@Payload ChatMessage chatMessage) {

		for (ChatMessage chatMessage2 : roomList) {
			if (chatMessage2.getRoomId().equals(chatMessage.getRoomId())) {
				if (chatMessage2.getUsers() == 1) {
					roomList.remove(chatMessage2);
					break;
				} else {
					chatMessage2.setUsers(chatMessage2.getUsers() - 1);
				}

			}
		}
	}

}
