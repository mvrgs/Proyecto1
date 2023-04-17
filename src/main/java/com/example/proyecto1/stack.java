package com.example.proyecto1;

import java.util.LinkedList;

public class stack {
    private LinkedList stackList;

    public void push (Object newElement){
        this.stackList.addFirst(newElement);
    }
    public Object pop(){
        return this.stackList.removeFirst();
    }
}
