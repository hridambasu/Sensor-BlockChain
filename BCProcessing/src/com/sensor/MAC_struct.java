package com.sensor;

import java.io.*;
import java.util.ArrayList;

public class MAC_struct{
    byte[] recv = new byte[2048];
    public int nodeID;
    public int nodeID2;
    public int stampFlags;
    public long tmp;
    public MAC_struct(byte[] recv){
        this.recv = recv;
    }
    /**
     analyze structure
     */
    public ArrayList parseFrame(){
        InputStream in_withcode;
        ArrayList parseResult = new ArrayList();
        try {
            //for(int i=0;i<24;i++)
            //    System.out.println("source["+i+"]="+recv[i]);
            in_withcode = new ByteArrayInputStream(recv);
            DataInputStream inputStream  =  new DataInputStream(in_withcode);
            int id=0;
            int type=0;
            int nodeID=0;
            //int source = 0;
            int[] Bits_4 = new int[4];
            //uint8_t id
            id = inputStream.readUnsignedByte();//read one unsigned byte
            parseResult.add(id);
            //System.out.println("id= "+id);
            inputStream.skipBytes(3);//skip N bytes
            //int type
            type = inputStream.readInt();//readInt() to read a 4 bytes
            parseResult.add(int_EndianBigtoLittle(type));
            //System.out.println("type= "+int_EndianBigtoLittle(type));
            //uint8_t level
            /*nodeID = inputStream.readInt();
            System.out.println("nodeID= "+int_EndianBigtoLittle(nodeID));*/
            //int level = inputStream.readUnsignedByte();//read 2 unsigned bytes
            int level = inputStream.readInt();
            parseResult.add(int_EndianBigtoLittle(level));
            //System.out.println("level= "+level);
            //inputStream.skipBytes(3);
            //uint32_t source
            int source = inputStream.readInt();
            parseResult.add(int_EndianBigtoLittle(source));
            //System.out.println("source= "+Long_EndianBigtoLittle(source));
            //float value
            for(int i=0;i<4;i++)
                Bits_4[i] = inputStream.readUnsignedByte();
            parseResult.add(Float_EndianBigtoLittle(Bits_4));
            //System.out.println("value= "+Float_EndianBigtoLittle(Bits_4));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return parseResult;
    }
    /**
     * transfer bytes[] array to String
     * @param valArr
     * @param startpoint
     * @param maxLen
     * @return
     */
    private static String toStr(byte[] valArr,int startpoint,int maxLen) {
        int index = 0;
        while(index + startpoint < valArr.length && index < maxLen) {
            if(valArr[index+startpoint] == 0) {
                break;
            }
            index++;
        }
        byte[] temp = new byte[index];
        System.arraycopy(valArr, startpoint, temp, 0, index);
        return new String(temp);
    }
    /**
     * read 4 bytes, transfer to unsigned 4 bytes
     * @param value
     * @return transfer read data to little endian
     */
    private static long Long_EndianBigtoLittle(int value)
    {
        byte[] src = new byte[4];
        src[0] = (byte) ((value>>24)&0xFF);
        src[1] = (byte) ((value>>16)&0xFF);
        src[2] = (byte) ((value>>8)&0xFF);
        src[3] = (byte) ((value)&0xFF);
        return (long) ((src[0]&0xFF)|((src[1]&0xFF)<<8)|((src[2]&0xFF)<<16)|((src[3]&0xFF)<<24))&0xFFFFFFFFl;
    }
    /**
     * Big endian to little endian
     * @param value
     * @return little endian data
     */
    private static float Float_EndianBigtoLittle(int[] value)
    {
        if (value == null || value.length != 4) {
            throw new IllegalArgumentException("value array must be not empty and be 4 bytes");
        }
        int i = ((value[0]&0xFF)|((value[1]&0xFF)<<8)|((value[2]&0xFF)<<16)|((value[3]&0xFF)<<24));
        return Float.intBitsToFloat(i);
    }
    /**
     * Big endian to little endian
     * @param value
     * @return little endian data
     */
    private static int int_EndianBigtoLittle(int value)
    {
        byte[] src = new byte[4];
        src[0] = (byte) ((value>>24)&0xFF);
        src[1] = (byte) ((value>>16)&0xFF);
        src[2] = (byte) ((value>>8)&0xFF);
        src[3] = (byte) ((value)&0xFF);
        return (int) ((src[0]&0xFF)|((src[1]&0xFF)<<8)|((src[2]&0xFF)<<16)|((src[3]&0xFF)<<24));
    }
    /**
     * read bytes data, transfer to unsigned data
     * @param value
     * @return little endian data
     */
    private static int uShort_EndianBigtoLittle(int value)
    {
        byte[] src = new byte[2];
        src[0] = (byte) ((value>>8)&0xFF);
        src[1] = (byte) ((value)&0xFF);
        return (int) ((src[0]&0xFF)|((src[1]&0xFF)<<8));
    }
    /**
     *
     */
    public static int vtolh(byte[] bArr) {
        int n = 0;
        for(int i=0;i<bArr.length&&i<4;i++){
            int left = i*8;
            n+= (bArr[i] << left);
        }
        return n;
    }
}
