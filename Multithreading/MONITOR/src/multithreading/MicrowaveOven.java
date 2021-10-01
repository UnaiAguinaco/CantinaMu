package multithreading;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MicrowaveOven extends Thread{
    int id;
    double seg;
    boolean free;
    Room room;
    int clienteId;

    public MicrowaveOven(int id, Room room, int clienteId){
        this.id=id;
        free=true;
        this.room=room;
        this.clienteId=clienteId;
    }
    
   
	@Override
    public synchronized void run() {
		free=false;
    	boolean condition=true;
    	while(condition) {
    		 System.out.println("--------------Microwave "+ id + " oven's counter -------> " + seg);
    	        if(seg == 0){
    	            //timer.stop();
    	        	condition=false;
    	        }
    	        else{
    	            seg--;
    	        }
    	        try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	}
    	System.out.println("--------Microondas "+id+ " se ha liberado");
    	room.using(null, clienteId);
    	free=true;
    }

	public boolean isFree() {
		return free;
	}

	public void setFree(boolean free) {
		this.free = free;
	}

	public void setSeg(double seg) {
		this.seg = seg;
	}
	public int getIdMicrowave() {
		return id;
	}

}
