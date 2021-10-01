package net.javaguides.springboot.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import net.javaguides.springboot.Monitor;
import net.javaguides.springboot.MyBuffer;
import net.javaguides.springboot.model.Building;
import net.javaguides.springboot.model.ChangePassword;
import net.javaguides.springboot.model.Desk;
import net.javaguides.springboot.model.Notification;
import net.javaguides.springboot.model.Reservation;
import net.javaguides.springboot.model.Room;
import net.javaguides.springboot.model.RoomRecord;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.service.building.BuildingService;
import net.javaguides.springboot.service.covid19_infections.Covid19InfectionsService;
import net.javaguides.springboot.service.desk.DeskService;
import net.javaguides.springboot.service.notification.NotificationService;
import net.javaguides.springboot.service.reservation.ReservationService;
import net.javaguides.springboot.service.room.RoomService;
import net.javaguides.springboot.service.roomRecord.RoomRecordService;
import net.javaguides.springboot.service.user.UserService;

@Controller
public class UserController {

    @Autowired
    private UserController userController;

    private UserService userService;
    private BCryptPasswordEncoder passwordEncoder;
    private BuildingService buildingService;
    private RoomService roomService;
    private DeskService deskService;
    private ReservationService reservationService;
    private Covid19InfectionsService covid19InfectionsService;
    private NotificationService notificationService;
    private RoomRecordService roomRecordService;

    MyBuffer myBuffer;

    public UserController(UserService userService, BuildingService buildingService, RoomService roomService,
            Covid19InfectionsService covid19InfectionsService, NotificationService notificationService,
            DeskService deskService, ReservationService reservationService, BCryptPasswordEncoder passwordEncoder,
            RoomRecordService roomRecordService) {
        super();
        this.roomRecordService = roomRecordService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.buildingService = buildingService;
        this.roomService = roomService;
        this.deskService = deskService;
        this.reservationService = reservationService;
        this.covid19InfectionsService = covid19InfectionsService;
        this.notificationService = notificationService;
        myBuffer = new MyBuffer(roomRecordService);
    }

    /**
     * Redirect the user to its main with the information necessary Notifications,
     * reservations...
     * 
     * @param model
     * @return String
     */
    @GetMapping("/user/main")
    public String main_user(Model model) {

        List<net.javaguides.springboot.model.Building> buildings = buildingService.getAllBuildings();
        model.addAttribute("buildingList", buildings);
        List<net.javaguides.springboot.model.Room> room = roomService.getAllRooms();
        List<Reservation> allReservations = reservationService.getAllReservations();
        List<Reservation> filteredReservations = new ArrayList<Reservation>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService
                .getUserByUsername((auth.getPrincipal().toString().split(";")[0].split(" ")[2].split("@")[0]));

        LocalDate todaydate = LocalDate.now();

        List<ReservationCalendar> days = new ArrayList<ReservationCalendar>();
        int dayNum = todaydate.withDayOfMonth(1).minusDays(todaydate.withDayOfMonth(1).getDayOfWeek().getValue())
                .plusDays(1).getDayOfMonth();
        for (Reservation reservation : allReservations) {
            if (reservation.getUserId().getIdal() == user.getIdal()
                    && reservation.getSquedule().getMonth() == (todaydate.getMonthValue() - 1)) {
                reservation.setTime(reservation.getTime().split(" ")[1]);
                filteredReservations.add(reservation);
            }
        }
        for (int i = dayNum; i < todaydate.minusMonths(1).lengthOfMonth() + 1; i++) {
            ReservationCalendar reserve = new ReservationCalendar();
            reserve.setDay(dayNum);
            days.add(reserve);
            dayNum++;
        }
        dayNum = 1;
        for (int i = 0; i < todaydate.lengthOfMonth(); i++) {
            ReservationCalendar reserve = new ReservationCalendar();
            for (Reservation reservation : filteredReservations) {
                if (reservation.getSquedule().getDate() == dayNum) {
                    reserve.setReservation(reservation);
                }
            }
            reserve.setDay(dayNum);
            days.add(reserve);
            dayNum++;
        }
        if (days.size() < 35) {
            dayNum = 1;
            for (int i = days.size(); i < 35; i++) {
                ReservationCalendar reserve = new ReservationCalendar();
                reserve.setDay(dayNum);
                days.add(reserve);
                dayNum++;
            }
        }

        model.addAttribute("reservationList", days);
        model.addAttribute("roomList", room);
        Reservation r = new Reservation();
        r.setIdRoom(new Room());
        r.setIdBuilding(new Building());
        model.addAttribute("reservation", r);

        /* Read notifications */

        List<Notification> notifications = notificationService.getAllNotifications();
        List<Notification> fNotifications = new ArrayList<>();
        Authentication aut = SecurityContextHolder.getContext().getAuthentication();
        User usr = userService
                .getUserByUsername((aut.getPrincipal().toString().split(";")[0].split(" ")[2].split("@")[0]));
        model.addAttribute("userData", usr);
        for (Notification noti : notifications) {
            if (noti.getUserId() == usr.getIdal()) {
                fNotifications.add(noti);
            }
        }
        model.addAttribute("notificationList", fNotifications);
        if (fNotifications != null) {
            model.addAttribute("notificationCount", fNotifications.size());
        }
        model.addAttribute("notificationCount", 0);

        return "/user/main";
    }

