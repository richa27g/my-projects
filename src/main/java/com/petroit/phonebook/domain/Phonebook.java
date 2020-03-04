package com.petroit.phonebook.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(
        collection = "phone_data"
)
public class Phonebook {

    String number;
    String comments;
    List<String> images;
    List<String> documents;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getComments() {
        return comments;
    }

    public Phonebook setComments(String comments) {
        this.comments = comments;
        return this;
    }

    public List<String> getImages() {
        return images;
    }

    public Phonebook setImages(List<String> images) {
        this.images = images;
        return this;
    }

    public List<String> getDocuments() {
        return documents;
    }

    public Phonebook setDocuments(List<String> documents) {
        this.documents = documents;
        return this;
    }
}
