package com.example.demo.controllers;

import com.example.demo.cache.Cache;
import com.example.demo.counter.Counter;
import com.example.demo.counter.CounterThread;
import com.example.demo.exception.IllegalArgumetsException;
import com.example.demo.model.CheckModel;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NumberController {
    private static final Logger LOGGER = LogManager.getLogger(NumberController.class);
    private final Cache<Integer,String> cache;
    private final CounterThread counterThread;

    @Autowired
    public NumberController(Cache<Integer,String> cache, CounterThread counterThread){
        this.cache = cache;
        this.counterThread = counterThread;
    }

    @GetMapping("/checkNumber")
    public String checkNumber(@RequestParam("number") int number) throws IllegalArgumetsException, JSONException {
        counterThread.run();
        String result ;

        if(!cache.contains(number)){
            result = CheckModel.checkNumber(number);
            cache.push(number,result);
        }else{
            LOGGER.info("get");
            result = cache.get(number);
        }

        return "result\t"+Counter.getCounter()+result.toString();
        //JSONObject jsonObject = new JSONObject();
        //return  jsonObject.put("result:"+ Counter.getCounter(), result).toString();
    }
}
