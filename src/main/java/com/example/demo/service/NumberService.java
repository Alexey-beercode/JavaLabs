package com.example.demo.service;

import com.example.demo.model.NumberModel;
import com.example.demo.repository.NumberRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NumberService {

    private final NumberRepository numberRepository;


    public NumberService(NumberRepository numberRepository) {
        this.numberRepository = numberRepository;
    }

    @Transactional
    public void save(NumberModel numberModel){
        numberRepository.save(numberModel);
    }

    @Transactional
    public NumberModel findOne(int id){
        Optional<NumberModel> foundNumber = numberRepository.findById(id);
        return foundNumber.orElse(null);
    }
}
