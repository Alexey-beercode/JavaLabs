package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST,reason = "bad arguments")
public class IllegalArgumetsException extends Exception{

    public IllegalArgumetsException(String message){
        super(message);
    }

    public String getMessage()
    {
        return super.getMessage();
    }

}
