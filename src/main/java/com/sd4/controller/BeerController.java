/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sd4.controller;

import com.sd4.model.Beer;
import com.sd4.service.BeerService;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import javax.imageio.ImageIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
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
import org.springframework.http.MediaType;
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

    @GetMapping("")
    Page<Beer> getAll(@RequestParam int pageSize, @RequestParam int pageNumber) {
        Page<Beer> alist = beerService.findAlls(pageNumber, pageSize);

//        if(alist.isEmpty()){
//            return new ResponseEntity(HttpStatus.NOT_FOUND);
//        }
//        else
        return beerService.findAlls(pageNumber, pageSize);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Beer> getOne(@PathVariable("id") long id) {
        Optional<Beer> optional = beerService.findOne(id);

        if (!optional.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(optional.get());
        }
    }

    @GetMapping(value = "/image/{id}/{imageType}")
    /**
     * imageType must = thumb or large
     */
    public ResponseEntity<BufferedImage> getImage(@PathVariable("id") long id, @PathVariable("imageType") String imageType) throws IOException {
        Optional<Beer> optional = beerService.findOne(id);

        if (!optional.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        String type = "large".equalsIgnoreCase(imageType) ? "large" : "thumbs";
        
        String path = "static/assets/images/" + type + "/" + optional.get().getImage();
        System.out.println(path);
        InputStream inputStream = new ClassPathResource(path).getInputStream();
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        return ResponseEntity.ok(bufferedImage);

    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity edit(@PathVariable("id") long id, @RequestBody Beer beer) { //the edit method should check if the Author object is already in the DB before attempting to save it.
        if (id != beer.getId()) {
            return ResponseEntity.badRequest().build();
        }
        beer = beerService.saveBeer(beer);
        return ResponseEntity.ok(beer);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Beer> add(@RequestBody Beer newBeer) {
        Beer beer = beerService.saveBeer(newBeer);
        return ResponseEntity.ok(beer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        Optional<Beer> optional = beerService.findOne(id);

        if (!optional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        beerService.deleteByID(id);
        return ResponseEntity.ok(optional.get());
    }

    @GetMapping(value = "/getBeer/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Beer> getBeerWithHateoas(@PathVariable long id) {
        Optional<Beer> b = beerService.findOne(id);
        if (!b.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {

            Link link = linkTo(methodOn(BeerController.class).getAllBeerWithHateoas()).withSelfRel();

            b.get().add(link);
            return ResponseEntity.ok(b.get());
        }
    }

    @GetMapping(value = "/allbeers/", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<Beer> getAllBeerWithHateoas() {
        List<Beer> alist = beerService.findAll();

        for (final Beer b : alist) {
            long id = b.getId();

            Link selfLink = linkTo(methodOn(BeerController.class).getBeerWithHateoas(id)).withSelfRel();

            b.add(selfLink);

            Link moreDetails = linkTo(methodOn(BeerController.class).getBeerWithHateoas(id)).withSelfRel();

            b.add(moreDetails);
        }
        Link listLink = linkTo(methodOn(BeerController.class).getAllBeerWithHateoas()).withSelfRel();
        CollectionModel<Beer> result = CollectionModel.of(alist, listLink);
        return result;

    }

}
