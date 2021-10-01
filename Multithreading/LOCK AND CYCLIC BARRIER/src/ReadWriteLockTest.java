import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockTest {
   private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
   private static String message = " ";

   public static void main(String[] args) throws InterruptedException {
      Thread t1 = new Thread(new WriterA());
      t1.setName("Administrator A");
      
      Thread t2 = new Thread(new WriterB());
      t2.setName("Administrator B");
      
      Thread t3 = new Thread(new Reader());
      t3.setName("User A");
      Thread t4 = new Thread(new Reader());
      t4.setName("User B");
      t1.start();
      t2.start();
      t3.start();
      t4.start();
   }

   static class Reader implements Runnable {

      public void run() {
         
         if(lock.isWriteLocked()) {
            System.out.println("Administrator is editing, reader waiting.");
         }
         lock.readLock().lock();

         try {
            Long duration = (long) (Math.random() * 10000);
            System.out.println(Thread.currentThread().getName() 
               + "  Time Taken to book " + (duration / 1000) + " seconds.");
            Thread.sleep(duration);
         } catch (InterruptedException e) {
            e.printStackTrace();
         } finally {
            System.out.println(Thread.currentThread().getName() +": "+ message );
            lock.readLock().unlock();
         }
      }
   }

   static class WriterA implements Runnable {

      public void run() {
         lock.writeLock().lock();
         
         try {
            Long duration = (long) (Math.random() * 10000);
            System.out.println(Thread.currentThread().getName() 
               + "  Time Taken to edit " + (duration / 1000) + " seconds.");
            Thread.sleep(duration);
         } catch (InterruptedException e) {
            e.printStackTrace();
         } finally {
            message = message.concat("a");
            lock.writeLock().unlock();
         }
      }
   }

   static class WriterB implements Runnable {

      public void run() {
         lock.writeLock().lock();
         
         try {
            Long duration = (long) (Math.random() * 10000);
            System.out.println(Thread.currentThread().getName() 
               + "  Time Taken to edit " + (duration / 1000) + " seconds.");
            Thread.sleep(duration);
         } catch (InterruptedException e) {
            e.printStackTrace();
         } finally {
            message = message.concat("b");
            lock.writeLock().unlock();
         }
      }
   }
}