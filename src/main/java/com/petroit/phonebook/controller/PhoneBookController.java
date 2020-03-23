package com.petroit.phonebook.controller;

import com.petroit.phonebook.domain.Phonebook;
import com.petroit.phonebook.dto.PhonebookRequestDTO;
import com.petroit.phonebook.services.PhoneBookService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;


@Controller
public class PhoneBookController {


    @Autowired
    private PhoneBookService phoneBookService;


    @GetMapping("/")
    public String greetingForm(Model model) {
        List<Phonebook> phonebook=phoneBookService.getAllRecords();
        model.addAttribute("phonebook",phonebook);
        return "index";
    }


    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
        model.addAttribute("phonebook",new Phonebook());
        return "add-user";
    }

    @ApiOperation(value = "Operation to upload files/image.")
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

    @ApiOperation(value = "Operation to download file.")
    @GetMapping(value="/download/{fileName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity downloadFileFromLocal(@PathVariable String fileName) {

            return phoneBookService.fileDownload(fileName,"application/octet-stream");
    }

    @ApiOperation(value = "Operation to download image.")
    @GetMapping(value="/download/image/{fileName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity downloadImageFromLocal(@PathVariable String fileName) {

        return phoneBookService.fileDownload(fileName,IMAGE_JPEG_VALUE);
    }

    @ApiOperation(value = "Operation to get the record using phone number.")
    @GetMapping("/phonebook/{number}")
    public void getData(@PathVariable String number)
    {
        phoneBookService.getRecord(number);
    }


    //Tested and working
    @ApiOperation(value = "Operation to save record for a phone number.")
    @PostMapping("/phonebook/add")
    public String saveData(@ModelAttribute PhonebookRequestDTO phonebook, Model model)
    {
        phoneBookService.saveRecord(phonebook);
        List<Phonebook> list=phoneBookService.getAllRecords();
        model.addAttribute("phonebook",list);
        return "index";
    }

    //Tested and working
    @ApiOperation(value = "Operation to remove the phone record.")
    @GetMapping("/phonebook/remove/{number}")
    public String removeRecord(@PathVariable String number,Model model)
    {
        phoneBookService.removeRecord(number);
        List<Phonebook> phonebook=phoneBookService.getAllRecords();
        model.addAttribute("phonebook",phonebook);
        return "index";
    }

    @ApiOperation(value = "Operation to update the phone record.")
    @PostMapping("/phonebook/update")
    public String updateRecord(PhonebookRequestDTO phoneBook,Model model)
    {
        phoneBookService.updateRecord(phoneBook);
        List<Phonebook> phonebook=phoneBookService.getAllRecords();
        model.addAttribute("phonebook",phonebook);
        return "index";
    }

    @GetMapping("/phonebook/edit/{id}")
    public String showUpdateForm(@PathVariable("id") String id, Model model) {

       Phonebook phonebook =phoneBookService.getRecord(id);
       model.addAttribute("phonebook", phonebook);
       return "update-user";
    }



}
