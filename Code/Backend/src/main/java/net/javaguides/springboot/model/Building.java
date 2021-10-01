package net.javaguides.springboot.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;

import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "building")

public class Building {

    @Id
    @Column(name = "idBuilding")
    private int idBuilding;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "buildingId", cascade = CascadeType.ALL)

    private Collection<Room> rooms;


    public Building(int idBuilding, String name, String address, Collection<Room> rooms) {
        super();
        this.idBuilding = idBuilding;
        this.name = name;
        this.address = address;
        this.rooms = rooms;
    }

    public Building() {

    }

    
    /** 
     * @return int
     */
    public int getIdBuilding() {
        return idBuilding;
    }

    
    /** 
     * @param idBuilding
     */
    public void setIdBuilding(int idBuilding) {
        this.idBuilding = idBuilding;
    }

    
    /** 
     * @return String
     */
    public String getName() {
        return name;
    }

    
    /** 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    
    /** 
     * @return String
     */
    public String getAddress() {
        return address;
    }

    
    /** 
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    
    /*
     * public Collection<Role> getRoles() { return roles; } public void
     * setRoles(Collection<Role> roles) { this.roles = roles; }
     */

}
