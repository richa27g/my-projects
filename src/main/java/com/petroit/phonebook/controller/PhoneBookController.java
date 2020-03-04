package com.petroit.phonebook.controller;

import com.petroit.phonebook.domain.Phonebook;
import com.petroit.phonebook.services.PhoneBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

@RequestMapping("/phonebook")
@RestController
public class PhoneBookController {


    @Autowired
    private PhoneBookService phoneBookService;

    @PostMapping(value = "/doUpload",consumes = {"multipart/form-data"})
    public String upload(@RequestParam MultipartFile file) {

        phoneBookService.uploadFile(file);

        return "done";
    }
/*

    @ExceptionHandler(StorageException.class)
    public String handleStorageFileNotFound(StorageException e) {

        return "redirect:/failure.html";
    }

*/

    @GetMapping(value="/download/{fileName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity downloadFileFromLocal(@PathVariable String fileName) {

            return phoneBookService.fileDownload(fileName,"application/octet-stream");
    }

    @GetMapping(value="/download/image/{fileName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity downloadImageFromLocal(@PathVariable String fileName) {

        return phoneBookService.fileDownload(fileName,IMAGE_JPEG_VALUE);
    }


    @GetMapping("/{number}")
    public void getData(@PathVariable String number)
    {
        phoneBookService.getRecord(number);
    }

    @PostMapping()
    public void saveData(@RequestBody Phonebook phoneBook)
    {

    }

    @PostMapping("/{number}")
    public boolean removeRecord(@PathVariable String number)
    {
        return phoneBookService.removeRecord(number);
    }

    @PutMapping("/phonebook")
    public void updateRecord(@RequestBody Phonebook phoneBook)
    {
       // return phoneBookService.updateRecord(phoneBook);
    }




}
