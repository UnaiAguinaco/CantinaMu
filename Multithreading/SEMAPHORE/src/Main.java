import java.util.concurrent.Semaphore;

public class Main{

	static int userCount = 0;
    static Semaphore sUser = new Semaphore(1);
    static Semaphore sAdmin = new Semaphore(1);
    static Semaphore sMutex = new Semaphore(1);
    
    static class Read implements Runnable {
        @Override
        public void run() {
            try {
            	sUser.acquire();
                userCount++;
                if (userCount == 1) sAdmin.acquire();
                sUser.release();

                System.out.println("Thread "+Thread.currentThread().getName() + " is BOOKING A TABLE");
                Thread.sleep(1500);
                System.out.println("Thread "+Thread.currentThread().getName() + " has FINISHED BOOKING A TABLE");
                
                sUser.acquire();
                userCount--;
                if (userCount == 0) sAdmin.release();
                sUser.release();

            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    static class Write implements Runnable {
        @Override
        public void run() {
            try {
            	sMutex.acquire();
            	if(userCount>0) {
            		System.out.println("\tThread "+Thread.currentThread().getName() + " is WAITING TO USER RESERVATION");	
            	}
            	sMutex.release();
            	sAdmin.acquire();
                System.out.println("Thread "+Thread.currentThread().getName() + " is EDITING A ROOM");
                Thread.sleep(2500);
                System.out.println("Thread "+Thread.currentThread().getName() + " has finished EDITING A ROOM");
                sAdmin.release();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Read read = new Read();
        Write write = new Write();
        Thread t1 = new Thread(read);
        t1.setName("reader 1");
        Thread t2 = new Thread(read);
        t2.setName("reader 2");
        Thread t3 = new Thread(write);
        t3.setName("writer 1");
        Thread t4 = new Thread(read);
        Thread t5 = new Thread(write);
        t5.setName("writer 2");
        t4.setName("reader 3");
        
        t1.start();
        t4.start();
        t5.start();
        t2.start();
        t3.start();
    }
}