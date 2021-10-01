package net.javaguides.springboot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import net.javaguides.springboot.chart.DataRestController;
import net.javaguides.springboot.controller.AdminController;
import net.javaguides.springboot.controller.FaqController;
import net.javaguides.springboot.controller.RoomController;
import net.javaguides.springboot.controller.UserController;
import net.javaguides.springboot.controller.dto.BuildingRegistrationDto;
import net.javaguides.springboot.controller.dto.UserRegistrationDto;
import net.javaguides.springboot.model.Building;
import net.javaguides.springboot.model.ChangePassword;
import net.javaguides.springboot.model.Desk;
import net.javaguides.springboot.model.Faq;
import net.javaguides.springboot.model.Notification;
import net.javaguides.springboot.model.Reservation;
import net.javaguides.springboot.model.Room;
import net.javaguides.springboot.model.RoomRecord;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.model.covid19_infections;
import net.javaguides.springboot.service.building.BuildingService;
import net.javaguides.springboot.service.covid19_infections.Covid19InfectionsService;
import net.javaguides.springboot.service.desk.DeskService;
import net.javaguides.springboot.service.faq.FaqService;
import net.javaguides.springboot.service.notification.NotificationService;
import net.javaguides.springboot.service.reservation.ReservationService;
import net.javaguides.springboot.service.room.RoomService;
import net.javaguides.springboot.service.roomRecord.RoomRecordService;
import net.javaguides.springboot.service.user.UserService;




