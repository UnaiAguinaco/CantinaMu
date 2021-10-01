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
@Table(name = "roomRecord")
public class RoomRecord {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public int roomRecordId;

  @Column(name = "squedule", columnDefinition = "DATETIME")
  @Temporal(TemporalType.TIMESTAMP)
  private Date squedule;

  @ManyToOne
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "idRoom")
  private Room idRoom;

  @ManyToOne
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "idBuilding")
  private Building idBuilding;

  @ManyToOne
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "idal")
  private User userId;

  private String time;
  private String type;

  public RoomRecord(Room idRoom, Desk deskId, Building idBuilding, User userId, Date squedule,
      String time, String type) {
    super();
    this.idRoom = idRoom;
    this.userId = userId;
    this.squedule = squedule;
    this.idBuilding = idBuilding;
    this.time = time;
    this.type  = type;
  }

  public RoomRecord() {

  }

  
  /** 
   * @return int
   */
  public int getRoomRecordId() {
    return roomRecordId;
  }

  
  /** 
   * @param roomRecordId
   */
  public void setRoomRecordId(int roomRecordId) {
    this.roomRecordId = roomRecordId;
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
  
  /** 
   * @return String
   */
  public String getType() {
    return type;
  }

  
  /** 
   * @param type
   */
  public void setType(String type) {
    this.type = type;
  }
}
