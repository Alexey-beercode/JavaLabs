package com.example.demo.model;

import com.example.demo.controllers.NumberController;
import com.example.demo.exception.IllegalArgumetsException;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@Component
public class CheckModel {

    private static Logger LOGGER = LogManager.getLogger(NumberController.class);


    static public String checkNumber(int number) throws IllegalArgumetsException {

        LOGGER.info("number checking");
        if(number<0)
            throw new IllegalArgumetsException("Negative number");
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
