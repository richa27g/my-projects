package com.petroit.phonebook.services;


import com.petroit.phonebook.domain.Phonebook;
import com.petroit.phonebook.dto.PhonebookRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PhoneBookService {

    void uploadFile(MultipartFile file);
    ResponseEntity fileDownload(String fileName,String contentType);
    boolean removeRecord(String number);
    Phonebook getRecord(String number);
    boolean saveRecord(PhonebookRequestDTO phonebook);
    boolean updateRecord(PhonebookRequestDTO phoneBook);
    public List<Phonebook> getAllRecords();
}
