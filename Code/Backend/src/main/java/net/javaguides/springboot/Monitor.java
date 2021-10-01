package net.javaguides.springboot;

public class Monitor {

    static boolean writter = false;
    static int readerInside = 0;
    static int id=0;

    public static int getId() {
        return id;
    }
    /** 
     * @param idal
     */
    public static void setId(int idal) {
        id = idal;
    }

    /** 
     * @return boolean
     */
    public static boolean getWritter() {
        return writter;
    }

    
    /** 
     * @param writte
     */
    public static void setWritter(boolean writte) {
        writter = writte;
    }

    
    /** 
     * Return the amount of readers 
     * @return int
     */
    public static int getReaderInside() {
        return readerInside;
    }
    /**
    * Increment the reader's count
    */
    public static void incrementReaders() {
        readerInside++;
    }
    /**
    * Decrement the reader's count
    */
    public static void decrementReaders() {
        readerInside--;
    }


}