/*
 * Most of the testing is done taking into account database values to test that the results that are being displayed
 * 
 * are the expected, if this test would be executed in another computer that the one where it was tested some of the
 * 
 * tests would have errors or failures. As for the testing some data is saved in the database some objects are created
 * 
 * with null parameters to ease the testing and can interfere with other tests if the tests are executed a second time
 * 
 * Thus, the database should be cleaned up to prevent this errors. In this case the covid19_infections table creates
 * 
 * a null userId object which interferes with another testing function. Finally, some functions in the controllers are
 * 
 * refactored because of the usage of getting the user or other parameters from the session. So for testing it the 
 * 
 * refactored functions have to be adapted to fit the database.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class SpringbootThymeleafCrudWebAppApplicationTests {

	@Autowired
	private UserController userController;
	@Autowired
	private GreetingController greetingController;
	@Autowired
	private RoomController roomController;
	@Autowired
	private DataRestController restController;
	@Autowired
	private AdminController adminController;
	@Autowired
	private FaqController faqController;
	@Autowired
	private UserService userService;
	@Autowired
	private BuildingService buildingService;
	@Autowired
	private Covid19InfectionsService covid19Service;
	@Autowired
	private DeskService deskService;
	@Autowired
	private FaqService faqService;
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private ReservationService reservationService;
	@Autowired
	private RoomRecordService roomRecordService;
	@Autowired
	private RoomService roomService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
		public SpringbootThymeleafCrudWebAppApplicationTests() {

	}
		private MockMvc mockMvc;

////////////////////////////////User Controller function testing//////////////////////////////////////////////////////

	@Test
	public void testChargeRooms() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(this.userController).build();
			MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/chargeRooms/5"))
					.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
			assertEquals(1, mvcResult.getModelAndView().getModel().size());
			
	}

	@Test
	public void testUserMain() throws Exception {
				
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(this.userController).build();
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/main"))
					.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();		
	}
	@Test
	public void testUserSettings() throws Exception {
				
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(this.userController).build();
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/userSettings"))
					.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();	
		assertEquals("/user/main", mvcResult.getModelAndView().getViewName());
	}
	
	 @Test
	public void testRemainingDesks() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(this.userController).build();
			MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/remainingDesks/5-3-2021-01-20-12:00:00"))
					.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();	
			assertEquals(1, mvcResult.getModelAndView().getModel().size());
	}

	 @Test
		public void testFreeDesks() throws Exception {
			MockitoAnnotations.initMocks(this);
			mockMvc = MockMvcBuilders.standaloneSetup(this.userController).build();
				MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/remainingDesks/5-3-2021-01-20-12:00:00"))
						.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();	
				assertEquals("{freeDesks=En la habitación 3 del edificio 5, hay 2 mesas disponibles para la hora seleccionada}", mvcResult.getModelAndView().getModel().toString());
		}
	
	 @Test
		public void testshowReservation() throws Exception {
			MockitoAnnotations.initMocks(this);
			mockMvc = MockMvcBuilders.standaloneSetup(this.userController).build();
				MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/show_reservation/159"))
						.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();		
				assertEquals("/user/show_reservation", mvcResult.getModelAndView().getViewName());
				
		}
		
		@Test
		public void testmakeReservation() throws Exception {
			MockitoAnnotations.initMocks(this);
			mockMvc = MockMvcBuilders.standaloneSetup(this.roomController).build();
				MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/reservation/153"))
						.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();	
		}
		
		@Test
		public void testEditReservation() throws Exception {
			MockitoAnnotations.initMocks(this);
			mockMvc = MockMvcBuilders.standaloneSetup(this.userController).build();
				MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/editReservation/153"))
						.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();	
		}
////////////////////////////////////////////Sockets function testing (refactorized)/////////////////////////////////////////////
	@Test
	public void testenter() throws Exception {
				
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(this.greetingController).build();
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/enter/3"))
					.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();		
	}
	
	@Test
	public void testchangeDesks() throws Exception {
				
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(this.greetingController).build();
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/change/3"))
					.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();		
	}

/////////////////////////////////////////Admin Controller function testing/////////////////////////////////////////////////////
	@Test
	public void testRoomMap() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(this.roomController).build();
			MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/admin/room_map/5"))
					.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();	
			assertEquals("/admin/room_map", mvcResult.getModelAndView().getViewName());
	}
	
	@Test
	public void testupdateRoomMap() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(this.roomController).build();
			MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/admin/update_room/5"))
					.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();	
			assertEquals("/admin/create_room", mvcResult.getModelAndView().getViewName());
	}

	

	@Test
	public void testavailableDesks() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(this.adminController).build();
			MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/admin/availableDesks"))
					.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();	
			assertEquals(1, mvcResult.getModelAndView().getModel().size());
	}
	
	
	

	@Test
	public void testupdateStatus() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(this.adminController).build();
			MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/admin/updateStatus/1"))
					.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();	
			assertEquals("/admin/manage_users", mvcResult.getModelAndView().getViewName());
	}

/////////////////////////////////////////////////Chart testing//////////////////////////////////////////////////////////////

	@Test
	public void testRestgetInfections() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(this.restController).build();
			MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/admin/charts/getInfections"))
					.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();	
			String s=mvcResult.getResponse().getContentAsString();
			boolean b=false;
			if(s.contains("0")&&s.contains("numInfections")) b=true;

			assertEquals(true, b);
	}

	@Test
	public void testRestgetReservations() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(this.restController).build();
			MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/admin/charts/reservation"))
					.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();	
	}

	@Test
	public void testRestgetInfectionsPerWeek() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(this.restController).build();
			MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/admin/charts/infectionPerWeek"))
					.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();	
	}

	@Test
	public void testRestREservationPerRoom() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(this.restController).build();
			MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/admin/charts/reservationPerRoom"))
					.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();	
	}

///////////////////////////////////////////Faq Controller testing//////////////////////////////////////////////////////
	@Test
	public void testfaq() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(this.faqController).build();
			MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/faq"))
					.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
			assertEquals("/user/main", mvcResult.getModelAndView().getViewName());
	}
	

  
	
//////////////////////////////////BUILDING getters/setters+impl functions///////////////////////////////////////////////////////////
	@Test
	public void testSetBuildingId() {
		Building building = new Building(2, "Name", "Address",null);
		building.setIdBuilding(3);
		assertEquals(3, building.getIdBuilding());
	}
	@Test
	public void testSetBuildingName() {
		Building building = new Building(2, "Name", "Address",null);
		building.setName("other");
		assertEquals("other", building.getName());
	}
	@Test
	public void testSetBuildingAddress() {
		Building building = new Building(2, "Name", "Address",null);
		building.setAddress("other");
		assertEquals("other", building.getAddress());
	}


	@Test
    void listBuilding(){
		List<Building> bList = buildingService.getAllBuildings();
		assertNotEquals(0, bList.size());

	}
	@Test
	void findBuilding(){	
		buildingService.getBuildingById(5);
	}

	@Test
	void createBuilding(){
		Building building = new Building(2, "Name", "Address",null);
		buildingService.saveBuilding(building);
		assertNotNull(buildingService.getBuildingById(0));
		
	}
	@Test
	void createBuildingDto(){
		BuildingRegistrationDto building = new BuildingRegistrationDto(2, "Name", "Address");
		buildingService.save(building);
		assertNotNull(buildingService.getBuildingById(0));
		
	}

	@Test
	void deleteBuilding(){
		buildingService.deleteBuildingById(2);
		List<Building> buildingList = buildingService.getAllBuildings();
	}
///////////////////////////////////////COVID-INFECTIONS  getters/setters+impl functions////////////////////////////////
@Test
	public void testSetCovidId() {
		Date date=new Date();
		covid19_infections infection = new covid19_infections(1,null,date);
		infection.setcovid19Infection_id(2);
		assertEquals(2, infection.getcovid19Infection_id());
	}
	@Test
	public void testSetCovidUserId() {
		Date date=new Date();
		covid19_infections infection = new covid19_infections(1,null,date);
		User user=new User(null, null, null, null, null, null, false);
		infection.setUserId(user);
		assertEquals(user, infection.getUserId());
	}
	@Test
	public void testSetCovidDate() {
		Date date=new Date();
		covid19_infections infection = new covid19_infections(1,null,date);
		infection.setSquedule(date);
		assertEquals(date, infection.getSquedule());
	}


@Test
    void listcovid19(){
		List<covid19_infections> bList = covid19Service.getAllInfections();
		assertNotEquals(0, bList.size());

	}
	@Test
	void findcovid19(){		
		covid19Service.getInfectionsByIdal(1);
	}

	@Test
	void createcovid19(){
		Date date=new Date();
		covid19_infections infection = new covid19_infections(1,null,date);
		covid19Service.saveInfections(infection);
		
	}



/////////////////////////////////////////USER getters/setters+impl functions///////////////////////////////////////////
@Test
public void testSetIdal() {
	User user = new User("name.surname", "name","surname", "name.surname@gmail.com", passwordEncoder.encode("12345"), null, false);
	user.setIdal(333);
	assertEquals(333, user.getIdal());
}
@Test
public void testSetUserFirstName() {
	User user = new User("name.surname", "name","surname", "name.surname@gmail.com", passwordEncoder.encode("12345"), null, false);
	user.setFirstName("other");
	assertEquals("other", user.getFirstName());
}
@Test
public void testSetUserLastName() {
	User user = new User("name.surname", "name","surname", "name.surname@gmail.com", passwordEncoder.encode("12345"), null, false);
	user.setLastName("other");
	assertEquals("other", user.getLastName());
}
@Test
public void testSetEmail() {
	User user = new User("name.surname", "name","surname", "name.surname@gmail.com", passwordEncoder.encode("12345"), null, false);
	user.setEmail("other@gmail.com");
	assertEquals("other@gmail.com", user.getEmail());
}
@Test
public void testUsername() {
	User user = new User("name.surname", "name","surname", "name.surname@gmail.com", passwordEncoder.encode("12345"), null, false);
	user.setUserName("other");
	assertEquals("other", user.getUserName());
}
@Test
public void testPassword() {
	User user = new User("name.surname", "name","surname", "name.surname@gmail.com", passwordEncoder.encode("12345"), null, false);
	user.setPassword(passwordEncoder.encode("other"));
}

@Test
public void testInfected() {
	User user = new User("name.surname", "name","surname", "name.surname@gmail.com", passwordEncoder.encode("12345"), null, false);
	user.setInfected(true);
	assertEquals(true, user.getInfected());
}

	@Test
	void userList(){
		List<User> userList = userService.getAllUsers();
		assertNotEquals(0, userList.size());
	}
	@Test
	void findUser(){
		
		userService.getUserByIdal(1);
	}

	@Test
	void createUser(){
		User user = new User("name.surname", "name","surname", "name.surname@gmail.com", passwordEncoder.encode("12345"), null, false);
		userService.saveUser(user);
		assertNotNull(userService.getUserByUsername("name.surname"));
		
	}
	@Test
	void createUserDto(){
		UserRegistrationDto user = new UserRegistrationDto("name2", "surname2", "name2.surname2", passwordEncoder.encode("12345"));
		userService.save(user);
		assertNotNull(userService.getUserByUsername("name2.surname2"));
		
	}
	@Test
	void findUserbyUsername(){
		userService.getUserByUsername("name");		
	}
	@Test
	void loadUserbyUsername(){
		userService.loadUserByUsername("unai.aguinaco");		
	}
	
	/*@Test
	void deleteUser(){
		userService.deleteUserByIdal(111);
	}*/







