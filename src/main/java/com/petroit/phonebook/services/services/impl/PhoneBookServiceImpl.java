package com.petroit.phonebook.services.services.impl;

import com.petroit.phonebook.domain.Phonebook;
import com.petroit.phonebook.dto.PhonebookRequestDTO;
import com.petroit.phonebook.exceptions.NoSuchRecordExists;
import com.petroit.phonebook.exceptions.RecordAlreadyExists;
import com.petroit.phonebook.exceptions.StorageException;
import com.petroit.phonebook.repository.PhoneBookRepository;
import com.petroit.phonebook.services.PhoneBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;


@Service
public class PhoneBookServiceImpl implements PhoneBookService {


    @Value("${upload.path}")
    private String path;

   private List<String> allowedDocTypes = Arrays.asList("application/octet-stream", "application/pdf", "text/plain");
    private List<String> allowedImageTypes = Arrays.asList("image/png", "image/jpeg", "image/jpg");

    @Autowired
    private PhoneBookRepository phoneBookRepository;

    @Override
    public void uploadFile(MultipartFile file) {

        if (file.isEmpty()) {
           // throw new StorageException("Failed to store empty file");
            System.out.println("Failed to store empty file");
        }

        try {
            String fileName = file.getOriginalFilename();
            InputStream is = file.getInputStream();

            Files.copy(is, Paths.get(path + fileName),
                    StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {

            String msg = String.format("Failed to store file", file.getName());
            e.printStackTrace();
        }

    }

    public List<String> uploadFiles(List<MultipartFile> files) {

        List<String> filesUploaded=new ArrayList<>();
        for(MultipartFile file:files) {
            if(!file.getOriginalFilename().trim().isEmpty()) {
                uploadFile(file);
                filesUploaded.add(file.getOriginalFilename());
            }
        }
        return filesUploaded;
    }

    @Override
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

    @Override
    public boolean removeRecord(String number)
    {
        Phonebook phonebook=phoneBookRepository.findByNumber(number);
        if(Objects.nonNull(phonebook))
        {
            phoneBookRepository.delete(phonebook);
            return true;
        }

        throw new NoSuchRecordExists("No record with number " + number +" exists in the database");
    }

    @Override
    public boolean saveRecord(PhonebookRequestDTO phonebook)
    {

        Phonebook existing=phoneBookRepository.findByNumber(phonebook.getNumber());
        if(Objects.nonNull(existing)){
            throw new RecordAlreadyExists("Record with number " + phonebook.getNumber() + "already exists");
        }
        Phonebook p1=binder(phonebook);
        phoneBookRepository.save(p1);
        return true;
    }

    private Phonebook binder(PhonebookRequestDTO phonebook) {
        Phonebook p1=new Phonebook();
        p1.setNumber(phonebook.getNumber());
        p1.setComments(phonebook.getComments());
        List<MultipartFile> files=phonebook.getDocuments();
        MultipartFile photo=phonebook.getImage();

        if(Objects.nonNull(files))
        {
            List<String> uploadedFiles=uploadFiles(files);
            p1.getDocuments().addAll(uploadedFiles);
        }

        if(Objects.nonNull(photo))
        {
            uploadFile(photo);
            p1.setImage(photo.getOriginalFilename());
        }

        return p1;
    }


    @Override
    public Phonebook getRecord(String number)
    {
        Phonebook phonebook=phoneBookRepository.findByNumber(number);
        if(Objects.nonNull(phonebook))
        {
            String filePath=path+phonebook.getImage();
            phonebook.setImage(filePath);
            return phonebook;
        }
         return null;
    }

    @Override
    public List<Phonebook> getAllRecords()
    {
        List<Phonebook> phonebook=phoneBookRepository.findAll();
         return phonebook;
    }


    @Override
    public boolean updateRecord(PhonebookRequestDTO phoneBook)
    {
        Phonebook book=phoneBookRepository.findByNumber(phoneBook.getNumber());
        if(Objects.nonNull(book))
        {
            List<MultipartFile> files=phoneBook.getDocuments();
            MultipartFile photo=phoneBook.getImage();
            if(Objects.nonNull(files))
            {
                List<String> uploadedFiles=uploadFiles(files);
                book.getDocuments().addAll(uploadedFiles);
             }
            if(Objects.nonNull(photo))
            {
                uploadFile(photo);
                book.setImage(photo.getOriginalFilename());
            }

            book.setComments(phoneBook.getComments()).setUpdatedOn(new Date());
            phoneBookRepository.save(book);
            return true;
        }
        return false;
    }
}
