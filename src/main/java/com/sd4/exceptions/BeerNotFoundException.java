/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sd4.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author mickh
 */

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason = "Bad request")
public class BeerNotFoundException extends RuntimeException{
    
    public BeerNotFoundException(String message){
       super(message);
    }
    
    public BeerNotFoundException(String message, Throwable cause){
        super(message,cause);
    }
    

    
}
