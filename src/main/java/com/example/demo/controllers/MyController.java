package com.example.demo.controllers;

import com.example.demo.cache.Cache;
import com.example.demo.counter.CounterThread;
import com.example.demo.exception.IllegalArgumetsException;
import com.example.demo.model.NumberModel;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
    private static final Logger logger = LogManager.getLogger(MyController.class);
    private final Cache<Integer,String> cache;
    private final NumberModel numberModel;
    private final CounterThread counterThread;

    @Autowired
    public MyController(Cache<Integer,String> cache, NumberModel numberModel, CounterThread counterThread){
        this.cache = cache;
        this.numberModel = numberModel;
        this.counterThread = counterThread;
    }

    @GetMapping("/checkNumber")
    public String checkNumber(@RequestParam("number") int number) throws IllegalArgumetsException, JSONException {
        counterThread.run();
        String result ;
        numberModel.setNumber(number);
        numberModel.isNegativeNumber();

        if(!cache.contains(number)){
            result = numberModel.checkNumber();
        }else{
            logger.info("get");
            result = cache.get(number);
        }


        cache.push(number,result);

        JSONObject jsonObject = new JSONObject();
        return  jsonObject.put("result:", result).toString();
    }
}
