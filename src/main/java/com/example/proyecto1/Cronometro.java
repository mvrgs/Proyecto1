package com.example.proyecto1;

public class Cronometro extends Thread{
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
                System.out.println(hora+":"+min+":"+seg);
                sleep(999);
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
