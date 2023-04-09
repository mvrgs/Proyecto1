package com.example.proyecto1;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import static com.example.proyecto1.GameController.actualizarMinasMarcadas;

public class Tablero {
        static Casilla[][] casillas;
        int numFilas;
        int numColumnas;
        int numMinas;
        int numCasillasAbiertas;
        int minasEncontradas = 0;
        static boolean juegoTerminado = false;
        private Consumer<List<Casilla>> eventLoseGame;
        private Consumer<Casilla> eventCasillaAbierta;
        private Consumer<List<Casilla>> eventWinGame;

        public Tablero(int numFilas, int numColumnas, int numMinas) {
            this.numFilas = numFilas;
            this.numColumnas = numColumnas;
            this.numMinas = numMinas;
            this.iniciarCasillas();
        }

        public void iniciarCasillas() {
            casillas = new Casilla[this.numFilas][this.numColumnas];
            for (int i = 0; i < casillas.length; i++) {
                for (int j = 0; j < casillas[i].length; j++) {
                    casillas[i][j] = new Casilla(i, j);
                }
            }
            generarMinas();
        }

        private void generarMinas() {
            int minasGeneradas = 0;
            while (minasGeneradas != numMinas) {
                int tmpFila = (int) (Math.random() * casillas.length);
                int tmpColumna = (int) (Math.random() * casillas[0].length);
                if (!casillas[tmpFila][tmpColumna].isMina()) {
                    casillas[tmpFila][tmpColumna].setMina(true);
                    minasGeneradas++;
                }
            }
            actualizarNumMinasAlrededor();
        }

        public void printTablero() {
            for (int i = 0; i < casillas.length; i++) {
                for (int j = 0; j < casillas[i].length; j++) {
                    System.out.print(casillas[i][j].isMina() ? "*" : "0");
                }
                System.out.println("");
            }
        }

        private void printPistas() {
            for (int i = 0; i < casillas.length; i++) {
                for (int j = 0; j < casillas[i].length; j++) {
                    System.out.print(casillas[i][j].getNumMinasAlrededor());
                }
                System.out.println("");
            }
        }

        private void actualizarNumMinasAlrededor() {
            for (int i = 0; i < casillas.length; i++) {
                for (int j = 0; j < casillas[i].length; j++) {
                    if (casillas[i][j].isMina()) {
                        List<Casilla> casillasAlrededor = obtenerCasillasAlrededor(i, j);
                        casillasAlrededor.forEach((c) -> c.incrementarNumMinasAlrededor());
                    }
                }
            }
        }

        private List<Casilla> obtenerCasillasAlrededor(int numFila, int numColumna) {
            List<Casilla> listaCasillas = new LinkedList<>();
            for (int i = 0; i < 8; i++) {
                int Filatmp = numFila;
                int Columnatmp = numColumna;
                switch (i) {
                    case 0:
                        Filatmp--;
                        break; //Arriba
                    case 1:
                        Filatmp--;
                        Columnatmp++;
                        break; //Arriba Derecha
                    case 2:
                        Columnatmp++;
                        break; //Derecha
                    case 3:
                        Columnatmp++;
                        Filatmp++;
                        break; //Derecha Abajo
                    case 4:
                        Filatmp++;
                        break; //Abajo
                    case 5:
                        Filatmp++;
                        Columnatmp--;
                        break; //Abajo Izquierda
                    case 6:
                        Columnatmp--;
                        break; //Izquierda
                    case 7:
                        Filatmp--;
                        Columnatmp--;
                        break; //Izquierda Arriba
                }

                if (Filatmp >= 0 && Filatmp < this.casillas.length
                        && Columnatmp >= 0 && Columnatmp < this.casillas[0].length) {
                    listaCasillas.add(this.casillas[Filatmp][Columnatmp]);
                }

            }
            return listaCasillas;
        }

        List<Casilla> obtenerCasillasConMinas(){
            List<Casilla> casillasConMinas = new LinkedList<>();
            for (int i = 0; i < casillas.length; i++) {
                for (int j = 0; j < casillas[i].length; j++) {
                    if (casillas[i][j].isMina()) {
                        casillasConMinas.add(casillas[i][j]);
                    }
                }
            }
            return casillasConMinas;
        }
        public void selectCasilla(int posFila, int posColumna){
            eventCasillaAbierta.accept(this.casillas[posFila][posColumna]);
            if (this.casillas[posFila][posColumna].isMina()){
                eventLoseGame.accept(obtenerCasillasConMinas());
                juegoTerminado = true;
            } else if (this.casillas[posFila][posColumna].getNumMinasAlrededor()==0){
                marcarCasillaAbierta(posFila, posColumna);
                List<Casilla> casillasAlrededor = obtenerCasillasAlrededor(posFila, posColumna);
                for (Casilla casilla: casillasAlrededor){
                    if (!casilla.isAbierta()){
                        selectCasilla(casilla.getNumFila(),casilla.getNumColumna());
                    }
                }
            }
            else {
                marcarCasillaAbierta(posFila, posColumna);
            }
            if (partidaGanada()){
                eventWinGame.accept(obtenerCasillasConMinas());

            }
        }

        public void marcarCasilla(int posFila, int posColumna){
            if (this.casillas[posFila][posColumna].isMina()){
               minasEncontradas++;
               System.out.println(minasEncontradas);
               actualizarMinasMarcadas(minasEncontradas);
            }
        }





    boolean partidaGanada(){return numCasillasAbiertas >= (numFilas*numColumnas)-numMinas;
    }

    void marcarCasillaAbierta(int posFila, int posColumna){
            if (!this.casillas[posFila][posColumna].isAbierta()){
                numCasillasAbiertas++;
                this.casillas[posFila][posColumna].setAbierta(true);
            }
    }

    public static void main(String[] args) {
            Tablero tablero = new Tablero(8, 8, 5);
            tablero.printTablero();
            System.out.println("---");
            tablero.printPistas();
        }

    public void setEventLoseGame(Consumer<List<Casilla>> eventLoseGame) {
        this.eventLoseGame = eventLoseGame;
    }

    public void setEventCasillaAbierta(Consumer<Casilla> eventCasillaAbierta) {
        this.eventCasillaAbierta = eventCasillaAbierta;
    }

    public void setEventWinGame(Consumer<List<Casilla>> eventWinGame) {
        this.eventWinGame = eventWinGame;
    }
}
