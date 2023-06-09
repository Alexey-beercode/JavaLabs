package com.example.demo.controllers;

import com.example.demo.async.NumberAsync;
import com.example.demo.cache.Cache;
import com.example.demo.calculations.ModelCalculations;
import com.example.demo.counter.Counter;
import com.example.demo.counter.CounterThread;
import com.example.demo.exception.IllegalArgumetsException;
import com.example.demo.model.CheckModel;
import com.example.demo.model.NumberModel;
import com.example.demo.service.NumberService;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final NumberService numberService;
    private final NumberAsync numberAsync;

    @Autowired
    public NumberController(Cache<Integer,String> cache,
                            CounterThread counterThread,
                            ModelCalculations modelCalculations,
                            NumberService numberService, NumberAsync numberAsync){

        this.cache = cache;
        this.counterThread = counterThread;
        this.modelCalculations = modelCalculations;
        this.numberService = numberService;
        this.numberAsync = numberAsync;
    }

    @GetMapping("/checkNumber")
    public ResponseEntity<?> checkNumber(@RequestParam("number") int number,
                                         @ModelAttribute("numberModel")NumberModel numberModel) throws IllegalArgumetsException, InterruptedException {
        counterThread.run();
        String result ;
        if(!cache.contains(number)){
            result = CheckModel.checkNumber(number);
            cache.push(number,result);
        }else{
            LOGGER.info("get result from cache");
            result = cache.get(number);
        }

        numberModel.setNumber(number);
        numberModel.setResult(result);

        numberService.save(numberModel);
        return new ResponseEntity<>(Counter.getCounter() + ". Result: " + result, HttpStatus.OK);
    }

    @GetMapping("/checkCounter")
    public ResponseEntity<?> checkCounter(){
        return new ResponseEntity<>(Counter.getCounter() , HttpStatus.OK);
    }


    @PostMapping("/checkNumber")
    public ResponseEntity<?> checkNumberWithBulkParameters(@RequestBody List<Integer> listOfNumbers){

        List<String> responseList = listOfNumbers.stream().map(x->{
            try {
                return CheckModel.checkNumber(x);
            } catch (IllegalArgumetsException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).toList();

        int max = modelCalculations.findMax(listOfNumbers);
        int min = modelCalculations.findMin(listOfNumbers);
        double avg = modelCalculations.findAverage(listOfNumbers);

        return new ResponseEntity<>("Result: "  + "\nlist: " + responseList +
                                    "\nmin: " + min + "\nmax: " + max + "\navg: " + avg,HttpStatus.OK);
    }

    @PostMapping("/async")
    public Integer method(@RequestBody NumberModel numberModel){
        int id = numberAsync.createAsync(numberModel);

        numberAsync.computeAsync(id);
        return id;
    }

    @GetMapping("/result/{id}")
    public NumberModel result(@PathVariable("id") int id){
        return numberService.findOne(id);
    }

}
