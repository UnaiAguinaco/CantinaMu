package net.javaguides.springboot.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "reservation")
public class Reservation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public int reservationId;

  @Column(name = "squedule", columnDefinition = "DATETIME")
  @Temporal(TemporalType.TIMESTAMP)
  private Date squedule;

  @ManyToOne
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "idRoom")
  private Room idRoom;

  // For Grafana
  @Column(name = "idRoomStr")
  private String idRoomStr;

  @ManyToOne
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "idBuilding")
  private Building idBuilding;

  @ManyToOne
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "id_desk")
  private Desk deskId;

  @ManyToOne
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "idal")
  private User userId;

  private String time;

  public Reservation(int reservationId, Room idRoom, Desk deskId, Building idBuilding, User userId, Date squedule,
      String time, String idRoomStr) {
    super();
    this.reservationId = reservationId;
    this.idRoomStr = idRoomStr;
    this.idRoom = idRoom;
    this.deskId = deskId;
    this.userId = userId;
    this.squedule = squedule;
    this.idBuilding = idBuilding;
    this.time = time;
  }

  public Reservation() {

  }

  
  /** 
   * @return int
   */
  public int getReservationId() {
    return reservationId;
  }

  
  /** 
   * @param reservationId
   */
  public void setReservationId(int reservationId) {
    this.reservationId = reservationId;
  }

  
  /** 
   * @return Room
   */
  public Room getIdRoom() {
    return idRoom;
  }

  
  /** 
   * @param idRoom
   */
  public void setIdRoom(Room idRoom) {
    this.idRoom = idRoom;
  }

  
  /** 
   * @return String
   */
  public String getIdRoomStr() {
    return idRoomStr;
  }

  
  /** 
   * @param idRoomStr
   */
  public void setIdRoomStr(String idRoomStr) {
    this.idRoomStr = idRoomStr;
  }

  
  /** 
   * @return Desk
   */
  public Desk getDeskId() {
    return deskId;
  }

  
  /** 
   * @param deskId
   */
  public void setDeskId(Desk deskId) {
    this.deskId = deskId;
  }

  
  /** 
   * @return Building
   */
  public Building getIdBuilding() {
    return idBuilding;
  }

  
  /** 
   * @param idBuilding
   */
  public void setIdBuilding(Building idBuilding) {
    this.idBuilding = idBuilding;
  }

  
  /** 
   * @return User
   */
  public User getUserId() {
    return userId;
  }

  
  /** 
   * @param userId
   */
  public void setUserId(User userId) {
    this.userId = userId;
  }

  
  /** 
   * @return Date
   */
  public Date getSquedule() {
    return squedule;
  }

  
  /** 
   * @param squedule
   */
  public void setSquedule(Date squedule) {
    this.squedule = squedule;
  }

  
  /** 
   * @return String
   */
  public String getTime() {
    return time;
  }

  
  /** 
   * @param time
   */
  public void setTime(String time) {
    this.time = time;
  }

}
