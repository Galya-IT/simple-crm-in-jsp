package com.company.crm.exceptions;

public class ClientAlreadyExistsException extends Exception {
    public ClientAlreadyExistsException(String message) {
        super(message);
    }
}