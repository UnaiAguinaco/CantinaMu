import java.util.ArrayList;
import java.util.List;

/**
 * 
 */

/**
 * @author Amaia
 *
 */
public class Main {
	static Buffer buffer = new Buffer();

	
	public Main() {
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		for(int i =0 ; i<30; i++) {
			buffer.setInfo("ACTION"+i);
			buffer.run();
		}



	}

	static class Buffer extends Thread{
		List<String> list = new ArrayList<String>();
		static int nElem=0;
		String info;
		
		@Override
		public synchronized void run() {
			enterList(info);
			nElem++;	
			if(nElem>5) {
				exitList();
			}
		}
		public synchronized void exitList() {
			for(int i=0; i<5; i++) {
				System.out.println("\tStoring "+ list.get(0)+" in database");
				list.remove(0);
				nElem--;
			}
			System.out.println("\nThe actions have been loaded to the database\n");

		}
		public synchronized void enterList(String info) {
			System.out.println("Storing in memory:"+info);
			list.add(info);
		}
		public synchronized void setInfo(String info) {
			this.info=info;
		}
	}
	
}
