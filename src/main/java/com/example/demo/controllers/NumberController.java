package com.example.demo.controllers;

import com.example.demo.cache.Cache;
import com.example.demo.calculations.ModelCalculations;
import com.example.demo.counter.Counter;
import com.example.demo.counter.CounterThread;
import com.example.demo.exception.IllegalArgumetsException;
import com.example.demo.model.CheckModel;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NumberController {
    private static final Logger LOGGER = LogManager.getLogger(NumberController.class);
    private final Cache<Integer,String> cache;
    private final CounterThread counterThread;
    private final ModelCalculations modelCalculations;

    @Autowired
    public NumberController(Cache<Integer,String> cache, CounterThread counterThread, ModelCalculations modelCalculations){
        this.cache = cache;
        this.counterThread = counterThread;
        this.modelCalculations = modelCalculations;
    }

    @GetMapping("/checkNumber")
    public ResponseEntity<?> checkNumber(@RequestParam("number") int number) throws IllegalArgumetsException{
        counterThread.run();
        String result ;

        if(!cache.contains(number)){
            result = CheckModel.checkNumber(number);
            cache.push(number,result);
        }else{
            LOGGER.info("get result from cache  ");
            result = cache.get(number);
        }

        return new ResponseEntity<>(Counter.getCounter() + ". Result: " + result, HttpStatus.OK);
    }

    @PostMapping("/checkNumber")
    public ResponseEntity<?> checkNumberWithBulkParameters(@RequestBody List<Integer> listOfNumbers){

        List<String> responseList = listOfNumbers.stream().map(x->{
            try {
                return CheckModel.checkNumber(x);
            } catch (IllegalArgumetsException e) {
                throw new RuntimeException(e);
            }
        }).toList();

        int max = modelCalculations.findMax(listOfNumbers);
        int min = modelCalculations.findMin(listOfNumbers);
        double avg = modelCalculations.findAverage(listOfNumbers);

        return new ResponseEntity<>("Result: "  + "\nlist: " + responseList +
                                    "\nmin: " + min + "\nmax: " + max + "\navg: " + avg,HttpStatus.OK);
    }

}
