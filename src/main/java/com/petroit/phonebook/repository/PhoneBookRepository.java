package com.petroit.phonebook.repository;

import com.petroit.phonebook.domain.Phonebook;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PhoneBookRepository extends MongoRepository<Phonebook, String> {
}
