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
    
    @GetMapping(value = "/{id}/map")
    public String getMap(@PathVariable long id){
        
       Optional<Brewery> o =  breweryService.findOne(id);
       String name = o.get().getName();
       String address = o.get().getAddress1();
       String address2 = o.get().getAddress2();
       String city = o.get().getCity();
       String country = o.get().getCountry();
       String code = o.get().getCode();
        
        return "<html><body><h2>" + name + address + address2 + city +"</h2><iframe width=\"600\" height=\"500\" id=\"gmap_canvas\" src=\"https://maps.google.com/maps?q="+ name+address+address2+city+code+country+ "=&output=embed\" frameborder=\"0\" scrolling=\"no\" marginheight=\"0\" marginwidth=\"0\">";
     }
    
    
}
