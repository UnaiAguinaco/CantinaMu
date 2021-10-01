package net.javaguides.springboot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.ManyToOne;

import javax.persistence.JoinColumn;

@Entity
@Table(name = "desk")

public class Desk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDesk;

    @Column(name = "line")
    private int row;

    @Column(name = "col")
    private int column;

    @Column(name = "deskNumber")
    private int deskNumber;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "idRoom")
    private Room roomId;

    @Column(name = "type")
    private int type; // -1 not selectable(white), 0 not selected(green), 1 is being selected(orange),
                      // 2 reserved(red)

    public Desk(int idDesk, int row, int column, Room roomId, int type, int deskNumber) {
        super();
        this.idDesk = idDesk;
        this.row = row;
        this.column = column;
        this.roomId = roomId;
        this.type = type;
        this.deskNumber = deskNumber;
    }

    public Desk() {

    }

    
    /** 
     * @return int
     */
    public int getIdDesk() {
        return idDesk;
    }

    
    /** 
     * @param idDesk
     */
    public void setIdDesk(int idDesk) {
        this.idDesk = idDesk;
    }

    
    /** 
     * @return Room
     */
    public Room getIdRoom() {
        return roomId;
    }

    
    /** 
     * @param roomId
     */
    public void setIdRoom(Room roomId) {
        this.roomId = roomId;
    }

    
    /** 
     * @return int
     */
    public int getRow() {
        return row;
    }

    
    /** 
     * @param row
     */
    public void setRow(int row) {
        this.row = row;
    }

    
    /** 
     * @return int
     */
    public int getColumn() {
        return column;
    }

    
    /** 
     * @param column
     */
    public void setColumn(int column) {
        this.column = column;
    }

    
    /** 
     * @return int
     */
    public int getType() {
        return type;
    }

    
    /** 
     * @param type
     */
    public void setType(int type) {
        this.type = type;
    }

    
    /** 
     * @return int
     */
    public int getDeskNumber() {
        return deskNumber;
    }

    
    /** 
     * @param deskNumber
     */
    public void setDeskNumber(int deskNumber) {
        this.deskNumber = deskNumber;
    }

}
