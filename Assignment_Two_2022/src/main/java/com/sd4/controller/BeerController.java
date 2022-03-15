/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sd4.controller;

import com.sd4.model.Beer;
import com.sd4.service.BeerService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;



/**
 *
 * @author mickh
 */
@RestController
@RequestMapping("/beer")

public class BeerController {
    
      @Autowired
    private BeerService beerService;
      
       @GetMapping("/")
    Page<Beer> getAll(@RequestParam int pageSize,@RequestParam int pageNumber) {
        Page<Beer> alist = beerService.findAlls(pageNumber,pageSize);
        
//        if(alist.isEmpty()){
//            return new ResponseEntity(HttpStatus.NOT_FOUND);
//        }
//        else
            return beerService.findAlls(pageNumber, pageSize);
    }
      
       @GetMapping(value = "/{id}")
    public ResponseEntity<Beer> getOne(@PathVariable long id) {
       Optional<Beer> o =  beerService.findOne(id);
       
       if (!o.isPresent()) 
            return new ResponseEntity(HttpStatus.NOT_FOUND);
         else 
            return ResponseEntity.ok(o.get());
    }
      
      @PutMapping("/{id}")
    public ResponseEntity edit(@RequestBody Beer b) { //the edit method should check if the Author object is already in the DB before attempting to save it.
        beerService.saveBeer(b);
        return new ResponseEntity(HttpStatus.OK);
    }
    
    @PostMapping("/add")
    public ResponseEntity add(@RequestBody Beer a) {
        beerService.saveBeer(a);
        return new ResponseEntity(HttpStatus.CREATED);
    }
    
     @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        beerService.deleteByID(id);
        return new ResponseEntity(HttpStatus.OK);
    }
    
 
      
    @GetMapping(value ="/hateoas/{id}", produces=MediaTypes.HAL_JSON_VALUE)  
    public ResponseEntity<Beer> getBeerWithHateoas(@PathVariable long id){
        Optional<Beer> b = beerService.findOne(id);
        if (!b.isPresent()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        else{
               
                
                Link link = linkTo(methodOn(BeerController.class).getAllBeerWithHateoas()).withSelfRel();
                
          
               b.get().add(link);
               return ResponseEntity.ok(b.get());
        }
    }  
    
    @GetMapping(value ="/hateoas/", produces=MediaTypes.HAL_JSON_VALUE)  
    public CollectionModel<Beer> getAllBeerWithHateoas(){
      List<Beer> alist = beerService.findAll();
      
      for(final Beer b : alist){
          long id = b.getId();
          Link selfLink = linkTo(methodOn(BeerController.class).getOne(id)).withSelfRel();
          Link link = linkTo(methodOn(BeerController.class).getBeerWithHateoas(id)).withSelfRel();
          b.add(selfLink);
          b.add(link);
      }
          
           CollectionModel<Beer> result = CollectionModel.of(alist);
           return result;
     
    }
    
   
    
     
    
    

   

   
    
}
