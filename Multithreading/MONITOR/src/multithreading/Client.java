package multithreading;

public class Client extends Thread {
	int id;
	int microId;
	Room room;
	
	public Client(Room room, int id) {
		this.room=room;
		this.id=id;
	}
	
	public int getClientId() {
		return id;
	}

	public void setMicroId(int microId) {
		this.microId = microId;
	}

	@Override
    public void run() {
		room.using(this,-1);
		
	}

}
