package com.petroit.phonebook.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class PhonebookRequestDTO {

    private String number;
    private String comments;
    private List<MultipartFile> documents;
    private MultipartFile image;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<MultipartFile> getDocuments() {
        return documents;
    }

    public void setDocuments(List<MultipartFile> documents) {
        this.documents = documents;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
