package com.example.proyecto1;

public class Casilla {
    private int numFila;
    private int numColumna;
    private boolean mina;
    private int numMinasAlrededor;
    private boolean abierta;

    public Casilla(int numFila, int numColumna) {
        this.numFila = numFila;
        this.numColumna = numColumna;
    }

    public int getNumFila() {
        return numFila;
    }

    public int getNumColumna() {
        return numColumna;
    }

    public boolean isMina() {
        return mina;
    }

    public void setMina(boolean mina) {
        this.mina = mina;
    }

    public int getNumMinasAlrededor() {
        return numMinasAlrededor;
    }

    public void incrementarNumMinasAlrededor() {
        this.numMinasAlrededor++;
    }

    public boolean isAbierta() {
        return abierta;
    }

    public void setAbierta(boolean abierta) {
        this.abierta = abierta;
    }
}