//////////////////////////////////////////FAQ getters/setters+impl functions///////////////////////////////////////////////////
	@Test
	public void testSetFaqId() {
		Faq faq = new Faq(5,"text","text",false);
		faq.setIdal(5);
		assertEquals(5, faq.getCreatorId());
	}
	
	@Test
	public void testSetFaqDefinition() {
		Faq faq = new Faq(5,"text","text",false);
		faq.setDefinition("other");
		assertEquals("other", faq.getDefinition());
	}
	
	@Test
	public void testSetFaqAnswer() {
		Faq faq = new Faq(5,"text","text",false);
		faq.setAnswer("other");
		assertEquals("other", faq.getAnswer());
	}
	
	@Test
	public void testSetFaq() {
		Faq faq = new Faq(5,"text","text",false);
		faq.setFaq(true);
		assertEquals(true, faq.isFaq());
	}

	@Test
	void faqList(){
		List<Faq> faqList = faqService.getAllFaqs();
	}

	@Test
	void findFaq(){
		
		faqService.getFaqByIdal(3);
	}

	@Test
	void createFaq(){
		Faq faq = new Faq(5,"text","text",false);
		faqService.saveFaq(faq);
		assertNotNull(faqService.getFaqByIdal(5));
		
	}
	

	////////////////////////////////////////NOTIFICATION getters/setters+impl functions///////////////////////////////////////////////////////

	@Test
	void notificationList(){
		List<Notification> notificationList = notificationService.getAllNotifications();
		assertNotEquals(0, notificationList.size());
	}

	@Test
	void findNotification(){	
		notificationService.getNotificationByIdal(2);
	}

	@Test
	void createNotification(){
		Notification notification = new Notification(11111,"covid");
		notificationService.saveNotification(notification);
		assertNotNull(notificationService.getNotificationByIdal(2));
		
	}
	

