/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sd4.service;

import com.sd4.model.Beer;
import com.sd4.repository.BeerRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 *
 * @author mickh
 */
@Service
public class BeerService {
    
     @Autowired
    private BeerRepository beerRepo;

    
    public Optional<Beer> findOne(Long id) {
       
        return beerRepo.findById(id);
    }

    public List<Beer> findAll() {
        return (List<Beer>) beerRepo.findAll();
    }
    
//    public Page<Beer> findAllPages() {
//        return (Page<Beer>) beerRepo.findAll();
//    }
    
    public long count() {
        return beerRepo.count();
    }
    
    public void saveBeer(Beer b) {
        beerRepo.save(b);
    }  


     public Page<Beer> findAlls(int pageNumber,int pageSize) {
         PageRequest page = PageRequest.of(pageNumber, pageSize);
        return beerRepo.findAll(page);
       // return (List<Beer>) beerRepo.findAll();
    }
    
     
    
    public void deleteByID(long id) {
        beerRepo.deleteById(id);
    }
 
// public List<Beer> getByKeyword(String keyword){
//  return beerRepo.findByKeyword(keyword);
// }
 
    
}
