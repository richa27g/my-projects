package com.petroit.phonebook.services;


import com.petroit.phonebook.domain.Phonebook;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface PhoneBookService {

    void uploadFile(MultipartFile file);
    ResponseEntity fileDownload(String fileName,String contentType);
    boolean removeRecord(String number);
    Phonebook getRecord(String number);

}
