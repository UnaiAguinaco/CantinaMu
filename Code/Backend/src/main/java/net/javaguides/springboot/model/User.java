package net.javaguides.springboot.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import javax.persistence.JoinColumn;

@Entity
@Component
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idal;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "username")
	private String username;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "infected")
	private boolean infected;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "idal"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))

	private Collection<Role> roles;

	public User(String username, String firstName, String lastName, String email, String password, Collection<Role> roles,
			boolean infected) {
		super();
		this.firstName = firstName;
		this.username = username;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.roles = roles;
		this.infected = infected;
	}

	public User() {

	}

	
	/** 
	 * @return int
	 */
	public int getIdal() {
		return idal;
	}

	
	/** 
	 * @param idal
	 */
	public void setIdal(int idal) {
		this.idal = idal;
	}

	
	/** 
	 * @return String
	 */
	public String getFirstName() {
		return firstName;
	}

	
	/** 
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	
	/** 
	 * @return String
	 */
	public String getUserName() {
		return username;
	}

	
	/** 
	 * @param username
	 */
	public void setUserName(String username) {
		this.username = username;
	}

	
	/** 
	 * @return String
	 */
	public String getLastName() {
		return lastName;
	}

	
	/** 
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	
	/** 
	 * @return String
	 */
	public String getEmail() {
		return email;
	}

	
	/** 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	
	/** 
	 * @return boolean
	 */
	public boolean getInfected() {
		return infected;
	}

	
	/** 
	 * @param infected
	 */
	public void setInfected(Boolean infected) {
		this.infected = infected;
	}

	
	/** 
	 * @return String
	 */
	public String getPassword() {
		return password;
	}

	
	/** 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	
	/** 
	 * @return Collection<Role>
	 */
	public Collection<Role> getRoles() {
		return roles;
	}

	
	/** 
	 * @param roles
	 */
	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}
}
