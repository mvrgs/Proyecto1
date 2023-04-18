package com.example.proyecto1;

import java.util.LinkedList;

public class stack<T> {
    private LinkedList<T> stackList;
    public stack (){
        stackList = new LinkedList<T>();
    }

    public void push (T newElement){
        this.stackList.addFirst(newElement);
    }
    public Object pop(){
        return this.stackList.removeFirst();
    }
    public Object peek() {
        if (isEmpty()) {
            System.out.println("La pila está vacía. No se puede realizar la operación.");
            return null;
        } else {
            return this.stackList.getFirst();
        }
    }

    public boolean isEmpty() {
        return this.stackList.isEmpty();
    }

    public int size() {
        return this.stackList.size();
    }
}
