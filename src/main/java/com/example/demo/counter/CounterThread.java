package com.example.demo.counter;

import org.springframework.stereotype.Component;

@Component
public class CounterThread implements Runnable{

    public void run(){
        try{
            Counter.increment();
            Thread.sleep(100);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
