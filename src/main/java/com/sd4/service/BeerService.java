package com.sd4.service;

import com.sd4.model.Beer;
import com.sd4.repository.BeerRepository;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public long count() {
        return beerRepo.count();
    }

    public Beer saveBeer(Beer b) {
        b.setLast_mod(new Date());
        return beerRepo.save(b);
    }

    public Page<Beer> findAlls(int pageNumber, int pageSize) {
        PageRequest page = PageRequest.of(pageNumber, pageSize);
        return beerRepo.findAll(page);
        // return (List<Beer>) beerRepo.findAll();
    }

    public Page<Beer> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        return beerRepo.findAll(pageable);
    }

    public void deleteByID(long id) {
        beerRepo.deleteById(id);
    }

    public static File zipBeerImages(List<Beer> beers) throws IOException {
        final String[] imageFilenames = beers.stream()
                .map(Beer::getImage)
                .distinct().toArray(String[]::new);
           

        final File zipFile = File.createTempFile("result", ".zip");
        try (final FileOutputStream fileOutputStream = new FileOutputStream(zipFile); final ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream)) {
            for (String imageFilename : imageFilenames) {
                final String path = "static/assets/images/large/" + imageFilename;
                final ClassPathResource resource = new ClassPathResource(path);
                addFileToZipStream(resource.getFile(), zipOutputStream);
            }
        }
        return zipFile;
    }

    private static void addFileToZipStream(final File file, final ZipOutputStream zipOutputStream) throws IOException {
        final int BUFFER_SIZE = 1024;

        try (final FileInputStream fileInputStream = new FileInputStream(file); final BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream, BUFFER_SIZE)) {
            final ZipEntry zipEntry = new ZipEntry(file.getName());
            zipOutputStream.putNextEntry(zipEntry);
            byte data[] = new byte[BUFFER_SIZE];
            int count;
            while ((count = bufferedInputStream.read(data, 0, BUFFER_SIZE)) != -1) {
                zipOutputStream.write(data, 0, count);
            }
            zipOutputStream.closeEntry();
        }
    }
}
