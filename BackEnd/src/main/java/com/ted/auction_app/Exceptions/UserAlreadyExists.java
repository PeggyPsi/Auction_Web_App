package com.ted.auction_app.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserAlreadyExists extends RuntimeException{
    public UserAlreadyExists(String exception) {
        super(exception);
    }
}
