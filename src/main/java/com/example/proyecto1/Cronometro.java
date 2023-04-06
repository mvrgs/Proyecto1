package com.example.proyecto1;

public class Cronometro extends Thread{
    static String tiempo;
    public Cronometro(){
        super();
    }
    public void run (){
        int min = 0;
        int seg = 0;
        int hora = 0;
        try {
            for (; ;){
                if (seg != 59){
                    seg++;
                }
                else {
                    if (min != 59){
                        seg = 0;
                        min++;
                    }
                    else {
                        hora++;
                        min = 0;
                        seg = 0;
                    }
                }
                tiempo= hora+":"+min+":"+seg;
                System.out.println(tiempo);
                sleep(999); /**Duerme el hilo casi un segundo para que sea un conteo de tiempo real*/
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
