package com.petroit.phonebook.exceptions;

public class RecordAlreadyExists extends RuntimeException {

    private static final long serialVersionUID = 62340971762936942L;

    public RecordAlreadyExists(String message)
    {
        super(message);
    }
}
