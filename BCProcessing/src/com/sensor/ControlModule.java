package com.sensor;

public class ControlModule {

    public enum ControlCommand{
        SHUTDOWN("SHUTDOWN", 1), START("START", 2);

        private String operation;
        private int index;

        private ControlCommand(String operation, int index){
            this.operation = operation;
            this.index = index;
        }

        public static String getOperation(int index){
            for(ControlCommand ccmd : ControlCommand.values()){
                if(ccmd.getIndex() == index){
                    return ccmd.operation;
                }
            }
            return null;
        }

        public String getOperation(){
            return operation;
        }

        public void setOperation(){
            this.operation = operation;
        }

        public int getIndex(){
            return index;
        }

        public void setIndex(){
            this.index = index;
        }

    }

    public void control(){

    }

    public static String sendBackToSensor(int command){
        //TODO: send back to sensor
        String sendBack = ControlCommand.getOperation(command);
        return sendBack;
    }
}
