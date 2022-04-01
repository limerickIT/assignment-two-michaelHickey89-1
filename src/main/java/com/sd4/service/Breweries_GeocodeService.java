/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sd4.service;

import com.sd4.model.Breweries_Geocode;
import com.sd4.model.Brewery;
import com.sd4.repository.Breweries_GeocodeRepository;
import com.sd4.repository.BreweryRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author mickh
 */
@Service
public class Breweries_GeocodeService {
    
         @Autowired
    private Breweries_GeocodeRepository breweryGeoRepo;

 
   public Optional<Breweries_Geocode> findOne(Long id) {
        return breweryGeoRepo.findById(id);
    }

    public List<Breweries_Geocode> findAll() {
        return (List<Breweries_Geocode>) breweryGeoRepo.findAll();
    }
    
}
