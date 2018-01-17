package com.sensor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

class Dgram {
    public static DatagramPacket toDatagram(String s, InetAddress destIA, int destPort) {
        byte[] buf = new byte[s.length() + 1];
        s.getBytes(0, s.length(), buf, 0);
        return new DatagramPacket(buf, buf.length, destIA, destPort);
    }
    public static String toString(DatagramPacket p){
        return new String(p.getData(), 0, p.getLength());
    }
}

public class ChatterServer implements Runnable {
    static final int INPORT = 8887;
    private byte[] buf = new byte[1000];
    private DatagramPacket dp = new DatagramPacket(buf, buf.length);
    private DatagramSocket socket;

    /*public void communicate() {
        ArrayList data = new ArrayList();
        try {
            socket = new DatagramSocket(INPORT);
            System.out.println("Server started...");
            while (true) {
                socket.receive(dp);
                //String rcvd = Dgram.toString(dp) + ",from address:" + dp.getAddress() + ",port:" + dp.getPort();
                String rcvd = "From address:" + dp.getAddress() + ",port:" + dp.getPort();
                System.out.println("From Client:" + rcvd);
                //dp.getData()get message content
                MAC_struct mac_data = new MAC_struct(dp.getData());
                data = mac_data.parseFrame();
                String echoString = "From Server Echoed:" + rcvd;
                DatagramPacket echo = Dgram.toDatagram(echoString, dp.getAddress(), dp.getPort());
                socket.send(echo);
                StartMain.bufferQueue.offer(data);
                System.out.println("end of communication");
            }
        } catch (SocketException e) {
            System.err.println("Can't open socket");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Communication error");
            e.printStackTrace();
        }
    }*/

    @Override
    public void run() {
        System.out.println("Start a new thread...");
        ArrayList data = new ArrayList();
        try {
            socket = new DatagramSocket(INPORT);
            System.out.println("Server started...");
            while (true) {
                socket.receive(dp);
                //String rcvd = Dgram.toString(dp) + ",from address:" + dp.getAddress() + ",port:" + dp.getPort();
                String rcvd = "From address:" + dp.getAddress() + ",port:" + dp.getPort();
                System.out.println("From Client:" + rcvd);
                //dp.getData()get message content
                MAC_struct mac_data = new MAC_struct(dp.getData());
                data = mac_data.parseFrame();
                String echoString = "From Server Echoed:" + rcvd;
                DatagramPacket echo = Dgram.toDatagram(echoString, dp.getAddress(), dp.getPort());
                socket.send(echo);
                StartMain.bufferQueue.offer(data);
                //System.out.println("end of communication");
            }
        } catch (SocketException e) {
            System.err.println("Can't open socket");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Communication error");
            e.printStackTrace();
        }
    }

}