/////////////////////////////////////////DESK  getters/setters+impl functions//////////////////////////////////////////////

	@Test
	public void testSetDeskId() {
		Desk desk = new Desk(100,3,3,null,0,5);
		desk.setIdDesk(333);
		assertEquals(333, desk.getIdDesk());
	}

	@Test
	public void testSetDeskRoomId() {
		Desk desk = new Desk(100,3,3,null,0,5);
		Room b=new Room(64, null,4,4);
		desk.setIdRoom(b);
		assertEquals(b, desk.getIdRoom());
	}

	@Test
	public void testSetDeskColumn() {
		Desk desk = new Desk(100,3,3,null,0,5);
		desk.setColumn(4);
		assertEquals(4, desk.getColumn());
	}

	@Test
	public void testSetDeskRow() {
		Desk desk = new Desk(100,3,3,null,0,5);
		desk.setRow(4);
		assertEquals(4, desk.getRow());
	}

	@Test
	public void testSetDeskType() {
		Desk desk = new Desk(100,3,3,null,0,5);
		desk.setType(1);
		assertEquals(1, desk.getType());
	}

	@Test
	public void testSetDeskNumber() {
		Desk desk = new Desk(100,3,3,null,0,5);
		desk.setDeskNumber(6);
		assertEquals(6, desk.getDeskNumber());
	}

	@Test
	void deskList(){
		List<Desk> deskList = deskService.getAllDesk();
		assertNotEquals(0, deskList.size());
	}

	@Test
	void findDesk(){
		
		deskService.getDeskById(1);
	}

	

