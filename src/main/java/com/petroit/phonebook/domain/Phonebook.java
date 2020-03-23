package com.petroit.phonebook.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(
        collection = "phone_data"
)
public class Phonebook {

    @Id
    String number;
    String comments;
    String image;
    List<String> documents = new ArrayList<>();
    Date createdOn;
    Date updatedOn;

    public Phonebook() {
        this.createdOn = new Date();
        this.updatedOn = new Date();
    }

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

    public String getImage() {
        return image;
    }

    public Phonebook setImage(String image) {
        this.image = image;
        return this;
    }

    public List<String> getDocuments() {
        return documents;
    }

    public Phonebook setDocuments(List<String> documents) {
        this.documents = documents;
        return this;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public Phonebook setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public Phonebook setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    @Override
    public String toString() {
        return "Phonebook{" +
                "number='" + number + '\'' +
                ", comments='" + comments + '\'' +
                ", image=" + image +
                ", documents=" + documents +
                ", createdOn=" + createdOn +
                ", updatedOn=" + updatedOn +
                '}';
    }
}
