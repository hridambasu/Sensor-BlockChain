package com.sensor;

import java.util.*;

import static java.lang.Thread.sleep;

public class StartMain {

    //public static ArrayList dataBuffer;
    public static Queue bufferQueue = new LinkedList();

    public static void main(String[] args) {
        ArrayList receivedData;
        ChatterServer chatterServer = new ChatterServer();
        Thread t1 = new Thread(chatterServer);
        t1.start();

        while(true){
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //System.out.println("data in main:\n");
            if(bufferQueue.size() > 0){
                receivedData = (ArrayList)bufferQueue.poll();
                new ProcessingModule().processStaticData(receivedData);
                new ProcessingModule().processRealtimeData(receivedData);
                System.out.println("Received id is:" + receivedData.get(0) + "\n");
            }else {
                System.out.println("Waiting data from sensor...");
                continue;
            }
        }

    }
}