    /**
     * Delete a user's reservation
     * 
     * @param idal
     * @param model
     * @return String
     */
    @PostMapping("/user/deleteReservation/{id}")
    public String deleteReservation(@PathVariable("id") int idal, Model model) {
        Reservation reservation = reservationService.getReservationByIdal(idal);
        RoomRecord roomRecord = new RoomRecord(reservation.getIdRoom(), reservation.getDeskId(),
                reservation.getIdBuilding(), reservation.getUserId(), reservation.getSquedule(), reservation.getTime(),
                "DELETE");
        myBuffer.setInfo(roomRecord);
        myBuffer.run();
        reservationService.deleteReservationByIdal(idal);
        return "redirect:/user/main";
    }

    /**
     * Edit an user's reservation
     * 
     * @param idal
     * @param model
     * @return String
     */
    @PostMapping("/user/editReservation/{id}")
    public String editReservation(@PathVariable("id") int idal, Model model) {

        Reservation reservation = new Reservation();
        reservation.setIdBuilding(reservationService.getReservationByIdal(idal).getIdBuilding());
        reservation.setIdRoom(reservationService.getReservationByIdal(idal).getIdRoom());
        reservation.setSquedule(reservationService.getReservationByIdal(idal).getSquedule());
        reservation.setTime(reservationService.getReservationByIdal(idal).getTime());
        reservation.setUserId(reservationService.getReservationByIdal(idal).getUserId());
        reservationService.saveReservation(reservation);
        RoomRecord roomRecord = new RoomRecord(reservation.getIdRoom(), reservation.getDeskId(),
                reservation.getIdBuilding(), reservation.getUserId(), reservation.getSquedule(), reservation.getTime(),
                "EDIT");
        myBuffer.setInfo(roomRecord);
        myBuffer.run();

        int id = -1;
        for (Reservation r : reservationService.getAllReservations()) {
            if (r.getSquedule().equals(reservation.getSquedule()) && r.getUserId() == reservation.getUserId()) {
                id = r.reservationId;
            }
        }

        reservationService.deleteReservationByIdal(idal);
        if (id != -1) {
            return "redirect:/user/reservation/" + id;
        }
        return null;
    }

    /**
     * Save a reservation in the database
     * 
     * @param model
     * @param reservation
     * @return String
     */
    @PostMapping("/user/saveReservation")
    public String saveReservation(Model model, @ModelAttribute("reservation") Reservation reservation) {
        Monitor.decrementReaders();
        // if(Monitor.getReaderInside()==0)notifyAll();
        try {
            reservation.setSquedule(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(reservation.getTime()));
            reservation.setIdRoomStr(String.valueOf(reservation.getIdRoom().getIdRoom()));
        } catch (ParseException e) {

        }
        RoomRecord roomRecord = new RoomRecord(reservation.getIdRoom(), reservation.getDeskId(),
                reservation.getIdBuilding(), reservation.getUserId(), reservation.getSquedule(), reservation.getTime(),
                "SAVE");
        myBuffer.setInfo(roomRecord);
        myBuffer.run();

        reservationService.saveReservation(reservation);
        return "redirect:/user/main";
    }

    /**
     * Show the room list to the user when reservating
     * 
     * @param model
     * @param id
     * @return String
     */
    @GetMapping("/user/chargeRooms/{id}")
    public String chargeRooms(Model model, @PathVariable(value = "id") int id) {

        List<Room> allRooms = roomService.getAllRooms();
        List<Room> filteredRooms = new ArrayList<Room>();

        for (Room room : allRooms) {
            if (room.getIdBuilding().getIdBuilding() == id) {
                filteredRooms.add(room);
            }
        }
        model.addAttribute("roomList", filteredRooms);
        return "/user/main :: roomList";
    }

    /**
     * Show the remaining desks of a room
     * 
     * @param model
     * @param freeDesks
     * @return String
     */
    @GetMapping("/user/remainingDesks/{freeDesks}")
    public String remainingDesks(Model model, @PathVariable(value = "freeDesks") String freeDesks) {
        String[] values = freeDesks.split("-");
        int desks = 0;
        for (Reservation r : reservationService.getAllReservations()) {
            try {
                if (r.getSquedule().toString().substring(0, r.getSquedule().toString().length() - 2)
                        .equals((values[2] + "-" + values[3] + "-" + values[4] + " " + values[5]))
                        && r.getIdBuilding().getIdBuilding() == Integer.parseInt(values[0])
                        && r.getIdRoom().getIdRoom() == Integer.parseInt(values[1])) {
                    desks++;
                }
            } catch (NumberFormatException e) {
            }
        }
        List<Desk> allDesks = deskService.getAllDesk();
        List<Desk> roomDesks = new ArrayList<Desk>();
        for (Desk desk : allDesks) {
            if (desk.getIdRoom().getIdBuilding().getIdBuilding() == Integer.parseInt(values[0])
                    && desk.getIdRoom().getIdRoom() == Integer.parseInt(values[1]) && desk.getType() != -1) {
                roomDesks.add(desk);
            }
        }
        ResourceBundle rb = ResourceBundle.getBundle("messages", Locale.getDefault());
        freeDesks = rb.getString("room.display") + " " + values[1] + " " + rb.getString("building.display") + " "
                + values[0] + rb.getString("middle.display") + " " + (roomDesks.size() - desks) + " "
                + rb.getString("desk.display");
        model.addAttribute("freeDesks", freeDesks);
        return "/user/main :: freeDesks";

    }

