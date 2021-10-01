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
@Table(name = "covid19_infections")
public class covid19_infections {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public int covid19Infection_id;
  
  @Column(name = "squedule", columnDefinition = "DATETIME")
  @Temporal(TemporalType.TIMESTAMP)
  private Date squedule;

  @ManyToOne
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "idal")
  private User userId;

  public covid19_infections(int covid19Infection_id, User userId, Date squedule) {
    super();
    this.covid19Infection_id=covid19Infection_id;
    this.userId = userId;
    this.squedule = squedule;
  }

  public covid19_infections() {

  }
  
  /** 
   * @return int
   */
  public int getcovid19Infection_id() {
    return covid19Infection_id;
}


/** 
 * @param covid19Infection_id
 */
public void setcovid19Infection_id(int covid19Infection_id) {
    this.covid19Infection_id = covid19Infection_id;
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

}
