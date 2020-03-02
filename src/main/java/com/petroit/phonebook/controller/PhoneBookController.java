package com.petroit.phonebook.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.petroit.phonebook.controller.domain.Phonebook;

@RequestMapping("/phonebook")
@RestController
public class PhoneBookController {

    @PostMapping("/phonebook")
    public void saveData(@RequestBody Phonebook phoneBook)
    {

    }


}
