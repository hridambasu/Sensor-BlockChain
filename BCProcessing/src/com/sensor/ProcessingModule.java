package com.sensor;

import java.util.ArrayList;

public class ProcessingModule {
    //ArrayList staticDataArray = new ArrayList();
    //Object staticDataArray = new Object();
    public void processStaticData(ArrayList staticDataArray){
        System.out.println("Process static data");
        System.out.println("static data: " + staticDataArray.get(2)); //level
        int id = (int)staticDataArray.get(0);
        int type = (int)staticDataArray.get(1);//type
        int level = (int)staticDataArray.get(2);//level
        int source = (int)staticDataArray.get(3);//source
        float value = (float)staticDataArray.get(4);//value
        //TODO: the model to process static data
        if(level == 1){//urgent, response immediately
            int command = 1;
            String result = ControlModule.sendBackToSensor(command);
            System.out.println("Operation: " + result);
        }

    }

    public void processRealtimeData(ArrayList RealtimeDataArray){
        System.out.println("Process real time data");
        System.out.println("real time data: " + RealtimeDataArray.get(0));
        int id = (int)RealtimeDataArray.get(0);
        int type = (int)RealtimeDataArray.get(1);//type
        int level = (int)RealtimeDataArray.get(2);//level
        int source = (int)RealtimeDataArray.get(3);//source
        float value = (float)RealtimeDataArray.get(4);//value

        if(level == 1){//urgent

        }else if(level == 2){//normal

        }else if(level == 3){//

        }else {
            System.out.println("The level of received data is invalid");
        }

    }
}
