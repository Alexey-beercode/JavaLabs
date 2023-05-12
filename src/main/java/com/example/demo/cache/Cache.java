package com.example.demo.cache;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Cache<K,V> {

    Logger logger = LogManager.getLogger(Cache.class);
    private Map<K,V> cache = new HashMap<K,V>();

    public boolean contains(K key){
        return cache.containsKey(key);
    }

    public void push(K key,V value){
        if(!cache.containsKey(key)) {
            logger.info("push result to cache");
            cache.put(key, value);
        }
    }

    public V get(K key){
        return cache.get(key);
    }
}
