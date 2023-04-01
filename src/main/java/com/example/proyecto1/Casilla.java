package com.example.proyecto1;

public class Casilla {
    int fila;
    int columna;
    boolean tienemina;

    public Casilla(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public boolean isTienemina() {
        return tienemina;
    }

    public void setTienemina(boolean tienemina) {
        this.tienemina = tienemina;
    }
}
