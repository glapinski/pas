package com.pas.exception;

import jakarta.persistence.EntityExistsException;

public class LoginAlreadyTakenException extends EntityExistsException {
    public LoginAlreadyTakenException(String message) {
        super(message);
    }
}
