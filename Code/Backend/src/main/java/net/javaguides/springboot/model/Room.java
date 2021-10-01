package net.javaguides.springboot.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.JoinColumn;

@Entity
@Table(name = "room")

public class Room {

    @Id
    @Column(name = "idRoom")
    private int idRoom;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "idBuilding")
    private Building buildingId;

    @Column(name = "rowCount")
    private int rowCount;

    @Column(name = "columnCount")
    private int columnCount;

    @OneToMany(mappedBy = "roomId", cascade = CascadeType.ALL)

    private Collection<Desk> desks;

    public Room(int idRoom, Building buildingId, int rowCount, int columnCount) {
        super();

        this.idRoom = idRoom;
        this.buildingId = buildingId;
        this.rowCount = rowCount;
        this.columnCount = columnCount;
    }

    public Room() {

    }

    
    /** 
     * @return int
     */
    public int getIdRoom() {
        return idRoom;
    }

    
    /** 
     * @param idRoom
     */
    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
    }

    
    /** 
     * @return Building
     */
    public Building getIdBuilding() {
        return buildingId;
    }

    
    /** 
     * @param buildingId
     */
    public void setIdBuilding(Building buildingId) {
        this.buildingId = buildingId;
    }

    
    /** 
     * @return int
     */
    public int getRowCount() {
        return rowCount;
    }

    
    /** 
     * @param rowCount
     */
    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    
    /** 
     * @return int
     */
    public int getColumnCount() {
        return columnCount;
    }

    
    /** 
     * @param columnCount
     */
    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

    
    /** 
     * @return Collection<Desk>
     */
    public Collection<Desk> getDesks() {
        return desks;
    }

    
    /** 
     * @param desks
     */
    public void setDesks(Collection<Desk> desks) {
        this.desks = desks;
    }

}
