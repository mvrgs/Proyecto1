package com.example.proyecto1;
import com.fazecast.jSerialComm.SerialPort;

//PRUEBA DE CONEXION
public class SerialComm  {
    public static void main(String[] args) {
        // Obtener la lista de puertos seriales disponibles
        SerialPort[] puertos = SerialPort.getCommPorts();


        // Abrir el puerto serial deseado (reemplazar "COM3" con el nombre del puerto serial de tu Arduino)
        SerialPort puerto = SerialPort.getCommPort("COM14");
        puerto.setBaudRate(9600); // Establecer la velocidad de transmisión del puerto serial
        puerto.openPort(); // Abrir el puerto serial

        // Enviar texto al Arduino
        String texto = "Hola, Arduino!";
        puerto.writeBytes(texto.getBytes(), texto.length());

        // Cerrar el puerto serial
        puerto.closePort();
    }
}