    /**
     * Charge the rooms of a building and asign the user to the reservation
     * 
     * @param model
     * @param reservation
     * @return String
     */
    @PostMapping("/user/newReserve")
    public synchronized String chargeRooms(Model model, @ModelAttribute("reservation") Reservation reservation) {
        while (Monitor.getWritter() == true&&Monitor.getId()==reservation.getIdBuilding().getIdBuilding()) {
            try {
                wait();
            } catch (InterruptedException e) {

            }
        }
        Monitor.incrementReaders();
        try {
            reservation.setSquedule(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(reservation.getTime()));
        } catch (ParseException e) {

        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService
                .getUserByUsername((auth.getPrincipal().toString().split(";")[0].split(" ")[2].split("@")[0]));
        reservation.setUserId(user);
        reservationService.saveReservation(reservation);
        int id = -1;
        for (Reservation r : reservationService.getAllReservations()) {
            if (r.getSquedule().equals(reservation.getSquedule()) && r.getUserId() == reservation.getUserId()) {
                id = r.reservationId;
            }
        }
        if (id != -1) {
            return "redirect:/user/reservation/" + id;
        }
        return null;
    }

    /**
     * Show the reservation by displaying it
     * 
     * @param model
     * @param id
     * @return String
     */
    @GetMapping("/user/show_reservation/{id}")
    public String showReservation(Model model, @PathVariable(value = "id") int id) {

        Reservation reservation = reservationService.getReservationByIdal(id);
        List<Reservation> listReservation = reservationService.getAllReservations();
        List<Reservation> filteredListReservation = new ArrayList<>();
        for (Reservation reservation2 : listReservation) {
            if (reservation.getIdBuilding().getIdBuilding() == reservation2.getIdBuilding().getIdBuilding()
                    && reservation.getIdRoom().getIdRoom() == reservation2.getIdRoom().getIdRoom()
                    && reservation.getTime().equals(reservation2.getTime())) {
                filteredListReservation.add(reservation2);
            }
        }
        List<Desk> deskList = deskService.getAllDesk();
        List<Desk> filteredDeskList = new ArrayList<>();
        for (Desk desk : deskList) {
            if (desk.getIdRoom().getIdBuilding().getIdBuilding() == reservation.getIdBuilding().getIdBuilding()
                    && desk.getIdRoom().getIdRoom() == reservation.getIdRoom().getIdRoom()) {
                Desk cDesk = new Desk();
                cDesk.setIdDesk(desk.getIdDesk());
                cDesk.setType(desk.getType());

                if (desk.getIdDesk() == reservation.getDeskId().getIdDesk()) {
                    cDesk.setType(3);
                }
                filteredDeskList.add(cDesk);
            }
        }

        for (Desk desk : filteredDeskList) {
            for (Reservation res : filteredListReservation) {
                if (res.getDeskId().getIdDesk() == desk.getIdDesk() && desk.getType() != 3) {
                    desk.setType(2);
                }
            }
        }
        model.addAttribute("newRoom", reservation.getIdRoom());
        model.addAttribute("desks", filteredDeskList);
        return "/user/show_reservation";

    }

    /**
     * Redirect to the user's settings
     * 
     * @param model
     * @return String
     */
    @GetMapping("/userSettings")
    public String user_settings(Model model) {
        this.main_user(model);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService
                .getUserByUsername((auth.getPrincipal().toString().split(";")[0].split(" ")[2].split("@")[0]));
        model.addAttribute("userData", user);
        ChangePassword changePass = new ChangePassword();
        model.addAttribute("changePass", changePass);
        return "/userSettings";
    }

    /**
     * Change the password of the user
     * 
     * @param changePass
     * @return String
     */
    @PostMapping("/userSettings/changePassword")
    public String changePassword(@ModelAttribute("changePass") ChangePassword changePass) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService
                .getUserByUsername((auth.getPrincipal().toString().split(";")[0].split(" ")[2].split("@")[0]));
        if (passwordEncoder.matches(changePass.getOldPassword(), user.getPassword())
                && (changePass.getNewPassword().equals(changePass.getRepeatPassword()))
                && (changePass.getNewPassword().equals("") && changePass.getRepeatPassword().equals(""))) {
            user.setPassword(passwordEncoder.encode(changePass.getNewPassword()));
            userService.saveUser(user);
        }
        return "redirect:/userSettings";
    }

    public class ReservationCalendar {
        int day;
        Reservation reservation;

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public Reservation getReservation() {
            return reservation;
        }

        public void setReservation(Reservation reservation) {
            this.reservation = reservation;
        }
    }
}
