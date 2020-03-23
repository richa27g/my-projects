package com.petroit.phonebook.exceptions;

public class NoSuchRecordExists extends RuntimeException {

    private static final long serialVersionUID = 62348371762936942L;

    public NoSuchRecordExists(String message)
    {
        super(message);
    }
}
