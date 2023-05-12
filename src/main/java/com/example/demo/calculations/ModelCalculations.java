package com.example.demo.calculations;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ModelCalculations {

    public int findMax(List<Integer> listOfNumbers){
        int max = 0;

        if(!listOfNumbers.isEmpty()){
            max = listOfNumbers.stream().max(Integer::compareTo).get();
        }
        return max;
    }

    public int findMin(List<Integer> listOfNumbers){
        int min = 0;

        if(!listOfNumbers.isEmpty()){
            min = listOfNumbers.stream().min(Integer::compareTo).get();
        }
        return min;
    }

    public double findAverage(List<Integer> listOfNumbers){
        double avg = 0;

        if(!listOfNumbers.isEmpty()){
        avg = listOfNumbers.stream().mapToDouble(Integer::intValue).average().getAsDouble();
        }
        return avg;
    }
}
