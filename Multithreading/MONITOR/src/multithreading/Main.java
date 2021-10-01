package multithreading;

public class Main {

	
    final static int N_CLIENTS=7;  
    Room room;
    Client client;

    public Main(){
    	room=new Room(this);
    	room.initializeMicrowaves();
    	inicializar(-1);
    }
    public synchronized void inicializar(int opt){
    	int i=0;
    	if(opt==-1) {
	        for(i=1;i<=N_CLIENTS;i++){
	        	while(!room.getIntoRoom()) {
	        		try {
						wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        	}
	        	System.out.println("----Cliente "+i+" ha entrado al comedor");
	        	Client client=new Client(room, i);
	        	client.start();
	        }
    	}
        else {
        	room.leaveRoom();
        	notifyAll();
        	System.out.println("----Cliente "+opt+" ha abandonado el comedor");
        }
    }
    
		
    public static void main(String[] arg) {
		Main main = new Main();
	}

}
