/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sd4.controller;

import com.sd4.model.Beer;
import com.sd4.model.Brewery;
import com.sd4.service.BreweryService;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;
import javax.imageio.ImageIO;
import net.glxn.qrgen.core.vcard.VCard;
import net.glxn.qrgen.javase.QRCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

        if (alist.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(alist);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Brewery> getOne(@PathVariable long id) {
        
        Optional<Brewery> o = breweryService.findOne(id);

        if (!o.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(o.get());
        }
    }
    
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity edit(@PathVariable("id") long id, @RequestBody Brewery brewery) { //the edit method should check if the Author object is already in the DB before attempting to save it.
        if (id != brewery.getId()) {
            return ResponseEntity.badRequest().build();
        }
        brewery = breweryService.saveBrewery(brewery);
        return ResponseEntity.ok(brewery);
    }

     @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Brewery> add(@RequestBody Brewery newBrewery) {
        Brewery b = breweryService.saveBrewery(newBrewery);
        return ResponseEntity.ok(b);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        Optional<Brewery> optional = breweryService.findOne(id);

        if (!optional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        breweryService.deleteByID(id);
        return ResponseEntity.ok(optional.get());
    }

    @GetMapping(value = "/{id}/map")
    public String getMap(@PathVariable long id) {

        Optional<Brewery> o = breweryService.findOne(id);
        String name = o.get().getName();
        String address = o.get().getAddress1();
        String address2 = o.get().getAddress2();
        String city = o.get().getCity();
        String country = o.get().getCountry();
        String code = o.get().getCode();

        return "<html><body><h2>" + name + address + address2 + city + "</h2><iframe width=\"600\" height=\"500\" id=\"gmap_canvas\" src=\"https://maps.google.com/maps?q=" + name + address + address2 + city + code + country + "=&output=embed\" frameborder=\"0\" scrolling=\"no\" marginheight=\"0\" marginwidth=\"0\">";
    }

    public static VCard getVCard(Brewery brewery) {
        VCard vCard = new VCard();
        vCard.setName(brewery.getName());
        vCard.setAddress(brewery.getAddress1());
        vCard.setAddress(brewery.getAddress2());
        vCard.setPhoneNumber(brewery.getPhone());
        vCard.setEmail(brewery.getEmail());
        vCard.setWebsite(brewery.getWebsite());
        return vCard;
    }

    @GetMapping(value = "/qrcode/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> generateQRCode(@PathVariable("id") long breweryId) throws Exception {
        Optional<Brewery> optional = breweryService.findOne(breweryId);
        if (!optional.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
         Brewery brewery = optional.get();
         VCard vCard = getVCard(brewery);
        try (ByteArrayOutputStream stream = QRCode.from(vCard.toString()).withSize(250, 250).stream()) {
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(stream.toByteArray()));
            return ResponseEntity.ok(bufferedImage);
        }
    }

}
