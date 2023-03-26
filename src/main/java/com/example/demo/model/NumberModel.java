package com.example.demo.model;

import com.example.demo.controllers.MyController;
import com.example.demo.exception.IllegalArgumetsException;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@Component
public class NumberModel {
    private int number;


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void  isNegativeNumber() throws IllegalArgumetsException {
        if(number<0)
            throw new IllegalArgumetsException("Negative number");
    }


    public String checkNumber() {
        Logger logger = LogManager.getLogger(MyController.class);
        logger.info("number checking");
        if(number==1)
            return "Не простое и не четное";
        BigInteger bigInteger = BigInteger.valueOf(number);
        boolean isPrime = bigInteger.isProbablePrime((int) Math.log(number));
        boolean isEven = number % 2 == 0;
        if (isPrime && isEven) {
            return "Четное и простое";
        } else if (isPrime && !isEven) {
            return "Не четное и простое";
        } else if (!isPrime && isEven) {
            return "Четное и не простое";
        }
        return "Не четное и не простое";
    }
}