///////////////////////////////////////RESERVATION  getters/setters+impl functions//////////////////////////////////////////////
	@Test
	public void testSetReservationId() {
		Reservation reservation = new Reservation(5,null,null,null,null,null,"18-01-2021");
		reservation.setReservationId(888);
		assertEquals(888, reservation.getReservationId());
	}
	@Test
	public void testSetReservationBuilding() {
		Reservation reservation = new Reservation(5,null,null,null,null,null,"18-01-2021");
		Building b=new Building(64, null, null, null);
		reservation.setIdBuilding(b);
		assertEquals(b, reservation.getIdBuilding());
	}
	@Test
	public void testSetReservationRoom() {
		Reservation reservation = new Reservation(5,null,null,null,null,null,"18-01-2021");
		Room b=new Room(64, null,4,4);
		reservation.setIdRoom(b);
		assertEquals(b, reservation.getIdRoom());
	}

	@Test
	public void testSetReservationDesk() {
		Reservation reservation = new Reservation(5,null,null,null,null,null,"18-01-2021");
		Desk b=new Desk(100,3,3,null,0,5);
		reservation.setDeskId(b);
		assertEquals(b, reservation.getDeskId());
	}

	@Test
	public void testSetReservationUser() {
		Reservation reservation = new Reservation(5,null,null,null,null,null,"18-01-2021");
		User b=new User("name.surname", "name","surname", "name.surname@gmail.com", passwordEncoder.encode("12345"), null, false);
		reservation.setUserId(b);
		assertEquals(b, reservation.getUserId());
	}

	@Test
	public void testSetReservationTime() {
		Reservation reservation = new Reservation(5,null,null,null,null,null,"18-01-2021");
		reservation.setTime("other");
		assertEquals("other", reservation.getTime());
	}

	@Test
	public void testSetReservationSquedule() {
		Reservation reservation = new Reservation(5,null,null,null,null,null,"18-01-2021");
		Date date=new Date(2021, 01, 20);
		reservation.setSquedule(date);
		assertEquals(date, reservation.getSquedule());
	}

	@Test
	void findReservation(){
		
		reservationService.getReservationByIdal(4);
	}


//////////////////////////////////////////ROOM getters/setters+impl functions////////////////////////////////////////////

		@Test
		void roomList(){
			List<Room> roomList = roomService.getAllRooms();
			assertNotEquals(0, roomList.size());
		}
		
