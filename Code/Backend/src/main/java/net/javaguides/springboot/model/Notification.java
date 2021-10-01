package net.javaguides.springboot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id_notification;

    @Column(name = "user_id")
    private int user_id;

    @Column(name = "definition")
    private String definition;


    public Notification(int user_id, String definition) {
        super();
        this.user_id = user_id;
        this.definition = definition;
    }

    public Notification() {

	}

	
	/** 
	 * @return int
	 */
	public int getUserId() {
		return user_id;
	}

	
	/** 
	 * @param user_id
	 */
	public void setUserId(int user_id) {
		this.user_id = user_id;
	}

	
	/** 
	 * @return int
	 */
	public int getNotificationId() {
		return id_notification;
	}

	
	/** 
	 * @param id_notification
	 */
	public void setNotificationId(int id_notification) {
		this.id_notification = id_notification;
	}

	
	/** 
	 * @return String
	 */
	public String getDefinition() {
		return definition;
	}

	
	/** 
	 * @param definition
	 */
	public void setDefinition(String definition) {
		this.definition = definition;
	}
}
