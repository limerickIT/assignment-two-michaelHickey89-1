/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sd4.controller;

import com.sd4.model.Beer;
import com.sd4.model.Brewery;
import com.sd4.service.BreweryService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author mickh
 */

@RestController
@RequestMapping("/brewery")
public class BreweryController {
    
     @Autowired
    private BreweryService breweryService;
      
       @GetMapping("")
    public ResponseEntity<List<Brewery>> getAll() {
        List<Brewery> alist = breweryService.findAll();
        
        if(alist.isEmpty()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        else
            return ResponseEntity.ok(alist);
    }
      
       @GetMapping(value = "/{id}")
    public ResponseEntity<Brewery> getOne(@PathVariable long id) {
       Optional<Brewery> o =  breweryService.findOne(id);
       
       if (!o.isPresent()) 
            return new ResponseEntity(HttpStatus.NOT_FOUND);
         else 
            return ResponseEntity.ok(o.get());
    }
    
    
   
    
    
}
