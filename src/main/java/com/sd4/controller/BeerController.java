package com.sd4.controller;

import com.sd4.model.Beer;
import com.sd4.model.Brewery;
import com.sd4.model.Category;
import com.sd4.model.Style;
import com.sd4.repository.CategoryRepository;
import com.sd4.repository.StyleRepository;
import com.sd4.service.BeerService;
import com.sd4.service.BreweryService;
import com.sd4.Builder.BuildPDF;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import javax.imageio.ImageIO;
import net.minidev.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
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
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.HttpHeaders;
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

    @Autowired
    private BreweryService breweryService;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private StyleRepository styleRepository;


    @GetMapping(value = "/{id}")
    public ResponseEntity<Beer> getOne(@PathVariable("id") long id) {
        Optional<Beer> optional = beerService.findOne(id);

        if (!optional.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(optional.get());
        }
    }

    @GetMapping(value = "/details/{id}")
    public ResponseEntity<JSONObject> getBeerDetailsById(@PathVariable("id") long id) {

        Optional<Beer> optional = beerService.findOne(id);

        if (!optional.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("beerName", optional.get().getName());
            jsonObject.put("beerDescription", optional.get().getDescription());
            jsonObject.put("breweryName", breweryService.findOne(optional.get().getBrewery_id()).get().getName());

            return ResponseEntity.ok(jsonObject);
        }
    }

    @GetMapping(value = "/image/{id}/{imageType}")
    public ResponseEntity<BufferedImage> getImage(@PathVariable("id") long id, @PathVariable(value = "imageType", required = false) String imageType) throws IOException {
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

            Link moreDetails = linkTo(methodOn(BeerController.class).getBeerDetailsById(id)).withRel("moreDetails");

            b.add(moreDetails);
        }
        Link listLink = linkTo(methodOn(BeerController.class).getAllBeerWithHateoas()).withSelfRel();
        CollectionModel<Beer> result = CollectionModel.of(alist, listLink);
        return result;

    }

    @GetMapping(value = "/zip", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> getZippedBeerImages() throws IOException {
        final List<Beer> beers = beerService.findAll();
        final File zipFile = BeerService.zipBeerImages(beers);
        try (final InputStream inputStream = new FileInputStream(zipFile)) {
            final HttpHeaders responseHeaders = new HttpHeaders();
            final String filename = "beer-images.zip";
            responseHeaders.set("Content-Disposition", "attachment; filename=\"" + filename + "\"");

            return new ResponseEntity(IOUtils.toByteArray(inputStream), responseHeaders, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/pdf/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<BuildPDF> getPdf(@PathVariable("id") long id) throws Exception {
        Optional<Beer> optional = beerService.findOne(id);
        if (!optional.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        

        Optional<Brewery> brewery = breweryService.findOne(optional.get().getBrewery_id());
        Optional<Category> category = categoryRepository.findById(optional.get().getCat_id());
        Optional<Style> style = styleRepository.findById(optional.get().getStyle_id());

        BuildPDF beerPdfPrinter = new BuildPDF(optional.get(), brewery.get(), category.get(), style.get());

        File pdfFile = beerPdfPrinter.generatePdfReport();
        try (InputStream inputStream = new FileInputStream(pdfFile)) {
            HttpHeaders responseHeaders = new HttpHeaders();
            String filename = optional.get().getName() + ".pdf";
            responseHeaders.set("Content-Disposition", "attachment; filename=\"" + filename + "\"");

            return new ResponseEntity(IOUtils.toByteArray(inputStream), responseHeaders, HttpStatus.OK);
        }
    }
   
}
