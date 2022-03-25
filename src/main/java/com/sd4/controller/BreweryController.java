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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
    
    @GetMapping(value = "/map", produces = MediaType.TEXT_HTML_VALUE)
    public String getMap(){
        return "<html><iframe src=\"https://docs.google.com/spreadsheets/d/e/2PACX-1vTNs4h_lJI0kbyje6ter7l4yvbHOUH2BXyjDW4xrwjJqdypJS8cGY96-fwQcSDIvPI0rUiT6A2Ntzph/pubchart?oid=314131014&format=interactive\"width=\"38%\" height=\"475px\"></iframe></html>";
        
    }
    
    
}
