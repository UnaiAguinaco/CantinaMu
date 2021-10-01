package net.javaguides.springboot.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import net.javaguides.springboot.service.user.UserService;
import net.javaguides.springboot.model.Desk;
import net.javaguides.springboot.model.Notification;
import net.javaguides.springboot.model.Reservation;
import net.javaguides.springboot.model.Room;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.model.covid19_infections;
import net.javaguides.springboot.service.building.BuildingService;
import net.javaguides.springboot.service.covid19_infections.Covid19InfectionsService;
import net.javaguides.springboot.service.desk.DeskService;
import net.javaguides.springboot.service.notification.NotificationService;
import net.javaguides.springboot.service.reservation.ReservationService;
import net.javaguides.springboot.service.room.RoomService;

@Controller
public class AdminController {
    private UserService userService;
    private BuildingService buildingService;
    private Covid19InfectionsService covid19InfectionsService;
    private NotificationService notificationService;
    private ReservationService reservationService;
    private RoomService roomService;
    private DeskService deskService;

    public AdminController(UserService userService, BuildingService buildingService,
            Covid19InfectionsService covid19InfectionsService, NotificationService notificationService,
            ReservationService reservationService, RoomService roomService, DeskService deskService) {
        super();
        this.userService = userService;
        this.buildingService = buildingService;
        this.covid19InfectionsService = covid19InfectionsService;
        this.reservationService = reservationService;
        this.notificationService = notificationService;
        this.roomService = roomService;
        this.deskService = deskService;
    }

    /**
     * Redirect to the administrator's main page
     * 
     * @return String
     */
    @GetMapping("/admin/main")
    public String main_admin() {
        return "/admin/main";
    }

    /**
     * Get and redirect to the building list
     * 
     * @param model
     * @return String
     */
    @GetMapping("/admin/building_list")
    public String edit_building(Model model) {
        List<net.javaguides.springboot.model.Building> buildings = buildingService.getAllBuildings();
        model.addAttribute("buildingList", buildings);
        return "/admin/building_list";
    }

    /**
     * Display left navigation
     * 
     * @return String
     */
    @GetMapping("/fragments/left_nav")
    public String left_nav() {
        return "/fragments/left_nav";
    }

    /**
     * Display header
     * 
     * @return String
     */
    @GetMapping("/fragments/header")
    public String header() {
        return "/fragments/header";
    }

    /**
     * Update the Covid status of an user
     * 
     * @param idal
     * @param model
     * @return String
     */
    @PostMapping("/admin/updateStatus/{id}")
    public String update(@PathVariable("id") int idal, Model model) {
        userService.updateCovidStatus(idal);
        if (covid19InfectionsService.getInfectionsByUser(idal) != null) {
            covid19_infections covid = covid19InfectionsService.getInfectionsByUser(idal);
            covid19InfectionsService.deleteInfectionsByIdal(covid.getcovid19Infection_id());
        } else {
            long now = System.currentTimeMillis();
            Date sqlDate = new Date(now);
            covid19_infections covid = new covid19_infections();
            covid.setUserId(userService.getUserByIdal(idal));

            covid.setSquedule(sqlDate);
            covid19InfectionsService.saveInfections(covid);
            /* Get all reservations from the user in last 7 days */
            List<Reservation> reservations = reservationService.getAllReservations();
            List<Reservation> userReservations = new ArrayList<Reservation>();
            LocalDate date = LocalDate.now().minusDays(7);
            Date dauite = new Date(date.getYear() - 1900, date.getMonthValue() - 1, date.getDayOfMonth());
            for (Reservation r : reservations) {
                if (r.getUserId().getIdal() == userService.getUserByIdal(idal).getIdal()) {
                    if (r.getSquedule().after(dauite))
                        userReservations.add(r);
                }
            }
            List<Reservation> restUserReservations = new ArrayList<Reservation>();
            for (User user : userService.getAllUsers()) {
                restUserReservations.clear();
                for (Reservation r : reservationService.getAllReservations()) {
                    if (r.getUserId().getIdal() == user.getIdal()) {
                        if (r.getSquedule().after(dauite))
                            restUserReservations.add(r);
                    }
                }
                for (Reservation re : userReservations) {
                    for (Reservation res : restUserReservations) {
                        if (re.getIdBuilding().getIdBuilding() == res.getIdBuilding().getIdBuilding()
                                && re.getIdRoom().getIdRoom() == res.getIdRoom().getIdRoom()
                                && re.getSquedule().equals(res.getSquedule())) {
                            if (res.getUserId().getIdal() != idal) {
                                notificationService.saveNotification(new Notification(
                                        userService.getUserByIdal(res.getUserId().getIdal()).getIdal(), "covid"));
                            }
                        }
                    }
                }
            }

        }
        return "redirect:/admin/manage_users";
    }

    /**
     * Get all the users from the database
     * 
     * @param model
     * @return String
     */
    @GetMapping("/admin/manage_users")
    public String manage_users(Model model) {

        List<net.javaguides.springboot.model.User> users = userService.getAllUsers();
        model.addAttribute("userList", users);
        return "/admin/manage_users";
    }

    /**
     * Get and redirect to the chart page
     * 
     * @param model
     * @return String
     */
    @GetMapping("/admin/charts")
    public String showCharts(Model model) {

        return "/admin/charts";
    }

    /**
     * Get the remaining desks for AJAX
     * 
     * @param model
     * @return String
     */
    @GetMapping("/admin/availableDesks")
    public String remainingDesks(Model model) {

        LocalDate todaydate = LocalDate.now();

        List<String> timeIntervals = Arrays.asList("12:00:00 12:30:00", "12:30:00 13:00:00", "13:00:00 13:30:00");
        List<String> freeDesks = new ArrayList<>();
        for (Room room : roomService.getAllRooms()) {
            for (String string : timeIntervals) {
                int desks = 0;
                for (Reservation r : reservationService.getAllReservations()) {
                    try {

                        if (r.getSquedule().toString().substring(0, r.getSquedule().toString().length() - 2)
                                .split(" ")[0].equals(todaydate.toString().split(" ")[0])
                                && r.getIdBuilding().getIdBuilding() == room.getIdBuilding().getIdBuilding()
                                && r.getIdRoom().getIdRoom() == room.getIdRoom()
                                && r.getSquedule().toString().substring(0, r.getSquedule().toString().length() - 2)
                                        .split(" ")[1].equals(string.split(" ")[0])) {
                            desks++;
                        }

                    } catch (NumberFormatException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                List<Desk> allDesks = deskService.getAllDesk();
                List<Desk> roomDesks = new ArrayList<Desk>();
                for (Desk desk : allDesks) {
                    if (desk.getIdRoom().getIdBuilding().getIdBuilding() == room.getIdBuilding().getIdBuilding()
                            && desk.getIdRoom().getIdRoom() == room.getIdRoom() && desk.getType() != -1) {
                        roomDesks.add(desk);
                    }

                }
                ResourceBundle rb = ResourceBundle.getBundle("messages", Locale.getDefault());
                freeDesks.add(rb.getString("room.name") + ": " + room.getIdRoom() + "/" + rb.getString("building.name")
                        + ": " + room.getIdBuilding().getIdBuilding() + "/" + rb.getString("time.interval") + ": "
                        + string.split(" ")[0] + " " + string.split(" ")[1] + "/" + rb.getString("desk.available")
                        + ": " + (roomDesks.size() - desks));
            }

        }
        model.addAttribute("freeDeskList", freeDesks);
        return "/admin/main :: freeDeskList";

    }

}
