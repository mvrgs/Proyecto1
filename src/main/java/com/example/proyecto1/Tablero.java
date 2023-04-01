package com.example.proyecto1;

public class Tablero {
    Casilla[][] casillas;
    int numFilas;
    int numColumnas;
    int numMinas;

    public Tablero(int numFilas, int numColumnas, int numMinas) {
        this.numFilas = numFilas;
        this.numColumnas = numColumnas;
        this.numMinas = numMinas;
        iniciarCasillas();
    }

    public void iniciarCasillas(){
        casillas = new Casilla[this.numFilas][this.numColumnas];
        for (int i = 0; i < casillas.length; i++){
            for (int j = 0 ; j < casillas[i].length; j++){
                casillas[i][j] = new Casilla(i, j);
            }
        }
        generarMinas();
    }

    private void generarMinas(){
        int minasGeneradas = 0;
        while (minasGeneradas != numMinas){
            int tmpFila = (int) (Math.random()*casillas.length);
            int tmpColumna = (int) (Math.random()* casillas[0].length);

            if (!casillas[tmpFila][tmpColumna].isTienemina()){
                casillas[tmpFila][tmpColumna].setTienemina(true);
                minasGeneradas++;
            }
        }
    }
    private void printTablero(){
        for (int i = 0; i < casillas.length; i++){
            for (int j = 0 ; j < casillas[i].length; j++){
                System.out.print(casillas[i][j].isTienemina()?"*":"0");
            }
            System.out.println("");
        }

    }
    public static void main(String[] args){
        Tablero tablero = new Tablero(6, 6, 5);
        tablero.printTablero();
    }
}
