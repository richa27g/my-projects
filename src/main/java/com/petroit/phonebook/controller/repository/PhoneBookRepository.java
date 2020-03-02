package com.petroit.phonebook.controller.repository;

import com.petroit.phonebook.controller.domain.Phonebook;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PhoneBookRepository extends MongoRepository<Phonebook, String> {
}
