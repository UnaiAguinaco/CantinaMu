package multithreading;

import java.util.ArrayList;
import java.util.List;

public class Room {
	final static int AFORO_MAX=15;
	final static int N_MICROWAVE=8;
	List<MicrowaveOven> listMicro;
	int numClients;
	Main main;
	
	public Room(Main main) {
		listMicro= new ArrayList<>();
		numClients=0;
		this.main=main;
	}

	public void initializeMicrowaves() {
		for(int i=0;i<N_MICROWAVE;i++){
        	MicrowaveOven a =new MicrowaveOven(i, this,0);
        	listMicro.add(a);
        }
	}
	public synchronized void leaveRoom() {
		numClients--;
	}
	
	public synchronized int getFreeMicrowave() {
		int id=-1;
		for(MicrowaveOven a:listMicro) {
			if(a.isFree()) {
				id=a.getIdMicrowave();
				break;
			}
		}
		return id;
	}
	public boolean getIntoRoom() {
		boolean cond=false;
		if(numClients<AFORO_MAX) {
			cond=true;
			numClients++;
		}
		return cond;
		
	}

	public synchronized void using(Client client,int opt) {
		if(opt==-1) {
			int id;
			while(getFreeMicrowave()<0) {
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			id=getFreeMicrowave();
			listMicro.set(id, new MicrowaveOven(id, this,client.getClientId()));
			listMicro.get(id).setFree(false);
			//client.setMicroId(id);
			System.out.println("--------Cliente "+client.getClientId()+ " is using the micro "+id);
			double seg=Math.round(Math.random()*(20-5+1)+5);
			listMicro.get(id).setSeg(seg);
			listMicro.get(id).start();
			
			}
		else {
			notifyAll();
			main.inicializar(opt);
		}
	}
		
	}
