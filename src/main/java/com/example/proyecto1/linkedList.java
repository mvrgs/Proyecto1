package com.example.proyecto1;

public class linkedList {
    private Nodo primero;
    private Nodo ultimo;
    private int largo;

    public linkedList() {
        primero = null;
        ultimo = null;
        largo = 0;
    }

    public boolean esVacia() {
        return primero == null;
    }

    public int getLargo() {
        return largo;
    }

    public void agregar(Casilla casilla) {
        Nodo nuevoNodo = new Nodo(casilla);

        if (esVacia()) {
            primero = nuevoNodo;
            ultimo = nuevoNodo;
        } else {
            ultimo.setSiguiente(nuevoNodo);
            ultimo = nuevoNodo;
        }

        largo++;
    }

    public Nodo getPrimero() {
        return primero;
    }

    public Nodo getNodoEnIndice(int indice) {
        if (indice < 0 || indice >= largo) {
            return null;
        }

        Nodo nodoActual = primero;
        for (int i = 0; i < indice; i++) {
            nodoActual = nodoActual.getSiguiente();
        }

        return nodoActual;
    }
    public void eliminarPrimero() {
        if (esVacia()) {
            return;
        }

        if (largo == 1) {
            primero = null;
            ultimo = null;
        } else {
            primero = primero.getSiguiente();
        }

        largo--;
    }
}
