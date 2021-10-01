package net.javaguides.springboot;

import java.util.ArrayList;
import java.util.List;

import net.javaguides.springboot.model.RoomRecord;
import net.javaguides.springboot.service.roomRecord.RoomRecordService;

public class MyBuffer extends Thread {

    RoomRecordService roomRecordService;
    List<RoomRecord> list = new ArrayList<RoomRecord>();
    static int nElem=0;
    RoomRecord info;
    
    public MyBuffer(RoomRecordService roomRecordService){
        this.roomRecordService = roomRecordService;
    }
    @Override
    public synchronized void run() {
        enterList(info);
        nElem++;	
        if(nElem>5) {
            exitList();
        }
    }

    /**
     * Save in the database the information of memory
     */
    public synchronized void exitList() {
        for(int i=0; i<5; i++) {
            roomRecordService.saveRoomRecord(list.get(0));
            list.remove(0);
            nElem--;
        }

    }
    
    /** 
     * Store in memory the action 
     * @param info
     */
    public synchronized void enterList(RoomRecord info) {
        list.add(info);
    }
    
    /** 
     * @param info
     */
    public synchronized void setInfo(RoomRecord info) {
        this.info=info;
    }

}
