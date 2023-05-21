package com.example.demo.counter;

public class Counter {
    private static int COUNTER = 0;

    public static int getCounter(){
        return COUNTER;
    }
    public static void increment() { ++COUNTER; }

}
