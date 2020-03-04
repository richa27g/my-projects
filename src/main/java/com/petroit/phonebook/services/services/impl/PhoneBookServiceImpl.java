package com.petroit.phonebook.services.services.impl;

import com.petroit.phonebook.domain.Phonebook;
import com.petroit.phonebook.repository.PhoneBookRepository;
import com.petroit.phonebook.services.PhoneBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.AssertTrue;
import java.io.IOException;
import java.io.InputStream;
import java.io.OptionalDataException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

@Component
public class PhoneBookServiceImpl implements PhoneBookService {


    @Value("${upload.path}")
    private String path;

    @Autowired
    private PhoneBookRepository phoneBookRepository;

    public void uploadFile(MultipartFile file) {

        if (file.isEmpty()) {
            //throw new StorageException("Failed to store empty file");
        }

        try {
            String fileName = file.getOriginalFilename();
            InputStream is = file.getInputStream();

            Files.copy(is, Paths.get(path + fileName),
                    StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {

            String msg = String.format("Failed to store file", file.getName());

            //throw new StorageException(msg, e);
        }

    }

    public ResponseEntity fileDownload(String fileName,String contentType)
    {
        Path filepath = Paths.get(path + fileName);
        Resource resource = null;
        try {
            resource = new UrlResource(filepath.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    public boolean removeRecord(String number)
    {
        Optional<Phonebook> phonebook=phoneBookRepository.findById(number);
        if(phonebook.isPresent())
        {
            phoneBookRepository.delete(phonebook.get());
            return true;
        }
        return false;
    }

    public boolean saveRecord(Phonebook phonebook)
    {
        phoneBookRepository.save(phonebook);
        return true;
    }

    public Phonebook getRecord(String number)
    {
        Optional<Phonebook> phonebook=phoneBookRepository.findById(number);
        if(phonebook.isPresent())
        {
            return phonebook.get();
        }

         return null;
    }



}
