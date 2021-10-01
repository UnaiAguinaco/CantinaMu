import java.util.concurrent.CyclicBarrier;

public class PublicCyclicBarrier {

    public static void main(String[] args) {
        int nThread = 5;
        final CyclicBarrier startBarrier = new CyclicBarrier(nThread + 1);
        final CyclicBarrier endBarrier = new CyclicBarrier(nThread + 1);

        for (int i = 0; i < nThread; i++) {
            Thread myThread = new Thread() {
                public void run() {
                    try {
                    	startBarrier.await();
                        System.out.println("Save in a list");
                        endBarrier.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            myThread.start();
        }

        try {
            System.out.println("Barrier started");
            startBarrier.await();
            endBarrier.await();
            System.out.println("Save all in the database");
            System.out.println("Barrier finished");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}