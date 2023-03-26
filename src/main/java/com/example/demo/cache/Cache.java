package com.example.demo.cache;

import com.example.demo.controllers.MyController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Cache<K,V> {

    private Map<K,V> cache = new HashMap<K,V>();

    public boolean contains(K key){
        return cache.containsKey(key);
    }

    public void push(K key,V value){
        Logger logger = LogManager.getLogger(MyController.class);
        if(!cache.containsKey(key)) {
            logger.info("push");
            cache.put(key, value);
        }
    }

    public V get(K key){
        return cache.get(key);
    }
}
