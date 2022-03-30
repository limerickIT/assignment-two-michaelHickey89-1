/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sd4.controller;

import com.sd4.exceptions.BeerNotFoundException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import javax.servlet.http.HttpServletRequest;
import org.omg.CORBA.portable.UnknownException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author mickh
 */
@ControllerAdvice
public class ExceptionController {
    
    @ExceptionHandler(value =BeerNotFoundException.class)
    public ResponseEntity<String> handleException(RuntimeException ex, WebRequest req){
        String message = "A " + ex + " error has occured ";
        System.out.println(message);
        return new ResponseEntity(message, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
    

    
    
    
}