//////////////////////////////ROOM RECORD getters/setters+impl functions/////////////////////////////////////////////////////

		@Test
	public void testSetRoomReservationId() {
		RoomRecord roomRecord = new RoomRecord(1,null,null,null,null,null,"18-01-2021");
		roomRecord.setRoomRecordId(888);
		assertEquals(888, roomRecord.getRoomRecordId());
	}
	@Test
	public void testSetRoomReservationBuilding() {
		RoomRecord roomRecord = new RoomRecord(1,null,null,null,null,null,"18-01-2021");
		Building b=new Building(64, null, null, null);
		roomRecord.setIdBuilding(b);
		assertEquals(b, roomRecord.getIdBuilding());
	}
	@Test
	public void testSetRoomReservationRoom() {
		RoomRecord roomRecord = new RoomRecord(1,null,null,null,null,null,"18-01-2021");
		Room b=new Room(64, null,4,4);
		roomRecord.setIdRoom(b);
		assertEquals(b, roomRecord.getIdRoom());
	}

	@Test
	public void testSetRoomReservationUser() {
		RoomRecord roomRecord = new RoomRecord(1,null,null,null,null,null,"18-01-2021");
		User b=new User("name.surname", "name","surname", "name.surname@gmail.com", passwordEncoder.encode("12345"), null, false);
		roomRecord.setUserId(b);
		assertEquals(b, roomRecord.getUserId());
	}

	@Test
	public void testSetRoomReservationTime() {
		RoomRecord roomRecord = new RoomRecord(1,null,null,null,null,null,"18-01-2021");
		roomRecord.setTime("other");
		assertEquals("other", roomRecord.getTime());
	}

	@Test
	public void testSetRoomReservationSquedule() {
		RoomRecord roomRecord = new RoomRecord(1,null,null,null,null,null,"18-01-2021");
		Date date=new Date(2021, 01, 20);
		roomRecord.setSquedule(date);
		assertEquals(date, roomRecord.getSquedule());
	}
		
		@Test
		void roomRecordList(){
			List<RoomRecord> roomRecordList = roomRecordService.getAllRoomRecords();
			assertNotEquals(0, roomRecordList.size());
		}

		@Test
		void createRoomRecord(){
			RoomRecord roomRecord = new RoomRecord(1,null,null,null,null,null,"18-01-2021");
			roomRecordService.saveRoomRecord(roomRecord);
			assertNotNull(roomRecordService.getRoomRecordByIdal(1));
			
		}
	
/////////////////////////////////////////////CHANGE PASSWORD getters/setters+impl functions//////////////////////////////////
		@Test
		void changeOldPassword(){
			ChangePassword password=new ChangePassword();
			password.setOldPassword("12345");
			assertEquals("12345", password.getOldPassword());			
		}

		@Test
		void changeNewPassword(){
			ChangePassword password=new ChangePassword();
			password.setNewPassword("12345");
			assertEquals("12345", password.getNewPassword());			
		}

		@Test
		void changeRepeatPassword(){
			ChangePassword password=new ChangePassword();
			password.setRepeatPassword("12345");
			assertEquals("12345", password.getRepeatPassword());			
		}

///////////////////////////////////////////////////////Monitor testing///////////////////////////////////////////////////
		@Test
		void testMonitor(){
			Monitor.setWritter(true);
			assertEquals(true, Monitor.getWritter());
			Monitor.incrementReaders();
			Monitor.incrementReaders();
			assertEquals(2,Monitor.getReaderInside());
			Monitor.decrementReaders();
			assertEquals(1,Monitor.getReaderInside());
		}
	
/////////////////////////////////////////////////////Chart testing/////////////////////////////////////////////////////
		@Test
	public void testSetChatRoomId() {
		ChatMessage chat = new ChatMessage("5",null,"name",4,4,5);
		chat.setRoomId("6");
		assertEquals("6", chat.getRoomId());
	}

	@Test
	public void testSetChatType() {
		ChatMessage chat = new ChatMessage("5",null,"name",4,4,5);
		chat.setType("yes");
		assertEquals("yes", chat.getType());
	}
	@Test
	public void testSetChatRow() {
		ChatMessage chat = new ChatMessage("5",null,"name",4,4,5);
		chat.setRow(6);
		assertEquals(6, chat.getRow());
	}
	@Test
	public void testSetChatCol() {
		ChatMessage chat = new ChatMessage("5",null,"name",4,4,5);
		chat.setCol(6);
		assertEquals(6, chat.getCol());
	}
	@Test
	public void testSetChatUsers() {
		ChatMessage chat = new ChatMessage("5",null,"name",4,4,5);
		chat.setUsers(6);
		assertEquals(6, chat.getUsers());
	}
//////////////////////////////////////////////////////////////BUFFER/////////////////////////////////////////////////////////
	@Test
	public void testenterList() {
		MyBuffer buffer=new MyBuffer();
		buffer.enterList(new RoomRecord(2,null,null,null,null,null,"19-01-2021"), 4);
	}

	
}

