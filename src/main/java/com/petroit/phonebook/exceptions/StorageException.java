package com.petroit.phonebook.exceptions;

public class StorageException extends RuntimeException {

    private static final long serialVersionUID = 62938371762936942L;

    public StorageException(String message)
    {
        super(message);
    }

    public StorageException(String message,Exception e)
    {
        super(message,e);
    }
}
