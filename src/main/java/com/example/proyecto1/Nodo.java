package com.example.proyecto1;

public class Nodo {
    private Casilla casilla;
    private Nodo siguiente;

    public Nodo(Casilla casilla) {
        this.casilla = casilla;
        this.siguiente = null;
    }

    public Casilla getCasilla() {
        return casilla;
    }

    public Nodo getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }
}
