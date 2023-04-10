package com.example.proyecto1;


import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortList;

public class serialComm {
    public static void main(String[] args) {
        String puertos[] = SerialPortList.getPortNames();
        for (String n : puertos) {
            System.out.println(n);
        }
        SerialPort sp = new SerialPort("COM14");
        try {
            sp.openPort();
            sp.setParams(SerialPort.BAUDRATE_115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            sp.addEventListener(new LecturaSerial(sp),SerialPort.MASK_RXCHAR);
            Thread.sleep(1500);
            while (true) {
                System.out.println("Enviando");
                sp.writeString("prueba");
                Thread.sleep(5000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
class LecturaSerial implements SerialPortEventListener{
    SerialPort sp;
    public LecturaSerial(SerialPort sp){
        this.sp = sp;
    }

    @Override
    public void serialEvent(SerialPortEvent spe) {
        try {
            String msg = sp.readString(6);
            System.out.println("Recibiendo");
            System.out.println(msg);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
